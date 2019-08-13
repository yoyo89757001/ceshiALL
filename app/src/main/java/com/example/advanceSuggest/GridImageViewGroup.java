package com.example.advanceSuggest;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.lansoeditor.demo.R;

public class GridImageViewGroup extends ViewGroup {
    private int childVerticalSpace = 0;
    private int childHorizontalSpace = 0;
    private int columnNum = 3;
    private int childWidth = 0;
    private int childHeight = 0;
    private long count=0;


    public GridImageViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.GridImageViewGroup);
        if (attributes != null) {
            childVerticalSpace = attributes.getDimensionPixelSize(R.styleable.GridImageViewGroup_childVerticalSpace, 0);
            childHorizontalSpace = attributes.getDimensionPixelSize(R.styleable.GridImageViewGroup_childHorizontalSpace, 0);
            columnNum = attributes.getInt(R.styleable.GridImageViewGroup_columnNum, 3);
            attributes.recycle();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("GridImageViewGroup", "l:" + l);
        Log.d("GridImageViewGroup", "t:" + t);
        Log.d("GridImageViewGroup", "r:" + r);
        Log.d("GridImageViewGroup", "b:" + b);
        count++;


        final int size = getChildCount();


        for (int i = 0; i < size; i++) {
            final View view = getChildAt(i);
        ValueAnimator anim = ValueAnimator.ofInt(0, childHeight);
        // ofInt（）作用有两个
        // 1. 创建动画实例
        // 2. 将传入的多个Int参数进行平滑过渡:此处传入0和1,表示将值从0平滑过渡到1
        // 如果传入了3个Int参数 a,b,c ,则是先从a平滑过渡到b,再从b平滑过渡到C，以此类推
        // ValueAnimator.ofInt()内置了整型估值器,直接采用默认的.不需要设置，即默认设置了如何从初始值 过渡到 结束值
        // 关于自定义插值器我将在下节进行讲解
        // 下面看看ofInt()的源码分析 ->>关注1
        // 步骤2：设置动画的播放各种属性
        anim.setDuration(500);
        // 设置动画运行的时长
        //anim.setStartDelay(500);
        // 设置动画延迟播放时间
        anim.setRepeatCount(0);
        // 设置动画重复播放次数 = 重放次数+1
        // 动画播放次数 = infinite时,动画无限重复
        anim.setRepeatMode(ValueAnimator.RESTART);
        // 设置重复播放动画模式
        // ValueAnimator.RESTART(默认):正序重放
        // ValueAnimator.REVERSE:倒序回放

        // 步骤3：将改变的值手动赋值给对象的属性值：通过动画的更新监听器
        // 设置 值的更新监听器
        // 即：值每次改变、变化一次,该方法就会被调用一次
            final int finalI = i;
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int currentValue = (Integer) animation.getAnimatedValue();
                // 获得改变后的值

              //  System.out.println(currentValue);
                // 输出改变后的值
                // 步骤4：将改变后的值赋给对象的属性值，下面会详细说明
               //  view.setY(currentValue);
                if (finalI ==0){
//                    if (size>1){
//                        if (count%2==0){
//                            //zuo
//                            view.layout(childWidth-currentValue, 0, 2*childWidth-currentValue, childHeight);
//                        }else {
//                            view.layout(childWidth+currentValue, 0, 2*childWidth+currentValue, childHeight);
//                        }
//
//                    }else{
                        //只有一个
                        view.layout(childWidth, childWidth-currentValue, 2*childWidth, childHeight);
                //    }

                }
                if (finalI ==1){
                    if (count%2==0){
                        //zuo
                        view.layout(childWidth-currentValue, 0, 2*childWidth-currentValue, childHeight);
                    }else {
                        view.layout(childWidth+currentValue, 0, 2*childWidth+currentValue, childHeight);
                    }
                   // view.layout(childWidth, childWidth-currentValue, 2*childWidth, childHeight);
                }
                if (finalI ==2){
                    if (count%2==1){
                        //zuo
                        view.layout(childWidth-currentValue, 0, 2*childWidth-currentValue, childHeight);
                    }else {
                        view.layout(childWidth+currentValue, 0, 2*childWidth+currentValue, childHeight);
                    }
                   // view.layout(childWidth, childWidth-currentValue, 2*childWidth, childHeight);
                }

                // 步骤5：刷新视图，即重新绘制，从而实现动画效果
                view.invalidate();
            }
        });
        anim.start();
        // 启动动画

    }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int rw = MeasureSpec.getSize(widthMeasureSpec);
        int rh = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount > 0) {

            if (childCount>3){
                removeViewAt(2);
                requestLayout();
                invalidate();
            }
            childWidth = rw/3;
            childHeight=childWidth;
            setMeasuredDimension(rw, 300);
        }


    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        int left = 0;
//        int top = 0;
//        int count = getChildCount();
//        Log.d("GridImageViewGroup", "count:" + count);
//        Log.d("GridImageViewGroup", "getHeight():" + getHeight());
//
//        if (count==1){
//           final View view= getChildAt(0);
//            getChildAt(0).layout(childWidth, 0, 2*childWidth,  childHeight);
//            ValueAnimator anim = ValueAnimator.ofInt(getHeight(), getHeight()+childHeight);
//            // ofInt（）作用有两个
//            // 1. 创建动画实例
//            // 2. 将传入的多个Int参数进行平滑过渡:此处传入0和1,表示将值从0平滑过渡到1
//            // 如果传入了3个Int参数 a,b,c ,则是先从a平滑过渡到b,再从b平滑过渡到C，以此类推
//            // ValueAnimator.ofInt()内置了整型估值器,直接采用默认的.不需要设置，即默认设置了如何从初始值 过渡到 结束值
//            // 关于自定义插值器我将在下节进行讲解
//            // 下面看看ofInt()的源码分析 ->>关注1
//            // 步骤2：设置动画的播放各种属性
//            anim.setDuration(500);
//            // 设置动画运行的时长
//            anim.setStartDelay(500);
//            // 设置动画延迟播放时间
//            anim.setRepeatCount(0);
//            // 设置动画重复播放次数 = 重放次数+1
//            // 动画播放次数 = infinite时,动画无限重复
//            anim.setRepeatMode(ValueAnimator.RESTART);
//            // 设置重复播放动画模式
//            // ValueAnimator.RESTART(默认):正序重放
//            // ValueAnimator.REVERSE:倒序回放
//
//// 步骤3：将改变的值手动赋值给对象的属性值：通过动画的更新监听器
//            // 设置 值的更新监听器
//            // 即：值每次改变、变化一次,该方法就会被调用一次
//            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//
//                    int currentValue = (Integer) animation.getAnimatedValue();
//                    // 获得改变后的值
//
//                    System.out.println(currentValue);
//                    // 输出改变后的值
//                    // 步骤4：将改变后的值赋给对象的属性值，下面会详细说明
//                    view.setY(currentValue);
//
//                    // 步骤5：刷新视图，即重新绘制，从而实现动画效果
//                 //   view.requestLayout();
//
//
//                }
//            });
//            anim.start();
//            // 启动动画
//
//
//        }
//        if (count==2){
//            getChildAt(0).layout(0, 0, childWidth,  childHeight);
//            getChildAt(1).layout(childWidth, 0, 2*childWidth,  childHeight);
//        }
//        if (count==3){
//            getChildAt(1).layout(0, 0, childWidth,  childHeight);
//            getChildAt(2).layout(childWidth, 0, 2*childWidth,  childHeight);
//            getChildAt(0).layout(2*childWidth, 0, 3*childWidth,  childHeight);
//        }
//
//
//
//    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("GridImageViewGroup", "dsadas");


    }
}
