package com.ruitong.huiyi3.view;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2018/7/7.
 */

public class DiBuView extends View {
    private Paint dPaint,xPaint1,xPaint2,xPaint3,wtPaint,allxian1,allxian2;
    private TextPaint zPaint;
    private RectF rect1,rect2,rect3,wtRect;
    private PaintFlagsDrawFilter paintFlagsDrawFilter=new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private StaticLayout staticLayout;

    private void init() {
        dPaint=new Paint();
        dPaint.setColor(Color.parseColor("#566af2"));
        dPaint.setStrokeWidth(6f);
        //舞台画笔
        wtPaint=new Paint();
        wtPaint.setStyle(Paint.Style.FILL);
        wtPaint.setColor(Color.parseColor("#FF7490D6"));
        wtRect=new RectF();
        //舞台字画笔
        zPaint=new TextPaint();
        zPaint.setColor(Color.BLUE);
        zPaint.setTextSize(40); //以px为单位
        zPaint.setTextAlign(Paint.Align.CENTER);
        staticLayout= new StaticLayout("舞台",zPaint,40, Layout.Alignment.ALIGN_CENTER,1.0F,0.0F,true);

        xPaint1=new Paint();
        xPaint1.setColor(Color.parseColor("#FFF3B32C"));
        xPaint1.setStyle(Paint.Style.STROKE);
        xPaint1.setStrokeWidth(6);rect1=new RectF();
        xPaint2=new Paint();
        xPaint2.setColor(Color.parseColor("#FF06C916"));
        xPaint2.setStyle(Paint.Style.STROKE);rect2=new RectF();
        xPaint2.setStrokeWidth(6);
        xPaint3=new Paint();
        xPaint3.setColor(Color.parseColor("#FFFF0000"));
        xPaint3.setStyle(Paint.Style.STROKE);rect3=new RectF();
        xPaint3.setStrokeWidth(6);

        //框线paint
        allxian1=new Paint();
        allxian1.setColor(Color.parseColor("#566af2"));
        allxian1.setStrokeWidth(1f);
        allxian2=new Paint();
        allxian2.setColor(Color.BLUE);
        allxian2.setStrokeWidth(2f);

    }

    public DiBuView(Context context) {
        super(context);
        init();
        Log.d("DiBuView", "ddddd");
    }



    public DiBuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        Log.d("DiBuView", "ddddd2");
    }

    public DiBuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        Log.d("DiBuView", "ddddd3");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(paintFlagsDrawFilter);
        int w= getMeasuredWidth();
        int h=getMeasuredHeight();
        Log.d("DiBuView", "getMeasuredWidth():" +w);
        Log.d("DiBuView", "getMeasuredHeight():" + h);
        float r = w / 200f;

        //开始绘制
        canvas.drawCircle(20, 20, r, dPaint);

        //三个框(大框)
        rect1.set(r*10,8*r,w-w/9-r*10,h-r*8);
        canvas.drawRect(rect1,xPaint1);
        //媒体框
        rect2.set((w-w/9-r*10)/3,(h-r*8)/2,(w-w/9-r*10)*2/3,h-r*8);
        canvas.drawRect(rect2,xPaint2);
        //VIP
        rect3.set((w-w/9-r*10)*2/3,8*r,w-w/9-r*10,h-r*8);
        canvas.drawRect(rect3,xPaint3);
        //vip里面的框(第一排)
        canvas.drawLine(w-w/9f-r*10-r*2,h/7f,w-w/9f-r*10-r*2,h*6/7f,allxian1);
        canvas.drawLine(w-w/9f-r*10-r*4,h/7f,w-w/9f-r*10-r*4,h*6/7f,allxian1);
        canvas.drawLine(w-w/9f-r*10-r*4,h/7f,w-w/9f-r*10-r*2,h/7f,allxian1);
        canvas.drawLine(w-w/9f-r*10-r*4,h*6/7f,w-w/9f-r*10-r*2,h*6/7f,allxian1);
        canvas.drawLine(w-w/9-r*10-r*4,(h/7f)+(h*6/7f-h/7f)/9,w-w/9-r*10-r*2,(h/7f)+(h*6/7f-h/7f)/9,allxian2);
        canvas.drawLine(w-w/9-r*10-r*4,(h/7f)+(h*6/7f-h/7f)*2/9,w-w/9-r*10-r*2,(h/7f)+(h*6/7f-h/7f)*2/9,allxian2);
        canvas.drawLine(w-w/9-r*10-r*4,(h/7f)+(h*6/7f-h/7f)*3/9,w-w/9-r*10-r*2,(h/7f)+(h*6/7f-h/7f)*3/9,allxian2);
        canvas.drawLine(w-w/9-r*10-r*4,(h/7f)+(h*6/7f-h/7f)*4/9,w-w/9-r*10-r*2,(h/7f)+(h*6/7f-h/7f)*4/9,allxian2);
        canvas.drawLine(w-w/9-r*10-r*4,(h/7f)+(h*6/7f-h/7f)*5/9,w-w/9-r*10-r*2,(h/7f)+(h*6/7f-h/7f)*5/9,allxian2);
        canvas.drawLine(w-w/9-r*10-r*4,(h/7f)+(h*6/7f-h/7f)*6/9,w-w/9-r*10-r*2,(h/7f)+(h*6/7f-h/7f)*6/9,allxian2);
        canvas.drawLine(w-w/9-r*10-r*4,(h/7f)+(h*6/7f-h/7f)*7/9,w-w/9-r*10-r*2,(h/7f)+(h*6/7f-h/7f)*7/9,allxian2);
        canvas.drawLine(w-w/9-r*10-r*4,(h/7f)+(h*6/7f-h/7f)*8/9,w-w/9-r*10-r*2,(h/7f)+(h*6/7f-h/7f)*8/9,allxian2);
        //圆点
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, h/7f+r*3f, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, h*6/7f-r*3f, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*2/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*3/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*4/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*5/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*6/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*7/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*8/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*9/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*10/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*11/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*12/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*13/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*14/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*15/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*16/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f)*17/18, r, dPaint);
        canvas.drawCircle(w-w/9f-r*10-r*4-r/2-r, (h/7f+r*3f)+(h*6/7f-r*6f-h/7f), r, dPaint);
        //第二排



      //  canvas.drawLine(w-w/9-r*10-r*4,(h*6/7),w-w/9-r*10-r*2,(h*6/7)*2/9,allxian2);

        //对位置有影响，最后绘制
        //舞台
        wtRect.set(w-w/9,h/8,w,h*7/8);
        canvas.drawRect(wtRect,wtPaint);
        canvas.translate((w-w/9)+w/18,h/2-40);
        //舞台字
        staticLayout.draw(canvas);
    }


}
