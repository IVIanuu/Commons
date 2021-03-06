/*
 * Copyright 2017 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.commons;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

/**
 * Color utils
 */
public final class ColorUtil {

    private ColorUtil() {
        // no instances
    }

    /**
     * Strips the color
     */
    @ColorInt
    public static int stripAlpha(@ColorInt int color) {
        return 0xff000000 | color;
    }

    @ColorInt
    public static int shiftColor(@ColorInt int color, @FloatRange(from = 0.0f, to = 2.0f) float by) {
        if (by == 1f) return color;
        int alpha = Color.alpha(color);
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= by;
        return (alpha << 24) + (0x00ffffff & Color.HSVToColor(hsv));
    }

    /**
     * Returns the darken color
     */
    @ColorInt
    public static int darkenColor(@ColorInt int color) {
        return shiftColor(color, 0.9f);
    }

    /**
     * Returns the lighten color
     */
    @ColorInt
    public static int lightenColor(@ColorInt int color) {
        return shiftColor(color, 1.1f);
    }

    /**
     * Returns whether the color is light
     */
    public static boolean isColorLight(@ColorInt int color) {
        final double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness < 0.4;
    }

    /**
     * Returns the inverted color
     */
    @ColorInt
    public static int invertColor(@ColorInt int color) {
        final int r = 255 - Color.red(color);
        final int g = 255 - Color.green(color);
        final int b = 255 - Color.blue(color);
        return Color.argb(Color.alpha(color), r, g, b);
    }

    /**
     * Adjusts the alpha of the color
     */
    @ColorInt
    public static int adjustAlpha(@ColorInt int color, @FloatRange(from = 0.0, to = 1.0) float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * Returns the color with the alpha
     */
    @ColorInt
    public static int withAlpha(@ColorInt int baseColor, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

    /**
     * Returns the blended color
     */
    @ColorInt
    public static int blendColors(int color1, int color2, @FloatRange(from = 0.0, to = 1.0) float ratio) {
        final float inverseRatio = 1f - ratio;
        float a = (Color.alpha(color1) * inverseRatio) + (Color.alpha(color2) * ratio);
        float r = (Color.red(color1) * inverseRatio) + (Color.red(color2) * ratio);
        float g = (Color.green(color1) * inverseRatio) + (Color.green(color2) * ratio);
        float b = (Color.blue(color1) * inverseRatio) + (Color.blue(color2) * ratio);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }

}
