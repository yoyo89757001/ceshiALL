package com.example.advanceSuggest;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.advanceSuggest.camera.CameraManager;
import com.example.advanceSuggest.camera.CameraPreview;
import com.example.advanceSuggest.camera.CameraPreviewData;
import com.example.advanceSuggest.camera.NV21ToBitmap;
import com.example.advanceSuggest.camera.SettingVar;
import com.lansoeditor.demo.R;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class CameraActivity extends Activity implements CameraManager.CameraListener {
    private CameraPreview cameraPreview;
    private CameraManager manager;
    private NV21ToBitmap nv21ToBitmap;
    private static boolean isAAA =false;
    private int type =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        type =getIntent().getIntExtra("type",0);
        cameraPreview=findViewById(R.id.camera);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightPixels = displayMetrics.heightPixels;
        int widthPixels = displayMetrics.widthPixels;
        SettingVar.mHeight = heightPixels;
        SettingVar.mWidth = widthPixels;

        manager = new CameraManager();
        manager.setPreviewDisplay(cameraPreview);
        manager.setListener(this);
        nv21ToBitmap=new NV21ToBitmap(CameraActivity.this);

    }



    public void kkk(View view){
        isAAA =true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.open(getWindowManager(), true, 1080, 720);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAAA =false;
    }

    @Override
    public void onPictureTaken(CameraPreviewData cameraPreviewData) {
        if (isAAA){
            isAAA=false;
            long time =System.currentTimeMillis();
            Bitmap fileBitmap = nv21ToBitmap.nv21ToBitmap(cameraPreviewData.nv21Data,cameraPreviewData.width,cameraPreviewData.height);
            String paths= Environment.getExternalStorageDirectory().getAbsolutePath();
            fileBitmap=adjustPhotoRotation(fileBitmap,270);

            boolean tt= nv21ToBitmap.saveBitmap(fileBitmap,paths,time+".png");
            if (tt){
                Events events =new Events();
                events.setPath(paths+File.separator+time+".png");
                events.setType(type);
                EventBus.getDefault().post(events);
                finish();
            }
        }


    }

    public static   Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {
        try {
            int width = bm.getWidth();
            int height = bm.getHeight();
            Matrix matrix = new Matrix();
            matrix.setRotate(orientationDegree, (float) width / 2, (float) height / 2);
            float targetX = 0;
            float targetY = 0;
            if (orientationDegree == 90 || orientationDegree == 270) {
                if (width > height) {
                    targetX = (float) height / 2 - (float) width / 2;
                    targetY = 0 - targetX;
                } else {
                    targetY = (float) width / 2 - (float) height / 2;
                    targetX = 0 - targetY;
                }
            }
            matrix.postTranslate(targetX, targetY);
            Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);

            Paint paint = new Paint();
            Canvas canvas = new Canvas(bm1);
            canvas.drawBitmap(bm, matrix, paint);

            return bm1;
        } catch (OutOfMemoryError | Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveBitmap2File2(Bitmap bm, final String path, int quality) {
        if (null == bm || bm.isRecycled()) {
            Log.d("InFoActivity", "回收|空");
            return;
        }
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.PNG, quality, bos);
            bos.flush();
            bos.close();

        } catch (Exception e) {
            e.printStackTrace();

        }finally {
            bm.recycle();
            bm=null;
        }
    }
}
