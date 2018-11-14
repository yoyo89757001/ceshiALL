package com.ruitong.huiyi3.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ruitong.huiyi3.R;

/**
 * 横排（horizon）时优先显示后面(RearFirst)元素的Layout。
 * 使用方法，在xml中引入该布局，添加 rear_first 属性为true即可。
 * 关闭RearFirst则与一般LinearLayout表现一致。
 *
 * @author Ben
 */
// TODO: 2017/3/24 考虑多子view更好的处理
public class RearFirstLinearlayout extends LinearLayout {

    private boolean isRearFirst = false;
    private int rest;
    int mLeft, mRight, mTop, mBottom;

    public RearFirstLinearlayout(Context context) {
        this(context, null);

    }


    public RearFirstLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {

        // 获取属性
        if (null != attrs) {
            //TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RearFirstLinearlayout);
            isRearFirst =true;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        非横排和不开启RearFirst，则用系统默认measure
        Log.d("RearFirstLinearlayout", "ddddddddd");
        if (!isHorizon() || !isRearFirst) {
            return;
        }
        Log.d("RearFirstLinearlayout", "ddddddddd2222");
        int mWidth = MeasureSpec.getSize(widthMeasureSpec);//可用宽度
        int mHeight = getMeasuredHeight();
        int mCount = getChildCount();//子view数量
        //计算预计总宽
        int preComputeWidth = 0;
        //临时记录位置
        mLeft = 0;
        mRight = 0;
        mTop = 0;
        mBottom = 0;

        rest = mWidth;

        for (int i = mCount - 1; i >= 0; i--) {//从后往前算（因为要顺便计算position以备使用）
            final View child = getChildAt(i);
            int spec = MeasureSpec.makeMeasureSpec(rest, MeasureSpec.AT_MOST);
            child.measure(spec, MeasureSpec.UNSPECIFIED);
            int childw = child.getMeasuredWidth();
            int childh = child.getMeasuredHeight();
            preComputeWidth += childw;

//          计算rest
            mRight = getPositionRight(i, mCount, mWidth);
            mLeft = mRight - childw;
            mBottom = mTop + childh;
            rest = mLeft;
        }
        //保存最终的测量结果
        if (preComputeWidth <= mWidth) {
            setMeasuredDimension(preComputeWidth, mHeight);
        } else {
            setMeasuredDimension(mWidth, mHeight);
        }
    }

    private boolean isHorizon() {
        return getOrientation() == HORIZONTAL;
    }

    public int getPositionRight(int index, int count, int totalWidth) {
        if (index < count - 1) {
            return getPositionRight(index + 1, count, totalWidth) - getChildAt(index + 1).getMeasuredWidth();
        }
        return totalWidth - getPaddingRight();
    }
}
