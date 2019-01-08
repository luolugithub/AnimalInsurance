package com.innovation.biz.classifier;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.SystemClock;
import android.os.Trace;

import com.innovation.biz.iterm.CowFaceKeyPointsItem;
import com.innovation.biz.iterm.DonkeyFaceKeyPointsItem;
import com.innovation.biz.iterm.PredictRotationIterm;
import com.innovation.utils.ByteUtil;
import com.innovation.utils.PointFloat;
import com.innovation.utils.Rot2AngleType;
import com.innovation.utils.TensorFlowHelper;

import org.tensorflow.demo.Classifier;
import org.tensorflow.demo.env.ImageUtils;
import org.tensorflow.demo.env.Logger;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.innovation.utils.ImageUtils.padBitmap;

/**
 * Author by luolu, Date on 2018/11/7.
 * COMPANY：InnovationAI
 */

public class CattleRotationAndKeypointsClassifier implements Classifier {
    private static final Logger sLogger = new Logger(CattleRotationAndKeypointsClassifier.class);
    private boolean isModelQuantized;
    private int inputSize;
    private int[] intValues;
    private byte[][] detectRotation;

    private ByteBuffer imgData;
    private Interpreter tfLite;
    private int counterSum;
    private byte[][] keyPoints;
    private byte[][] exists;
    public static int cattlePredictAngleType;
    public static boolean cattleRotationAndKeypointsK1;
    public static boolean cattleRotationAndKeypointsK2;
    public static boolean cattleRotationAndKeypointsK3;

    /**
     * Initializes a native TensorFlow session for classifying images.
     *  @param assetManager The asset manager to be used to load assets.
     * @param modelFilename The filepath of the model GraphDef protocol buffer.
     * @param labelFilename The filepath of label file for classes.
     * @param inputSize The size of image input
     * @param isQuantized Boolean representing model is quantized or not
     */
    public static Classifier create(
            final AssetManager assetManager,
            final String modelFilename,
            final String labelFilename,
            final int inputSize,
            final boolean isQuantized)
            throws IOException {
        final CattleRotationAndKeypointsClassifier d = new CattleRotationAndKeypointsClassifier();

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
        d.detectRotation = new byte[1][2];
        d.keyPoints = new byte[1][26];
        d.exists = new byte[1][13];
        return d;
    }

    private CattleRotationAndKeypointsClassifier() {}

    @Override
    public List<PointFloat> recognizePointImage(Bitmap bitmap) {
        return null;
    }

    @Override
    public RecognitionAndPostureItem donkeyFaceBoxDetector(Bitmap bitmap) {
        return null;
    }

    @Override
    public RecognitionAndPostureItem cowFaceBoxDetector(Bitmap bitmap) {
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
    public PredictRotationIterm donkeyRotationAndKeypointsClassifier(Bitmap bitmap) {
        return null;
    }


    @Override
    public PredictRotationIterm cowRotationAndKeypointsClassifier(Bitmap bitmap) {
        counterSum++;
        PredictRotationIterm predictRotationIterm = null;
        if (bitmap == null) {
            return null;
        }
        cattleRotationAndKeypointsK1 = false;
        cattleRotationAndKeypointsK2 = false;
        cattleRotationAndKeypointsK3 = false;
        sLogger.i("bitmap height:" + bitmap.getHeight());
        sLogger.i("bitmap width:" + bitmap.getWidth());
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int padSize = Math.max(height, width);
        Bitmap padBitmap = padBitmap(bitmap);
        Bitmap resizeBitmap = TensorFlowHelper.resizeBitmap(padBitmap, inputSize);
        imgData = TensorFlowHelper.convertBitmapToByteBuffer(resizeBitmap, intValues, imgData);
        Trace.beginSection("feed");

        detectRotation = new byte[1][2];
        keyPoints = new byte[1][26];
        exists = new byte[1][13];
        sLogger.i("inputSize:" + inputSize);

        Object[] inputArray = {imgData};

        Map<Integer, Object> outputMap = new HashMap<>();
        outputMap.put(0, keyPoints);
        outputMap.put(1, exists);
        outputMap.put(2, detectRotation);
        Trace.endSection();

        // Run the inference call.
        Trace.beginSection("run");
        final long startTime = SystemClock.uptimeMillis();
        tfLite.runForMultipleInputsOutputs(inputArray, outputMap);
        sLogger.i("cattle RotationAndKeypoints tflite cost:" + (SystemClock.uptimeMillis() - startTime));
        float quantizationRotation = 104;
        double scaleRotation = 0.0109892;
        double quantizationKeypoints = 0.00390625;
        double existsThresholds = 0.5;

        int[] pointsExists = new int[13];
        float predictRotX;
        float predictRotY;
        float rotScale = (float) 57.6;
        cattlePredictAngleType = 10;
        char charsOutRotation0 = ByteUtil.convertByte2Uint8(detectRotation[0][0]);
        char charsOutRotation1 = ByteUtil.convertByte2Uint8(detectRotation[0][1]);
        float x = 404, y = 404;
        List<PointFloat> points = new ArrayList<>();
        PointFloat point0 = new PointFloat(x, y);
        PointFloat point1 = new PointFloat(x, y);
        PointFloat point2 = new PointFloat(x, y);
        PointFloat point3 = new PointFloat(x, y);
        PointFloat point4 = new PointFloat(x, y);
        PointFloat point5 = new PointFloat(x, y);
        PointFloat point6 = new PointFloat(x, y);
        PointFloat point7 = new PointFloat(x, y);
        PointFloat point8 = new PointFloat(x, y);
        PointFloat point9 = new PointFloat(x, y);
        PointFloat point10 = new PointFloat(x, y);
        PointFloat point11 = new PointFloat(x, y);
        PointFloat point12 = new PointFloat(x, y);

        char charsOutkeyPoints0 = ByteUtil.convertByte2Uint8(keyPoints[0][0]);
        char charsOutkeyPoints1 = ByteUtil.convertByte2Uint8(keyPoints[0][1]);
        char charsOutkeyPoints2 = ByteUtil.convertByte2Uint8(keyPoints[0][2]);
        char charsOutkeyPoints3 = ByteUtil.convertByte2Uint8(keyPoints[0][3]);
        char charsOutkeyPoints4 = ByteUtil.convertByte2Uint8(keyPoints[0][4]);
        char charsOutkeyPoints5 = ByteUtil.convertByte2Uint8(keyPoints[0][5]);
        char charsOutkeyPoints6 = ByteUtil.convertByte2Uint8(keyPoints[0][6]);
        char charsOutkeyPoints7 = ByteUtil.convertByte2Uint8(keyPoints[0][7]);
        char charsOutkeyPoints8 = ByteUtil.convertByte2Uint8(keyPoints[0][8]);
        char charsOutkeyPoints9 = ByteUtil.convertByte2Uint8(keyPoints[0][9]);
        char charsOutkeyPoints10 = ByteUtil.convertByte2Uint8(keyPoints[0][10]);
        char charsOutkeyPoints11 = ByteUtil.convertByte2Uint8(keyPoints[0][11]);
        char charsOutkeyPoints12 = ByteUtil.convertByte2Uint8(keyPoints[0][12]);
        char charsOutkeyPoints13 = ByteUtil.convertByte2Uint8(keyPoints[0][13]);
        char charsOutkeyPoints14 = ByteUtil.convertByte2Uint8(keyPoints[0][14]);
        char charsOutkeyPoints15 = ByteUtil.convertByte2Uint8(keyPoints[0][15]);
        char charsOutkeyPoints16 = ByteUtil.convertByte2Uint8(keyPoints[0][16]);
        char charsOutkeyPoints17 = ByteUtil.convertByte2Uint8(keyPoints[0][17]);
        char charsOutkeyPoints18 = ByteUtil.convertByte2Uint8(keyPoints[0][18]);
        char charsOutkeyPoints19 = ByteUtil.convertByte2Uint8(keyPoints[0][19]);
        char charsOutkeyPoints20 = ByteUtil.convertByte2Uint8(keyPoints[0][20]);
        char charsOutkeyPoints21 = ByteUtil.convertByte2Uint8(keyPoints[0][21]);
        char charsOutkeyPoints22 = ByteUtil.convertByte2Uint8(keyPoints[0][22]);
        char charsOutkeyPoints23 = ByteUtil.convertByte2Uint8(keyPoints[0][23]);
        char charsOutkeyPoints24 = ByteUtil.convertByte2Uint8(keyPoints[0][24]);
        char charsOutkeyPoints25 = ByteUtil.convertByte2Uint8(keyPoints[0][25]);
        char charsOutExists0 = ByteUtil.convertByte2Uint8(exists[0][0]);
        char charsOutExists1 = ByteUtil.convertByte2Uint8(exists[0][1]);
        char charsOutExists2 = ByteUtil.convertByte2Uint8(exists[0][2]);
        char charsOutExists3 = ByteUtil.convertByte2Uint8(exists[0][3]);
        char charsOutExists4 = ByteUtil.convertByte2Uint8(exists[0][4]);
        char charsOutExists5 = ByteUtil.convertByte2Uint8(exists[0][5]);
        char charsOutExists6 = ByteUtil.convertByte2Uint8(exists[0][6]);
        char charsOutExists7 = ByteUtil.convertByte2Uint8(exists[0][7]);
        char charsOutExists8 = ByteUtil.convertByte2Uint8(exists[0][8]);
        char charsOutExists9 = ByteUtil.convertByte2Uint8(exists[0][9]);
        char charsOutExists10 = ByteUtil.convertByte2Uint8(exists[0][10]);
        char charsOutExists11 = ByteUtil.convertByte2Uint8(exists[0][11]);
        char charsOutExists12 = ByteUtil.convertByte2Uint8(exists[0][12]);
        predictRotX = (float) ((charsOutRotation0 - quantizationRotation) * scaleRotation);
        predictRotY = (float) ((charsOutRotation1 - quantizationRotation) * scaleRotation);
        sLogger.i("charsOutRotation0 %f:" + predictRotX);
        sLogger.i("charsOutRotation1 %f:" + predictRotY);
        CowFaceKeyPointsItem cowFaceKeyPointsItem = CowFaceKeyPointsItem.getInstance();

        if ((charsOutExists0 - 0) * quantizationKeypoints > existsThresholds) {
            point0.set((float) (charsOutkeyPoints0 * quantizationKeypoints ), (float) (charsOutkeyPoints1 * quantizationKeypoints ));
            points.add(point0);
            pointsExists[0] = 1;
            cowFaceKeyPointsItem.setPointsExists0(pointsExists[0]);
            cowFaceKeyPointsItem.setPointFloat0(point0);
            sLogger.i("关键点1:");
            sLogger.i("获取的point1 %d:" + point0.toString());
        }else {
            pointsExists[0] = 0;
            cowFaceKeyPointsItem.setPointsExists0(pointsExists[0]);
            cowFaceKeyPointsItem.setPointFloat0(point0);
        }
        if ((charsOutExists1 - 0) * quantizationKeypoints > existsThresholds) {
            point1.set((float) (charsOutkeyPoints2 * quantizationKeypoints ), (float) (charsOutkeyPoints3 * quantizationKeypoints ));
            points.add(point1);
            pointsExists[1] = 1;
            cowFaceKeyPointsItem.setPointsExists1(pointsExists[1]);
            cowFaceKeyPointsItem.setPointFloat1(point1);
            sLogger.i("关键点2:");
            sLogger.i("获取的point2 %d:" + point1.toString());
        }else {
            pointsExists[1] = 0;
            cowFaceKeyPointsItem.setPointsExists1(pointsExists[1]);
            cowFaceKeyPointsItem.setPointFloat1(point1);
        }
        if ((charsOutExists2 - 0) * quantizationKeypoints > existsThresholds) {
            point2.set((float) (charsOutkeyPoints4 * quantizationKeypoints ), (float) (charsOutkeyPoints5 * quantizationKeypoints ));
            points.add(point2);
            pointsExists[2] = 1;
            cowFaceKeyPointsItem.setPointsExists2(pointsExists[2]);
            cowFaceKeyPointsItem.setPointFloat2(point2);
            sLogger.i("关键点3:");
            sLogger.i("获取的point3 %d:" + point2.toString());
        }else {
            pointsExists[2] = 0;
            cowFaceKeyPointsItem.setPointsExists2(pointsExists[2]);
            cowFaceKeyPointsItem.setPointFloat2(point2);
        }
        if ((charsOutExists3 - 0) * quantizationKeypoints > existsThresholds) {
            point3.set((float) (charsOutkeyPoints6 * quantizationKeypoints ), (float) (charsOutkeyPoints7 * quantizationKeypoints ));
            points.add(point3);
            pointsExists[3] = 1;
            cowFaceKeyPointsItem.setPointsExists3(pointsExists[3]);
            cowFaceKeyPointsItem.setPointFloat3(point3);
            sLogger.i("关键点4:");
            sLogger.i("获取的point4 %d:" + point3.toString());
        }else {
            pointsExists[3] = 0;
            cowFaceKeyPointsItem.setPointsExists3(pointsExists[3]);
            cowFaceKeyPointsItem.setPointFloat3(point3);
        }
        if ((charsOutExists4 - 0) * quantizationKeypoints > existsThresholds) {
            point4.set((float) (charsOutkeyPoints8 * quantizationKeypoints ), (float) (charsOutkeyPoints9 * quantizationKeypoints ));
            points.add(point4);
            pointsExists[4] = 1;
            cowFaceKeyPointsItem.setPointsExists4(pointsExists[4]);
            cowFaceKeyPointsItem.setPointFloat4(point4);
            sLogger.i("关键点5:");
            sLogger.i("获取的point5 %d:" + point4.toString());
        }else {
            pointsExists[4] = 0;
            cowFaceKeyPointsItem.setPointsExists4(pointsExists[4]);
            cowFaceKeyPointsItem.setPointFloat4(point4);
        }
        if ((charsOutExists5 - 0) * quantizationKeypoints > existsThresholds) {
            point5.set((float) (charsOutkeyPoints10 * quantizationKeypoints ), (float) (charsOutkeyPoints11 * quantizationKeypoints ));
            points.add(point5);
            pointsExists[5] = 1;
            cowFaceKeyPointsItem.setPointsExists5(pointsExists[5]);
            cowFaceKeyPointsItem.setPointFloat5(point5);
            sLogger.i("关键点6:");
            sLogger.i("获取的point6 %d:" + point5.toString());
        }else {
            pointsExists[5] = 0;
            cowFaceKeyPointsItem.setPointsExists5(pointsExists[5]);
            cowFaceKeyPointsItem.setPointFloat5(point5);
        }
        if ((charsOutExists6 - 0) * quantizationKeypoints > existsThresholds) {
            point6.set((float) (charsOutkeyPoints12 * quantizationKeypoints ), (float) (charsOutkeyPoints13 * quantizationKeypoints ));
            points.add(point6);
            pointsExists[6] = 1;
            cowFaceKeyPointsItem.setPointsExists6(pointsExists[6]);
            cowFaceKeyPointsItem.setPointFloat6(point6);
            sLogger.i("关键点7:");
            sLogger.i("获取的point7 %d:" + point6.toString());
        }else {
            pointsExists[6] = 0;
            cowFaceKeyPointsItem.setPointsExists6(pointsExists[6]);
            cowFaceKeyPointsItem.setPointFloat6(point6);
        }
        if ((charsOutExists7 - 0) * quantizationKeypoints > existsThresholds) {
            point7.set((float) (charsOutkeyPoints14 * quantizationKeypoints ), (float) (charsOutkeyPoints15 * quantizationKeypoints ));
            points.add(point7);
            pointsExists[7] = 1;
            cowFaceKeyPointsItem.setPointsExists7(pointsExists[7]);
            cowFaceKeyPointsItem.setPointFloat7(point7);
            sLogger.i("关键点8:");
            sLogger.i("获取的point8 %d:" + point7.toString());
        }else {
            pointsExists[7] = 0;
            cowFaceKeyPointsItem.setPointsExists7(pointsExists[7]);
            cowFaceKeyPointsItem.setPointFloat7(point7);
        }
        if ((charsOutExists8 - 0) * quantizationKeypoints > existsThresholds) {
            point8.set((float) (charsOutkeyPoints16 * quantizationKeypoints ), (float) (charsOutkeyPoints17 * quantizationKeypoints ));
            points.add(point8);
            pointsExists[8] = 1;
            cowFaceKeyPointsItem.setPointsExists8(pointsExists[8]);
            cowFaceKeyPointsItem.setPointFloat8(point8);
            sLogger.i("关键点9:");
            sLogger.i("获取的point9 %d:" + point8.toString());
        }else {
            pointsExists[8] = 0;
            cowFaceKeyPointsItem.setPointsExists8(pointsExists[8]);
            cowFaceKeyPointsItem.setPointFloat8(point8);
        }
        if ((charsOutExists9 - 0) * quantizationKeypoints > existsThresholds) {
            point9.set((float) (charsOutkeyPoints18 * quantizationKeypoints ), (float) (charsOutkeyPoints19 * quantizationKeypoints ));
            points.add(point9);
            pointsExists[9] = 1;
            cowFaceKeyPointsItem.setPointsExists9(pointsExists[9]);
            cowFaceKeyPointsItem.setPointFloat9(point9);
            sLogger.i("关键点10:");
            sLogger.i("获取的point10 %d:" + point9.toString());
        }else {
            pointsExists[9] = 0;
            cowFaceKeyPointsItem.setPointsExists9(pointsExists[9]);
            cowFaceKeyPointsItem.setPointFloat9(point9);
        }
        if ((charsOutExists10 - 0) * quantizationKeypoints > existsThresholds) {
            point10.set((float) (charsOutkeyPoints20 * quantizationKeypoints ), (float) (charsOutkeyPoints21 * quantizationKeypoints ));
            points.add(point10);
            pointsExists[10] = 1;
            cowFaceKeyPointsItem.setPointsExists10(pointsExists[10]);
            cowFaceKeyPointsItem.setPointFloat10(point10);
            sLogger.i("关键点11:");
            sLogger.i("获取的point11 %d:" + point10.toString());
        }else {
            pointsExists[10] = 0;
            cowFaceKeyPointsItem.setPointsExists10(pointsExists[10]);
            cowFaceKeyPointsItem.setPointFloat10(point10);
        }
        if ((charsOutExists11 - 0) * quantizationKeypoints > existsThresholds) {
            point11.set((float) (charsOutkeyPoints22 * quantizationKeypoints), (float) (charsOutkeyPoints23 * quantizationKeypoints));
            points.add(point11);
            pointsExists[11] = 1;
            cowFaceKeyPointsItem.setPointsExists11(pointsExists[11]);
            cowFaceKeyPointsItem.setPointFloat11(point11);
            sLogger.i("关键点12:");
            sLogger.i("获取的point11 %d:" + point11.toString());
        }else {
            pointsExists[11] = 0;
            cowFaceKeyPointsItem.setPointsExists11(pointsExists[11]);
            cowFaceKeyPointsItem.setPointFloat11(point11);
        }
        if ((charsOutExists12 - 0) * quantizationKeypoints > existsThresholds) {
            point12.set((float) (charsOutkeyPoints24 * quantizationKeypoints), (float) (charsOutkeyPoints25 * quantizationKeypoints));
            points.add(point12);
            pointsExists[12] = 1;
            cowFaceKeyPointsItem.setPointsExists12(pointsExists[12]);
            cowFaceKeyPointsItem.setPointFloat12(point12);
            sLogger.i("关键点13:");
            sLogger.i("获取的point12 %d:" + point12.toString());
        }else {
            pointsExists[12] = 0;
            cowFaceKeyPointsItem.setPointsExists12(pointsExists[12]);
            cowFaceKeyPointsItem.setPointFloat12(point12);
        }




        sLogger.i("获取的关键点 %d:" + points.toString());




        predictRotationIterm = new PredictRotationIterm(
                predictRotX,
                predictRotY,
                720);
        sLogger.i("角度预测分类器接收图片数量:" + counterSum);
        // TODO: 2018/11/1 By:LuoLu
        cattlePredictAngleType = Rot2AngleType.getCowAngleType(predictRotX,
                predictRotY);
        Canvas canvasDrawRecognition = new Canvas(padBitmap);
        if (cattlePredictAngleType == 1 && pointsExists[11] + pointsExists[12] == 2
                && pointsExists[7] + pointsExists[8] + pointsExists[9] + pointsExists[10] > 0
                && pointsExists[4] + pointsExists[5] == 0){
            // draw rotation

            com.innovation.utils.ImageUtils.drawText(canvasDrawRecognition,
                    "角度:"+cattlePredictAngleType, 10,30,
                    "X:"+String.valueOf(predictRotX * rotScale),10,60,
                    "Y:"+String.valueOf(predictRotY * rotScale),10,90);
            com.innovation.utils.ImageUtils.drawKeypoints(canvasDrawRecognition,points,padSize);

            cattleRotationAndKeypointsK1 = true;
            com.innovation.utils.ImageUtils.saveBitmap(padBitmap,"cattleRK1",CowFaceBoxDetector.srcCowBitmapName);
        }else if (cattlePredictAngleType == 2 && pointsExists[4] + pointsExists[6] + pointsExists[11] == 3
                && (pointsExists[0] + pointsExists[1] + pointsExists[2] + pointsExists[3] > 0
                || pointsExists[7] + pointsExists[8] + pointsExists[9] + pointsExists[10] > 0)){
            // draw rotation
            com.innovation.utils.ImageUtils.drawText(canvasDrawRecognition,
                    "角度:"+cattlePredictAngleType, 10,30,
                    "X:"+String.valueOf(predictRotX * rotScale),10,60,
                    "Y:"+String.valueOf(predictRotY * rotScale),10,90);
            com.innovation.utils.ImageUtils.drawKeypoints(canvasDrawRecognition,points,padSize);
            cattleRotationAndKeypointsK2 = true;
            com.innovation.utils.ImageUtils.saveBitmap(padBitmap,"cattleRK2",CowFaceBoxDetector.srcCowBitmapName);
        }else if (cattlePredictAngleType == 3 && pointsExists[11] + pointsExists[12] == 0
                && pointsExists[4] + pointsExists[5] == 2
                && pointsExists[0] + pointsExists[1]+ pointsExists[2]+ pointsExists[3] > 0){
            // draw rotation
            com.innovation.utils.ImageUtils.drawText(canvasDrawRecognition,
                    "角度:"+cattlePredictAngleType,10,30,
                    "X:"+String.valueOf(predictRotX * rotScale),10,60,
                    "Y:"+String.valueOf(predictRotY * rotScale),10,90);
            com.innovation.utils.ImageUtils.drawKeypoints(canvasDrawRecognition,points,padSize);
            cattleRotationAndKeypointsK3 = true;
            com.innovation.utils.ImageUtils.saveBitmap(padBitmap,"cattleRK3",CowFaceBoxDetector.srcCowBitmapName);
        }else {
            // draw rotation
            com.innovation.utils.ImageUtils.drawText(canvasDrawRecognition,
                    "角度:"+cattlePredictAngleType,10,30,
                    "X:"+String.valueOf(predictRotX * rotScale),10,60,
                    "Y:"+String.valueOf(predictRotY * rotScale),10,90);
            com.innovation.utils.ImageUtils.drawKeypoints(canvasDrawRecognition,points,padSize);
            com.innovation.utils.ImageUtils.saveBitmap(padBitmap,"cattleRK10",CowFaceBoxDetector.srcCowBitmapName);
        }

        return predictRotationIterm;
    }

}
