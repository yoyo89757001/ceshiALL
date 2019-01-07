package com.xiaojun.yaodiandemo.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by lemon.deng on 2018/12/13.
 * Description: 动态设置selecter,drawable
 */

public class DrawableUtil {

    /**
     * @param color
     * @param radius
     * 圆角
     */
    public static Drawable getGradientDrawable(int color, float radius){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setColor(color);
        return  drawable;
    }

    /**
     * @param color
     * @param radius
     * 圆角边线框
     */
    public static Drawable getGradientDrawableStroke(int color,int bgColor,float radius){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setStroke(1, color);
        drawable.setColor(bgColor);
        return drawable;
    }

    /**
     * 圆角
     */
    public static Drawable getGradientDrawable(int color,float rl1,float rl2,float rt1,float rt2,float rr1,float rr2,float rb1,float rb2){
        GradientDrawable drawable = new GradientDrawable();
        float f[] = {rl1,rl2, rt1, rt2, rr1,rr2, rb1,rb2};
        drawable.setCornerRadii(f);
        drawable.setColor(color);
        return drawable;
    }

    /**
     * 圆角边线框
     */
    public static Drawable getGradientDrawableStroke(int color,int bgColor,float rl1,float rl2,float rt1,float rt2,float rr1,float rr2,float rb1,float rb2){
        GradientDrawable drawable = new GradientDrawable();
        float f[] = {rl1,rl2, rt1, rt2, rr1,rr2, rb1,rb2};
        drawable.setCornerRadii(f);
        drawable.setStroke(1, color);
        drawable.setColor(bgColor);
        return drawable;
    }

    public static Drawable getSelectorDrawableColor(int normalColor,int focusedColor,float radius){
        StateListDrawable drawables = new StateListDrawable();

        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setCornerRadius(radius);
        normalDrawable.setColor(normalColor);

        GradientDrawable forcusedDrawable = new GradientDrawable();
        forcusedDrawable.setCornerRadius(radius);
        forcusedDrawable.setColor(focusedColor);

        drawables.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused},forcusedDrawable);
        drawables.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed},forcusedDrawable);
        drawables.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        drawables.addState(new int[]{},normalDrawable);
        return drawables;
    }

    public static Drawable getSelectorDrawableImage(Drawable normalDrawable,Drawable focusedDrawable){
        StateListDrawable drawables = new StateListDrawable();
        drawables.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused},focusedDrawable);
        drawables.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed},focusedDrawable);
        drawables.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_checked},focusedDrawable);
        drawables.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        drawables.addState(new int[]{},normalDrawable);
        return drawables;
    }

    public static ColorStateList getSelectorColorList(int normalColor,int focusedColor){
        int[] colors = new int[] { focusedColor, focusedColor,focusedColor,normalColor,normalColor};
        int[][] states = new int[5][];
        states[0] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_pressed };
        states[2] = new int[] { android.R.attr.state_enabled, android.R.attr.state_checked };
        states[3] = new int[] { android.R.attr.state_enabled };
        states[4] = new int[] {};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }
}
