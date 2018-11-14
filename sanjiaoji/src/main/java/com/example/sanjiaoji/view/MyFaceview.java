package com.example.sanjiaoji.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.sanjiaoji.R;

public class MyFaceview  extends View {

    private Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.quan1);
    private Bitmap bitmap2=BitmapFactory.decodeResource(getResources(), R.drawable.quan_2_1);
    private Bitmap bitmap3=BitmapFactory.decodeResource(getResources(), R.drawable.quan_2_2);
    private int w,h;
    private ValueAnimator animator=null;
    private float jiaodu=0;
    private RectF rectf=new RectF();
    private RectF rectf2=new RectF();
    private RectF rectf3=new RectF();

    public MyFaceview(Context context) {
        super(context);
        init();
    }

    public MyFaceview(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyFaceview(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void pause(){
        animator.pause();
    }

    public void resume(){
        animator.resume();
    }


    public void setDate(int w,int h){
        this.w=w;
        this.h=h;
        Log.d("MyFaceview", "h*0.1f:" + (h * 0.1f));
        rectf.set(0f,h*0.1f,w,h*0.6f);
        rectf2.set(100f,200f,w-100f,200f+(w-200f));
        rectf3.set(130f,230,w-130f,230f+(w-260f));

        Log.d("MyFaceview", rectf.toString());
        Log.d("MyFaceview", rectf2.toString());
        Log.d("MyFaceview", rectf3.toString());
    }


    private void init(){


        if (animator==null) {
            animator = new ValueAnimator();
            animator = ValueAnimator.ofFloat(0, 360);
            //动画时长，让进度条在CountDown时间内正好从0-360走完，
            animator.setDuration(2000);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.setInterpolator(new LinearInterpolator());//匀速
            animator.setRepeatCount(-1);//表示不循环，-1表示无限循环
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    /**
                     * 这里我们已经知道ValueAnimator只是对值做动画运算，而不是针对控件的，因为我们设置的区间值为0-1.0f
                     * 所以animation.getAnimatedValue()得到的值也是在[0.0-1.0]区间，而我们在画进度条弧度时，设置的当前角度为360*currentAngle，
                     * 因此，当我们的区间值变为1.0的时候弧度刚好转了360度
                     */
                    jiaodu = Float.valueOf(animation.getAnimatedValue().toString()).intValue();
                     MyFaceview.this.invalidate();

                }
            });
            animator.start();

        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (w==0)
            return;

        canvas.drawBitmap(bitmap,null,rectf,null);
        canvas.save();
        canvas.rotate(jiaodu,rectf2.centerX(),rectf2.centerY());
        canvas.drawBitmap(bitmap2,null,rectf2,null);
        canvas.restore();

        canvas.save();
        canvas.rotate(360-jiaodu,rectf3.centerX(),rectf3.centerY());
        canvas.drawBitmap(bitmap3,null,rectf3,null);
        canvas.restore();



    }
}
