package com.example.advanceSuggest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyTest extends View {
    private int ballX = 300;
    private int ballY = 300;
    private int radius = 200;
    private int width = 10;
    private int startAngle = 0;
    private int angleSpeed = 1;

    int ringX;
    int ringY;
    int ringRadius;
    int ringWidth;
    int headBallX;
    int headBallY;
    Paint paint;
    Shader sweepGradient;
    //private static final int RED = 230, GREEN = 85, BLUE = 35; //基础颜色，这里是橙红色
    private static final int RED = 230, GREEN = 0, BLUE = 0; //基础颜色，这里是橙红色
    private static final int MIN_ALPHA = 0; //最小不透明度
    private static final int MAX_ALPHA = 255; //最大不透明度

    private static int[] changeColors = new int[]{
            Color.argb(MIN_ALPHA, RED, GREEN, BLUE),
            Color.argb(MIN_ALPHA, RED, GREEN, BLUE),
            Color.argb(MAX_ALPHA, RED, GREEN, BLUE),
//            Color.TRANSPARENT,
    };


    public MyTest(Context context) {
        super(context);

        init(ballX , ballY + 300, radius, width, Color.RED);

    }

    public MyTest(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(ballX , ballY + 300, radius, width, Color.RED);
    }

    public MyTest(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(ballX , ballY + 300, radius, width, Color.RED);
    }


    public void init(int ringX, int ringY, int ringRadius, int ringWidth, int ringColor){
        this.ringX = ringX;
        this.ringY =  ringY;
        this.ringRadius =  ringRadius;
        this.ringWidth=  ringWidth;
        paint = new Paint();
        paint.reset();
        paint.setColor(ringColor);
        paint.setAntiAlias(true); //消除锯齿
        paint.setStrokeWidth(ringWidth);
        paint.setStyle(Paint.Style.STROKE);  //绘制空心圆或 空心矩形,只显示边缘的线，不显示内部

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    startAngle += angleSpeed;
                    if (startAngle >= 360)
                        startAngle = 0;
                    Log.d("MyTest", "startAngle:" + startAngle);
                //        startAngle %= 360;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }
        }).start();

    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("MyTest", "ringRadius:" + ringRadius);

        canvas.save();
        canvas.rotate(startAngle,ringX,ringY);

        paint.reset();
        RectF rect = new RectF(ringX - ringRadius, ringY - ringRadius, ringX + ringRadius, ringY + ringRadius);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(ringWidth);
        paint.setStyle(Paint.Style.STROKE);
        //设置渐变
        sweepGradient = new SweepGradient(ringX, ringY, changeColors, null);
        //按照圆心旋转
        Matrix matrix = new Matrix();
        matrix.setRotate(startAngle, ringX, ringY);
        sweepGradient.setLocalMatrix(matrix);
        paint.setShader(sweepGradient);
        canvas.drawArc(rect, 0, 360, false, paint);
        //绘制进度环开头的小圆点
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(MAX_ALPHA, RED, GREEN, BLUE));
        //使用三角函数时，需要把角度转为弧度
        headBallX = ringX + (int)(ringRadius * Math.cos((double)startAngle/180 * Math.PI));
        Log.e("degree", "degree: " + startAngle + "cos: " + Math.cos((double)startAngle/180 * Math.PI));
        headBallY = ringY + (int)(ringRadius * Math.sin((double)startAngle/180 * Math.PI));
        canvas.drawCircle(headBallX, headBallY, ringWidth/ 2, paint);
        canvas.restore();
    }
}
