package com.innovation.biz.classifier;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Trace;

import com.innovation.base.InnApplication;
import com.innovation.biz.iterm.AnimalClassifierResultIterm;
import com.innovation.biz.iterm.CowClassifiedResultItem;
import com.innovation.biz.iterm.PostureItem;
import com.innovation.biz.iterm.PredictRotationIterm;
import com.innovation.utils.PointFloat;
import com.innovation.utils.TensorFlowHelper;

import org.tensorflow.demo.CameraConnectionFragment;
import org.tensorflow.demo.Classifier;
import org.tensorflow.demo.DetectorActivity;
import org.tensorflow.demo.env.ImageUtils;
import org.tensorflow.demo.env.Logger;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.innovation.utils.ImageUtils.padBitmap2SpRatio;
import static com.innovation.utils.ImageUtils.zoomImage;
import static org.tensorflow.demo.CameraConnectionFragment.textAngelX;
import static org.tensorflow.demo.CameraConnectionFragment.textAngelY;
import static org.tensorflow.demo.DetectorActivity.offsetX;
import static org.tensorflow.demo.DetectorActivity.offsetY;

/**
 * @author luolu .2018/8/4
 */
public class CowFaceBoxDetector implements Classifier {
    private static final Logger sLogger = new Logger(CowFaceBoxDetector.class);
    public static final float MIN_CONFIDENCE = (float) 0.5;
    private static final String TAG = "CowFaceBoxDetector";

    private boolean isModelQuantized;
    // Config values.
    private int inputSize;
    private int[] intValues;
    private float[][][] outputLocations;
    private float[][] outputScores;
    private float[] outputDetectNum;
    private float[][] outputClassifyResult;
    private ByteBuffer imgData;
    private Interpreter tfLite;
    private Classifier cattleFaceClassifier;
    private Classifier cowFaceRotationDetector;
    private Classifier cowFaceKeyPointsDetector;
    private Context context;
    public static String srcCowBitmapName;
    public static RecognitionAndPostureItem cowRecognitionAndPostureItemTFlite;

    private CowFaceBoxDetector() {
        try {
//            cattleFaceClassifier =
//                    CattleRotationAndKeypointsClassifier.create(
//                            InnApplication.getAppContext().getAssets(),
//                            CATTLE_ROTATION_AND_KEYPOINT,
//                            "",
//                            192,
//                            true);
            cowFaceRotationDetector =
                    CowRotationClassifier.create(
                            InnApplication.getAppContext().getAssets(),
                            TensorFlowHelper.COW_TFLITE_PREDICTION_MODEL_FILE,
                            "",
                            192,
                            true);
            cowFaceKeyPointsDetector =
                    CowKeyPointsClassifier.create(
                            InnApplication.getAppContext().getAssets(),
                            TensorFlowHelper.COW_TFLITE_KEYPOINTS_MODEL_FILE,
                            "",
                            192,
                            true);

        } catch (final Exception e) {
            throw new RuntimeException("Error initializing TensorFlow!", e);
        }
    }

    /**
     * Initializes a native TensorFlow session for classifying images.
     *
     * @param assetManager  The asset manager to be used to load assets.
     * @param modelFilename The filepath of the model GraphDef protocol buffer.
     * @param labelFilename The filepath of label file for classes.
     * @param inputSize     The size of image input
     * @param isQuantized   Boolean representing model is quantized or not
     */
    public static Classifier create(
            final AssetManager assetManager,
            final String modelFilename,
            final String labelFilename,
            final int inputSize,
            final boolean isQuantized)
            throws IOException {
        final CowFaceBoxDetector d = new CowFaceBoxDetector();
        d.inputSize = inputSize;
        try {
            d.tfLite = new Interpreter(TensorFlowHelper.loadModelFile(assetManager, modelFilename));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        d.isModelQuantized = isQuantized;
        // Pre-allocate buffers.
        int numBytesPerChannel;
        if (isQuantized) {
            numBytesPerChannel = 1; // Quantized
        } else {
            numBytesPerChannel = 4; // Floating point
        }
        d.imgData = ByteBuffer.allocateDirect(1 * d.inputSize * d.inputSize * 3 * numBytesPerChannel);
        d.imgData.order(ByteOrder.nativeOrder());
        d.intValues = new int[d.inputSize * d.inputSize];
        d.tfLite.setNumThreads(TensorFlowHelper.NUM_THREADS);
        d.outputLocations = new float[1][TensorFlowHelper.NUM_DETECTIONS][4];
        d.outputClassifyResult = new float[1][10];
        d.outputScores = new float[1][10];
        d.outputDetectNum = new float[1];
        return d;
    }

    @Override
    public List<PointFloat> recognizePointImage(Bitmap bitmap) {
        return null;
    }

    @Override
    public RecognitionAndPostureItem donkeyFaceBoxDetector(Bitmap bitmap) {
        return null;
    }

    @Override
    public PredictRotationIterm cowRotationAndKeypointsClassifier(Bitmap bitmap) {
        return null;
    }

    @Override
    public RecognitionAndPostureItem pigFaceBoxDetector(Bitmap bitmap) {
        return null;
    }

    @Override
    public PredictRotationIterm pigRotationAndKeypointsClassifier(Bitmap bitmap) {
        return null;
    }

    @Override
    public PredictRotationIterm donkeyRotationAndKeypointsClassifier(Bitmap bitmap) {
        return null;
    }

    @Override
    public void enableStatLogging(boolean debug) {
        //inferenceInterface.enableStatLogging(debug);
    }

    @Override
    public String getStatString() {
        return "tflite";
    }

    @Override
    public void close() {
        tfLite.close();
    }

    @Override
    public RecognitionAndPostureItem cowFaceBoxDetector(Bitmap bitmap) {
        cowRecognitionAndPostureItemTFlite = new RecognitionAndPostureItem();
        PostureItem posture = null;
        float angleX = -20000;
        float angleY = -20000;
        if (bitmap == null) {
            return null;
        }
        sLogger.i("bitmap height:" + bitmap.getHeight());
        sLogger.i("bitmap width:" + bitmap.getWidth());
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int padSize = height;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
        srcCowBitmapName = sdf.format(new Date(System.currentTimeMillis())) + ".jpeg";
        com.innovation.utils.ImageUtils.saveBitmap(bitmap, "cowSRC", srcCowBitmapName);
        sLogger.i("padBitmap padSize %d:" + padSize);

        Bitmap resizeBitmap = TensorFlowHelper.resizeBitmap(bitmap, inputSize);
        imgData = TensorFlowHelper.convertBitmapToByteBuffer(resizeBitmap, intValues, imgData);
        Trace.beginSection("feed");

        outputLocations = new float[1][TensorFlowHelper.NUM_DETECTIONS][4];
        outputClassifyResult = new float[1][TensorFlowHelper.NUM_DETECTIONS];
        outputScores = new float[1][TensorFlowHelper.NUM_DETECTIONS];
        outputDetectNum = new float[1];

        sLogger.i("inputSize:" + inputSize);

        Object[] inputArray = {imgData};

        Map<Integer, Object> outputMap = new HashMap<>();
        outputMap.put(0, outputLocations);
        outputMap.put(1, outputClassifyResult);
        outputMap.put(2, outputScores);
        outputMap.put(3, outputDetectNum);
        Trace.endSection();

        // Run the inference call.
        Trace.beginSection("run");
        final long startTime = SystemClock.uptimeMillis();


        tfLite.runForMultipleInputsOutputs(inputArray, outputMap);

        sLogger.i("Detect face tflite cost:" + (SystemClock.uptimeMillis() - startTime));

        Trace.endSection();

        float modelY0 = 0;
        float modelX0 = 0;
        float modelY1 = 0;
        float modelX1 = 0;
        int detectedNum = 0;
        boolean bContinue = false;

        if (outputDetectNum[0] <= 0){
            DetectorActivity.AngleTrackType = 10;
            return cowRecognitionAndPostureItemTFlite;
        }
        if (outputDetectNum[0] > 1) {
            CameraConnectionFragment.showToast("检测到干扰对象，请调整！");
        }
        //box数量及边界的判断
        if (outputDetectNum[0] > 0) {
            for (int i = 0; i < outputDetectNum[0]; i++) {
                if (outputScores[0][i] >= MIN_CONFIDENCE) {
                    detectedNum++;
                }
            }
                bContinue = true;
        }
        if (bContinue) {
            com.innovation.utils.ImageUtils.saveBitmap(bitmap, "cowDetected", srcCowBitmapName);
            sLogger.i("outputScores0 %f:" + outputScores[0][0]);
            sLogger.i("OutClassifyResult0 %f:" + outputClassifyResult[0][0]);
            sLogger.i("OutPDetectNum %f:" + outputDetectNum[0]);
            modelY0 = outputLocations[0][0][1];
            modelX0 = outputLocations[0][0][0];
            modelY1 = outputLocations[0][0][3];
            modelX1 = outputLocations[0][0][2];

            sLogger.i("modelY0 %f:" + modelY0);
            sLogger.i("modelX0 %f:" + modelX0);
            sLogger.i("modelY1 %f:" + modelY1);
            sLogger.i("modelX1 %f:" + modelX1);
//tracker
            final List<Classifier.Recognition> mappedRecognitions =
                    new LinkedList<Recognition>();
            final ArrayList<Recognition> recognitions = new ArrayList<>(1);
            final RectF detection =
                    new RectF(
                            modelY0 * padSize - offsetY,
                            modelX0 * padSize - offsetX,
                            modelY1 * padSize - offsetY,
                            modelX1 * padSize - offsetX);
            recognitions.add(
                    new Recognition(
                            "",
                            "cowLite",
                            outputScores[0][0],
                            detection, null));
            Trace.endSection(); // "recognizeImage"
            cowRecognitionAndPostureItemTFlite.setList(recognitions);


            if (modelY0 <= 0 && modelY0 >= 1 &&
                    modelX0 <= 0 && modelX0 >= 1 &&
                    modelY1 <= 0 && modelY1 >= 1 &&
                    modelX1 <= 0 && modelX1 >= 1) {
                DetectorActivity.AngleTrackType = 10;
                return cowRecognitionAndPostureItemTFlite;
            }

            //clip image
            Bitmap clipBitmap = null;
            clipBitmap = com.innovation.utils.ImageUtils.clipBitmap(bitmap, modelY0, modelX0, modelY1, modelX1, 1.2f);
            Bitmap padBitmap2SpRatio = padBitmap2SpRatio(clipBitmap, 1.0f);
            int widthZoom = 320, heightZoom = 320;
            Bitmap resizeClipBitmap = zoomImage(padBitmap2SpRatio, widthZoom, heightZoom);

//rotation predict
            PredictRotationIterm predictRotationIterm = null;
//                    predictRotationIterm = cattleFaceClassifier.cowRotationAndKeypointsClassifier(clipBitmap);
            List<PointFloat> keypointResults = new ArrayList<>();
            predictRotationIterm = cowFaceRotationDetector.cowRotationAndKeypointsClassifier(clipBitmap);
            if (predictRotationIterm != null) {
                //keypoint detect
                keypointResults = cowFaceKeyPointsDetector.recognizePointImage(clipBitmap);
            }

            com.innovation.utils.ImageUtils.saveBitmap(clipBitmap, "cowCrop", srcCowBitmapName);
// draw rect
//                Canvas canvasDrawRecognition = new Canvas(bitmap);
//                com.innovation.utils.ImageUtils.drawRecognitions(canvasDrawRecognition,
//                        modelY0, modelX0, modelY1, modelX1);
//                saveDrawRectBitmap(bitmap);

            // TODO: 2018/10/30 By:LuoLu
            angleX = (float) (predictRotationIterm.rot_x * 180 / 3.14);
            angleY = (float) (predictRotationIterm.rot_y * 180 / 3.14);
            if (predictRotationIterm != null && keypointResults != null) {
                posture = new PostureItem(
                        (float) predictRotationIterm.rot_x,
                        (float) predictRotationIterm.rot_y,
                        (float) predictRotationIterm.rot_z,
                        modelX0, modelY0, modelX1, modelY1, outputScores[0][0],
                        modelY0 * padSize, modelX0 * padSize,
                        modelY1 * padSize, modelX1 * padSize, resizeClipBitmap, resizeClipBitmap);
                cowRecognitionAndPostureItemTFlite.setPostureItem(posture);
                AnimalClassifierResultIterm.cowAngleCalculateTFlite(cowRecognitionAndPostureItemTFlite.getPostureItem());
            } else {
                cowRecognitionAndPostureItemTFlite.setPostureItem(null);
            }

        } else {
            DetectorActivity.AngleTrackType = 10;
            return cowRecognitionAndPostureItemTFlite;
        }
        float finalAngleX = angleX;
        float finalAngleY = angleY;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                if (finalAngleX == -20000 || finalAngleY == -20000) {
                    textAngelX.setText("X轴:" + "NULL");
                    textAngelY.setText("Y轴:" + "NULL");
                } else {
                    textAngelX.setText("X轴:" + String.valueOf(finalAngleX));
                    textAngelY.setText("Y轴:" + String.valueOf(finalAngleY));
                }
            }
        });
        return cowRecognitionAndPostureItemTFlite;
    }

}
