package com.example.mariliavgama.profilelist.util;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;

/**
 * Utility class for layout interactions
 */

public class LayoutUtils {

    /** Convert a value in dp (density independent pixels) to pixels
     *
     * @param valueInDP value in dp
     * @return value in pixels
     */
    public static int DPToPixels(float valueInDP) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDP, Resources.getSystem().getDisplayMetrics());
    }

    public static String formatText(String s1, String s2, String format) {
        if ("".equals(s1) || "".equals(s2)) {
            return s1 + s2;
        }
        return  String.format(format,s1, s2);
    }
}