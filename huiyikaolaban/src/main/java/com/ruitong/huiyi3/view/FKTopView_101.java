package com.ruitong.huiyi3.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ruitong.huiyi3.R;

import java.util.ArrayList;
import java.util.List;



public class FKTopView_101 extends View {
    private Paint centerPaint;
    private Paint spreadPaint;
    List<Integer> alphas=new ArrayList<>();
    List<Integer> spreadRadius=new ArrayList<>();
    private int centerX=0,centerY=0;
    private int radius=100; //头像的宽度
    private int radius2=60;
    private int distance=1; //扩散的快慢
    private int maxRadius=30;
    private Bitmap bitmap=null;
  //  private RectF rectF=new RectF();
  //  private RectF rectF2=new RectF();
    private Bitmap henfu= null;
    private int type=0;

    private int hight=0,width=0;
    private String name=null,bumen=null;
    private Bitmap bitmapTX=null;
    private Paint namePaint=new Paint();
    private Paint yPaint=new Paint();
    private RectF rectF=new RectF();
    private RectF rectF2=new RectF();
    private RectF rectFkuang=new RectF();
    private RectF rectFbg1=new RectF();
    private RectF rectFbg2=new RectF();
    private Bitmap bitmapHG=null;
    private Context context;
    private boolean ismaozi=false;
    private Bitmap bg1=BitmapFactory.decodeResource(getResources(), R.drawable.fkbg1);
    private Bitmap bg2=BitmapFactory.decodeResource(getResources(),R.drawable.fkbg2);

    public FKTopView_101(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public FKTopView_101(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }


    public FKTopView_101(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    public void setHight(int width,int hight){
        this.hight=hight;
        this.width=width;
        float bitmapR=((float) width*0.3f);
        rectF.set(width/2-bitmapR,hight*0.09f,width/2+bitmapR,hight*0.5f);
        rectFbg1.set(0,0,width,hight*0.6f);
        rectFbg2.set(0,hight*0.4f,width,hight);
        rectFkuang.set(width/2-bitmapR,hight*0.09f,width/2+bitmapR,hight*0.5f);
        rectF2.set(width*0.7f,hight*0.004f,width,hight*0.16f);
    }

    public void setBitmapTX(Bitmap bitmapTX){
        this.bitmapTX=bitmapTX;
    }

    public void setBitmapHG(){
        bitmapHG= BitmapFactory.decodeResource(context.getResources(), R.drawable.huangguan_tx);
       //

    }

    private void init(){
        namePaint.setAntiAlias(true);
        namePaint.setColor(Color.WHITE);

        yPaint.setAntiAlias(true);
        yPaint.setColor(Color.WHITE);
        yPaint.setStrokeWidth(2);
        yPaint.setStyle(Paint.Style.STROKE);

        henfu=BitmapFactory.decodeResource(context.getResources(), R.drawable.viphf_203);
        //画笔1:
        centerPaint = new Paint();
        centerPaint.setAntiAlias(true);//抗锯齿效果
        //最开始不透明且扩散距离为0
        alphas.add(255);
        spreadRadius.add(0);
        //画笔2:
        spreadPaint = new Paint();
        spreadPaint.setAntiAlias(true);
        spreadPaint.setAlpha(255);
        spreadPaint.setColor(Color.WHITE);


    }
    public void setName(String name,String bumen,boolean ismaozi){
        this.name=name;
        if (bumen==null || bumen.equals("")){
            this.bumen="访客";
        }else{
            this.bumen=bumen;
        }
        this.ismaozi=ismaozi;

    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
////        int w = MeasureSpec.getSize(widthMeasureSpec)-60;
////        int h = MeasureSpec.getSize(heightMeasureSpec)-60;
////        int size = Math.min(w, h);
////        setMeasuredDimension(size, size);
//
//
//    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //圆心位置
        centerX = w / 2;
        centerY = h / 2;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        for (int i = 0; i < spreadRadius.size(); i++) {
//            int alpha = alphas.get(i);
//            spreadPaint.setAlpha(alpha);
//            int width = spreadRadius.get(i);
//            //绘制扩散的圆
//            canvas.drawCircle(rectF.centerX(), rectF.centerY(), radius2 + width, spreadPaint);
//            //每次扩散圆半径递增，圆透明度递减
//            if (alpha > 0 && width < 300) {
//                alpha = alpha - distance > 0 ? alpha - distance : 0;
//                alphas.set(i, alpha);
//                spreadRadius.set(i, width + distance);
//            }
//        }
//        //当最外层扩散圆半径达到最大半径时添加新扩散圆
//        if (spreadRadius.get(spreadRadius.size() - 1) > maxRadius) {
//            spreadRadius.add(0);
//            alphas.add(255);
//        }
//        //超过8个扩散圆，删除最先绘制的圆，即最外层的圆
//        if (spreadRadius.size() >= 13) {
//            alphas.remove(0);
//            spreadRadius.remove(0);
//        }



        if (bg1!=null){
            canvas.drawBitmap(bg1,null,rectFbg1,null);
        }
        if (bitmapTX!=null){
            canvas.drawBitmap(bitmapTX,null,rectF,null);
            canvas.drawRect(rectFkuang,yPaint);
        }
        if (bg2!=null){
            canvas.drawBitmap(bg2,null,rectFbg2,null);
        }

        if (name!=null && rectF.bottom>0){
            namePaint.setTextSize(70);
            float jj=namePaint.measureText(name);
            canvas.drawText(name,width/2-jj/2,rectF.bottom+150,namePaint);
            namePaint.setTextSize(30);
            float kk=namePaint.measureText(bumen);
            canvas.drawText(bumen,width/2-kk/2,rectF.bottom+230,namePaint);
        }

        if (bitmapHG!=null && ismaozi){
            canvas.drawBitmap(bitmapHG,null,rectF2,null);
        }

        postInvalidateDelayed(10);



    }



}
