package com.innovation.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Arrays;

/**
 * Created by Luolu on 2018/11/19.
 * InnovationAI
 * luolu@innovationai.cn
 */
public class BoxUtil {
    public static boolean isRectangleOverlap(float[] rec1, float[] rec2) {

        return rec1[0] < rec2[2] && rec2[0] < rec1[2] && rec1[1] < rec2[3] && rec2[1] < rec1[3];
    }



    public static void main(String[] args) {
        float[] rec1 = {0, 10, 10, 0};
        float[] rec2 = {5, 5, 15, 0};
        float[] rec3 = {1, 20, 15, 30};
        float[] rec4 = {5, 15, 25, 40};
        float[] rec5 = {2, 5, 15, 40};
//        boolean result = isRectangleOverlap(rec1,rec2);
//       if (!result){
//           System.out.println("Rectangles Overlap");
//       }else {
//           System.out.println("Rectangles Don't Overlap");
//       }

//        List al = new ArrayList();
//        al.add(rec1);
//        al.add(rec2);
//        al.add(rec3);
//        al.add(rec4);
//        al.add(rec5);
//
//        System.out.println("SRC ArrayList : " + al.toArray().toString());
//        // This makes a call to remove(int) and
//        // removes element 20.
//        al.remove(1);
//
//        // Now element 30 is moved one position back
//        // So element 30 is removed this time
//        al.remove(1);
//
//        System.out.println("Modified ArrayList : " + al.toArray().toString());
    }
}
