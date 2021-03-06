package com.example.sanjiaoji.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;


import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.YuvImage;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;

import com.example.sanjiaoji.MyApplication;
import com.example.sanjiaoji.R;
import com.example.sanjiaoji.camera.CameraManager;
import com.example.sanjiaoji.camera.CameraPreview;
import com.example.sanjiaoji.camera.CameraPreviewData;

import com.example.sanjiaoji.model.MenBean;

import com.example.sanjiaoji.utils.CustomerEngine;

import com.example.sanjiaoji.utils.FacePassUtil;

import com.example.sanjiaoji.utils.FileUtil;
import com.example.sanjiaoji.utils.GsonUtil;
import com.example.sanjiaoji.utils.SettingVar;
import com.example.sanjiaoji.utils.SharedPreferenceHelper;
import com.example.sanjiaoji.utils.camera.CameraHelper;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yf.humansensor.humansensor_manager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import megvii.facepass.FacePassException;
import megvii.facepass.FacePassHandler;
import megvii.facepass.types.FacePassDetectionResult;
import megvii.facepass.types.FacePassFace;
import megvii.facepass.types.FacePassImage;
import megvii.facepass.types.FacePassImageType;
import megvii.facepass.types.FacePassRecognitionResult;
import megvii.facepass.types.FacePassRecognitionResultType;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity implements CameraManager.CameraListener {
    private static final String TAG = "PreviewActivity";
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.rlrlrl)
    RelativeLayout rlrlrl;
   // @BindView(R.id.myface)
   // MyFaceview myface;
    @BindView(R.id.dabg)
    ImageView dabg;
    @BindView(R.id.logo)
    ImageView logo;
  //  long l1=0;
   // long l2=0;
    private CameraHelper cameraHelper;
    //private DrawHelper drawHelper;
    //private Camera.Size previewSize;
   // private FaceEngine faceEngine;
   // private int afCode = -1;
    private int dw, dh;
    private int fd;
    /* SDK 实例对象 */
  private   FacePassHandler mFacePassHandler;
    /* 相机实例 */
    private CameraManager manager;
    /* 相机预览界面 */
    private CameraPreview cameraView;
    private static final int cameraWidth = 1024;
    private static final int cameraHeight = 600;

    private static boolean isLink = true;
    private long tID = -1;
    private ConcurrentHashMap<Long, Integer> concurrentHashMap = new ConcurrentHashMap<Long, Integer>();
    /*DetectResult queue*/
   private ArrayBlockingQueue<byte[]> mDetectResultQueue;
   private ArrayBlockingQueue<CameraPreviewData> mFeedFrameQueue;


    // private BaoCunBean baoCunBean = null;
   // private Box<BaoCunBean> baoCunBeanDao = null;
   // private static Vector<Long> longList = new Vector<>();
    private final Timer timer = new Timer();
    private TimerTask task;
    private SharedPreferenceHelper sharedPreferencesHelper = null;
    private String screen_token = null;
    private String url="";
   // private TanChuangThread tanChuangThread;
  //  private LinkedBlockingQueue<MenBean> linkedBlockingQueue;
    private  final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(TIMEOUT2, TimeUnit.MILLISECONDS)
            .connectTimeout(TIMEOUT2, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT2, TimeUnit.MILLISECONDS)
            //  .cookieJar(new CookiesManager())
            // .retryOnConnectionFailure(true)
            .build();


    WeakHandler weakHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 111:
                    //弹窗
                    Log.d(TAG, "收到what消息");
                    MenBean menBean = (MenBean) msg.obj;
                    name.setText(menBean.getPerson().getTag().getName());
                    rlrlrl.setVisibility(View.VISIBLE);
                    dabg.setVisibility(View.GONE);
                    logo.setVisibility(View.GONE);
                    //启动定时器或重置定时器
                    if (task != null) {
                        task.cancel();
                        //timer.cancel();
                        task = new TimerTask() {
                            @Override
                            public void run() {
                                Message message = new Message();
                                message.what = 999;
                                weakHandler.sendMessage(message);
                            }
                        };
                        timer.schedule(task, 5000);

                    } else {
                        task = new TimerTask() {
                            @Override
                            public void run() {
                                Message message = new Message();
                                message.what = 999;
                                weakHandler.sendMessage(message);
                            }
                        };
                        timer.schedule(task, 5000);
                    }


                    //红外状态,value判断
                    //  int value = humansensor_manager.get_gpio1_value(fd);


                    //开继电器，即闸机开关
                    humansensor_manager.set_gpio3_value(fd, 1);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(500);

                            //关继电器
                            humansensor_manager.set_gpio3_value(fd, 0);

//                            SystemClock.sleep(5000);
//                            try {
//                                longList.remove(0);
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }

                        }
                    }).start();


                    break;

                case 999:
                    logo.setVisibility(View.VISIBLE);
                    dabg.setVisibility(View.VISIBLE);
                    rlrlrl.setVisibility(View.INVISIBLE);
                  //  if (longList.size() > 0)
                 //       longList.remove(0);
                    //关灯
                //    humansensor_manager.set_gpio2_value(fd, 0);
                    //关继电器
                 //   humansensor_manager.set_gpio3_value(fd, 0);

                    break;

            }


            return false;
        }
    });


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        EventBus.getDefault().register(this);//订阅

     //   linkedBlockingQueue = new LinkedBlockingQueue<>();
        mDetectResultQueue = new ArrayBlockingQueue<byte[]>(5);
        mFeedFrameQueue = new ArrayBlockingQueue<CameraPreviewData>(1);

        sharedPreferencesHelper = new SharedPreferenceHelper(
                MainActivity.this, "xiaojun");


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SettingVar.mHeight = displayMetrics.heightPixels;
        SettingVar.mWidth = displayMetrics.widthPixels;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        dw = dm.widthPixels;
        dh = dm.heightPixels;
        Log.d(TAG, "dw:" + dw);
        Log.d(TAG, "dh:" + dh);

        //myface.setDate(dw, dh);

      //  baoCunBeanDao = MyApplication.myApplication.getBoxStore().boxFor(BaoCunBean.class);

      //  FaceEngine faceEngine = new FaceEngine();
       // int activeCode = faceEngine.active(MainActivity.this, Constants.APP_ID, Constants.SDK_KEY);
      //  Log.d(TAG, "activeCode:" + activeCode);

        //previewView = findViewById(R.id.texture_preview);
        // faceRectView = findViewById(R.id.face_rect_view);
        //private View previewView;
        //  private static boolean isLink1 = true;
        ImageView ceshi = findViewById(R.id.ceshi);
        ceshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SheZhiActivity.class));
            }
        });

        //虹软的SharedPreferences
      //  ConfigUtil.setFtOrient(MainActivity.this, 1);
        fd = humansensor_manager.open();

        manager = new CameraManager();
        cameraView =  findViewById(R.id.preview);
        manager.setPreviewDisplay(cameraView);
        /* 注册相机回调函数 */
        manager.setListener(this);


        quanxian();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlrlrl.getLayoutParams();
        params.height = (int) (dh * 0.3f);
        rlrlrl.setLayoutParams(params);
        rlrlrl.invalidate();

        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) cameraView.getLayoutParams();
        params2.topMargin = (int) (dh * 0.3f);
        params2.height = (int) (dh * 0.7f);
        cameraView.setLayoutParams(params2);
        cameraView.invalidate();


        /*recognize thread*/
        RecognizeThread mRecognizeThread = new RecognizeThread();
        mRecognizeThread.start();
        FeedFrameThread mFeedFrameThread = new FeedFrameThread();
        mFeedFrameThread.start();
    }

//    private void initEngine() {
//
//
//        faceEngine = new FaceEngine();
//        afCode = faceEngine.init(this.getApplicationContext(), FaceEngine.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(this),
//                16, 10, FaceEngine.ASF_FACE_DETECT);
//        VersionInfo versionInfo = new VersionInfo();
//        faceEngine.getVersion(versionInfo);
//        Log.i(TAG, "initEngine:  init: " + afCode + "  version:" + versionInfo);
//        if (afCode != ErrorInfo.MOK) {
//            Toast.makeText(this, afCode + "初始化代码", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void unInitEngine() {
//
//        if (linkedBlockingQueue != null) {
//            linkedBlockingQueue.clear();
//        }
//
////        if (tanChuangThread != null) {
////            tanChuangThread.isRing = true;
////            tanChuangThread.interrupt();
////        }
//
//        if (afCode == 0) {
//            afCode = faceEngine.unInit();
//            Log.i(TAG, "unInitEngine: " + afCode);
//        }
//    }

//    @Override
//    public void onPictureTaken(final CameraPreviewData cameraPreviewData) {
//
//        final List<FaceInfo> faceInfoList = new ArrayList<>();
//        int code = faceEngine.detectFaces(cameraPreviewData.nv21Data, cameraWidth, cameraHeight, FaceEngine.CP_PAF_NV21, faceInfoList);
//        if (code != ErrorInfo.MOK && faceInfoList.size() <= 0) {
//
//            return;
//        }
////                if (code == ErrorInfo.MOK && faceInfoList.size() > 0) {
////                    code = faceEngine.process(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList, processMask);
////                    if (code != ErrorInfo.MOK) {
////                        return;
////                    }
////                }else {
////                    return;
////                }
//
//
//        if (isLink1) {
//
//            isLink1 = false;
//            if (faceInfoList.size() == 0) {
//                myface.setVisibility(View.GONE);
//                isLink1 = true;
//                return;
//            } else {
//                myface.setVisibility(View.VISIBLE);
//            }
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                    int cwidth = 640;
//                    int cheight = 480;
//
//                    // Log.d(TAG, "cwidth:" + cwidth);
//                    // Log.d(TAG, "cheight:" + cheight);
////                            if (cwidth == 0) {
////                                isLink1 = true;
////                                return;
////                            }
//
//                    try {
//
//                        YuvImage image2 = new YuvImage(cameraPreviewData.nv21Data, ImageFormat.NV21, cwidth, cheight, null);
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        image2.compressToJpeg(new Rect(0, 0, cwidth, cheight), 100, stream);
//                        final Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
//                        stream.close();
//
//                        for (FaceInfo faceInfo : faceInfoList) {
//                            //头像加宽加高点
//                            Rect rect = new Rect(faceInfo.getRect().left - 40 < 0 ? 0 : faceInfo.getRect().left - 40,
//                                    faceInfo.getRect().top - 100 < 0 ? 0 : faceInfo.getRect().top - 100,
//                                    faceInfo.getRect().right + 40 > cwidth ?
//                                            cwidth : faceInfo.getRect().right + 40,
//                                    faceInfo.getRect().bottom + 100 > cheight
//                                            ? cheight : faceInfo.getRect().bottom + 100);
//                            int x1, y1, x2, y2 = 0;
//                            x1 = rect.left;
//                            y1 = rect.top;
//                            //是宽高，不是坐标
//                            x2 = (rect.left + (rect.right - rect.left)) > cwidth ? (int) (cwidth - rect.left) : (int) (rect.right - rect.left);
//                            y2 = (rect.top + (rect.bottom - rect.top)) > cheight ? (int) (cheight - rect.top) : (int) (rect.bottom - rect.top);
//                            //截取单个人头像
//                            final Bitmap bitmap = Bitmap.createBitmap(bmp, x1, y1, x2, y2);
//                            //Log.d(TAG, "bitmap.getWidth():" + bitmap.getWidth());
//
//                            File file=null;
//                            try {
//                                file=compressImage(bitmap);
//                                link_P2(file);
//                            }catch (Exception e){
//                                if (file!=null)
//                                    file.delete();
//                                isLink1 = true;
//                            }
//
//                        }
//                    } catch (IOException e) {
//                        isLink1 = true;
//                        Log.d(TAG, e.getMessage() + "yiccvcvc");
//                    }
//
//                }
//            }).start();
//        }
//
//
//    }


//    private class TanChuangThread extends Thread {
//        boolean isRing;
//
//        @Override
//        public void run() {
//            while (!isRing) {
//                try {
//                    //有动画 ，延迟到一秒一次
//                    MenBean menBean = linkedBlockingQueue.take();
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        @Override
//        public void interrupt() {
//            isRing = true;
//            super.interrupt();
//        }
//    }



//    private void initCamera() {
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//
//        CameraListener cameraListener = new CameraListener() {
//            @Override
//            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
//                Log.i(TAG, "onCameraOpened: " + cameraId + "  " + displayOrientation + " " + isMirror);
//                previewSize = camera.getParameters().getPreviewSize();
//              //  drawHelper = new DrawHelper(previewSize.width, previewSize.height, previewView.getWidth(), previewView.getHeight(), displayOrientation
//                   //     , cameraId, isMirror);
//            }
//
//
//            @Override
//            public void onPreview(final byte[] nv21, final Camera camera) {
////
////
////                if (faceRectView != null) {
////                    faceRectView.clearFaceInfo();
////                }
//
//                final List<FaceInfo> faceInfoList = new ArrayList<>();
//                int code = faceEngine.detectFaces(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList);
//                if (code != ErrorInfo.MOK && faceInfoList.size() <= 0) {
//
//                    return;
//                }
////                if (code == ErrorInfo.MOK && faceInfoList.size() > 0) {
////                    code = faceEngine.process(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList, processMask);
////                    if (code != ErrorInfo.MOK) {
////                        return;
////                    }
////                }else {
////                    return;
////                }
//
//
//                if (isLink1) {
//                    isLink1 = false;
//                    if (faceInfoList.size() == 0) {
//                        myface.setVisibility(View.GONE);
//                        isLink1 = true;
//                        return;
//                    } else {
//                        myface.setVisibility(View.VISIBLE);
//                    }
//
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            int cwidth = 640;
//                            int cheight = 480;
//
//                            // Log.d(TAG, "cwidth:" + cwidth);
//                            // Log.d(TAG, "cheight:" + cheight);
////                            if (cwidth == 0) {
////                                isLink1 = true;
////                                return;
////                            }
//
//                            try {
//
//                                YuvImage image2 = new YuvImage(nv21, ImageFormat.NV21, cwidth, cheight, null);
//                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                                image2.compressToJpeg(new Rect(0, 0, cwidth, cheight), 100, stream);
//                                final Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
//                                stream.close();
//
//                                for (FaceInfo faceInfo : faceInfoList) {
//                                    //头像加宽加高点
//                                    Rect rect = new Rect(faceInfo.getRect().left - 40 < 0 ? 0 : faceInfo.getRect().left - 40,
//                                            faceInfo.getRect().top - 100 < 0 ? 0 : faceInfo.getRect().top - 100,
//                                            faceInfo.getRect().right + 40 > cwidth ?
//                                                    cwidth : faceInfo.getRect().right + 40,
//                                            faceInfo.getRect().bottom + 100 > cheight
//                                                    ? cheight : faceInfo.getRect().bottom + 100);
//                                    int x1, y1, x2, y2 = 0;
//                                    x1 = rect.left;
//                                    y1 = rect.top;
//                                    //是宽高，不是坐标
//                                    x2 = (rect.left + (rect.right - rect.left)) > cwidth ? (int) (cwidth - rect.left) : (int) (rect.right - rect.left);
//                                    y2 = (rect.top + (rect.bottom - rect.top)) > cheight ? (int) (cheight - rect.top) : (int) (rect.bottom - rect.top);
//                                    //截取单个人头像
//                                    final Bitmap bitmap = Bitmap.createBitmap(bmp, x1, y1, x2, y2);
//                                    //Log.d(TAG, "bitmap.getWidth():" + bitmap.getWidth());
//
//                                    File file=null;
//                                    try {
//                                        file=compressImage(bitmap);
//                                        link_P2(file);
//                                    }catch (Exception e){
//                                        if (file!=null)
//                                            file.delete();
//                                        isLink1 = true;
//                                    }
//
//                                }
//                            } catch (IOException e) {
//                                isLink1 = true;
//                                Log.d(TAG, e.getMessage() + "yiccvcvc");
//                            }
//
//                        }
//                    }).start();
//                }
//
//
//            }
//
//            @Override
//            public void onCameraClosed() {
//                Log.i(TAG, "onCameraClosed: ");
//            }
//
//            @Override
//            public void onCameraError(Exception e) {
//                Log.i(TAG, "onCameraError: " + e.getMessage());
//            }
//
//            @Override
//            public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {
////                if (drawHelper != null) {
////                    drawHelper.setCameraDisplayOrientation(displayOrientation);
////                }
//                Log.i(TAG, "onCameraConfigurationChanged: " + cameraID + "  " + displayOrientation);
//            }
//        };
//
//        cameraHelper = new CameraHelper.Builder()
//                .metrics(metrics)
//                .rotation(0)
//                .specificCameraId(0)
//                .isMirror(false)
//                .previewOn(previewView)
//                .cameraListener(cameraListener)
//                .build();
//        cameraHelper.init();
//
//    }



    /* 相机回调函数 */
    @Override
    public void onPictureTaken(CameraPreviewData cameraPreviewData) {
        mFeedFrameQueue.offer(cameraPreviewData);
    }

    private class FeedFrameThread extends Thread {
        boolean isInterrupt;

        @Override
        public void run() {
            while (!isInterrupt) {
                CameraPreviewData cameraPreviewData = null;
                try {
                    cameraPreviewData = mFeedFrameQueue.take();
                    // Log.d("takenpicture", "takenpicture");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    continue;
                }
                if (mFacePassHandler == null) {
                   // Log.d("ddd", "mFacepass handler = null");
                    continue;
                }

                final FacePassImage image;
                try {
                    image = new FacePassImage(cameraPreviewData.nv21Data, cameraPreviewData.width, cameraPreviewData.height, 0, FacePassImageType.NV21);
                } catch (FacePassException e) {
                    e.printStackTrace();
                    continue;
                }

                /* 将每一帧FacePassImage 送入SDK算法， 并得到返回结果 */
                FacePassDetectionResult detectionResult = null;
                try {
                    detectionResult = mFacePassHandler.feedFrame(image);

                } catch (FacePassException e) {
                    e.printStackTrace();
                }

                if (detectionResult != null && detectionResult.faceList.length > 0) {
                    //开灯
                  //  humansensor_manager.set_gpio2_value(fd, 1);
                    Log.d("ffffff", "fdsfsf");
                    showFacePassFace(detectionResult.faceList, image);

//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            myface.setVisibility(View.VISIBLE);
//                        }
//                    });
                }else {
                    //关灯
                    humansensor_manager.set_gpio2_value(fd, 0);
                }

//                else {
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            myface.setVisibility(View.GONE);
//                        }
//                    });
//                }

            /*离线模式，将识别到人脸的，message不为空的result添加到处理队列中*/
            if (detectionResult != null && detectionResult.message.length != 0) {
                // Log.d(DEBUG_TAG, "mDetectResultQueue.offer");
                mDetectResultQueue.offer(detectionResult.message);
               // Log.d("ddd", "1 mDetectResultQueue.size = " + mDetectResultQueue.size());
            }
            //else {
                // Log.d(DEBUG_TAG, "mDetectResultQueue.offer null");
          //  }


            }
        }

        @Override
        public void interrupt() {
            isInterrupt = true;
            super.interrupt();
        }
    }

    private class RecognizeThread extends Thread {

        boolean isInterrupt;

        @Override
        public void run() {
            while (!isInterrupt) {
                try {

                    byte[] detectionResult = mDetectResultQueue.take();

                  //  Log.d("ddddddd", "mDetectResultQueue.recognize");

                    FacePassRecognitionResult[] recognizeResult = mFacePassHandler.recognize("facepasstestx", detectionResult);
                    if (recognizeResult != null && recognizeResult.length > 0) {
                        for (FacePassRecognitionResult result : recognizeResult) {
                           // String faceToken = new String(result.faceToken);
                            if (FacePassRecognitionResultType.RECOG_OK == result.facePassRecognitionResultType) {
                                Log.d(TAG, "ddd识别");
                            }else {
                              //  Log.d("RecognizeThread", "未识别");
                                //未识别的
                                // 防止concurrentHashMap 数据过多 ,超过一定数据 删除没用的
                                if (concurrentHashMap.size() > 10) {
                                    concurrentHashMap.clear();
                                }
                                if (concurrentHashMap.get(result.trackId) == null) {
                                    //找不到新增
                                    concurrentHashMap.put(result.trackId, 1);
                                } else {
                                    //找到了 把value 加1
                                    concurrentHashMap.put(result.trackId, (concurrentHashMap.get(result.trackId)) + 1);
                                }
                                //判断次数超过3次
                                if (concurrentHashMap.get(result.trackId) == 1) {
                                    tID = result.trackId;
                                    isLink = true;
                                    //   Log.d("RecognizeThread", "入库"+tID);
                                }
                            }

                        }
                    }

                } catch (InterruptedException | FacePassException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void interrupt() {
            isInterrupt = true;
            super.interrupt();
        }
    }


    private void quanxian() {

        PermissionGen.with(MainActivity.this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)

                .request();

    }

    private void showFacePassFace(FacePassFace[] detectResult, final FacePassImage image) {

        for (FacePassFace face : detectResult) {
            boolean mirror = false; /* 前摄像头时mirror为true */
            Matrix mat = new Matrix();
            int w = cameraView.getMeasuredWidth();
            int h = cameraView.getMeasuredHeight();

            int cameraHeight = manager.getCameraheight();
            int cameraWidth = manager.getCameraWidth();

            float left = 0;
            float top = 0;
            float right = 0;
            float bottom = 0;
            switch (90) {
                case 0:
                    left = face.rect.left;
                    top = face.rect.top;
                    right = face.rect.right;
                    bottom = face.rect.bottom;
                    mat.setScale(mirror ? -1 : 1, 1);
                    mat.postTranslate(mirror ? (float) cameraWidth : 0f, 0f);
                    mat.postScale((float) w / (float) cameraWidth, (float) h / (float) cameraHeight);
                    break;
                case 90:
                    mat.setScale(mirror ? -1 : 1, 1);
                    mat.postTranslate(mirror ? (float) cameraHeight : 0f, 0f);
                    mat.postScale((float) w / (float) cameraHeight, (float) h / (float) cameraWidth);
                    left = face.rect.top;
                    top = cameraWidth - face.rect.right;
                    right = face.rect.bottom;
                    bottom = cameraWidth - face.rect.left;
                    break;
                case 180:
                    mat.setScale(1, mirror ? -1 : 1);
                    mat.postTranslate(0f, mirror ? (float) cameraHeight : 0f);
                    mat.postScale((float) w / (float) cameraWidth, (float) h / (float) cameraHeight);
                    left = face.rect.right;
                    top = face.rect.bottom;
                    right = face.rect.left;
                    bottom = face.rect.top;
                    break;
                case 270:
                    mat.setScale(mirror ? -1 : 1, 1);
                    mat.postTranslate(mirror ? (float) cameraHeight : 0f, 0f);
                    mat.postScale((float) w / (float) cameraHeight, (float) h / (float) cameraWidth);
                    left = cameraHeight - face.rect.bottom;
                    top = face.rect.left;
                    right = cameraHeight - face.rect.top;
                    bottom = face.rect.right;
            }

            RectF drect = new RectF();
            RectF srect = new RectF(left, top, right, bottom);
            mat.mapRect(drect, srect);

            //头像加宽加高点
            RectF srect2 = new RectF(face.rect.left - 40 < 0 ? 0 : face.rect.left - 40, face.rect.top - 100 < 0 ? 0 : face.rect.top - 100,
                    face.rect.right + 40 > image.width ? image.width : face.rect.right + 40, face.rect.bottom + 100 > image.height ? image.height : face.rect.bottom + 100);


            float pitch = face.pose.pitch;
            float roll = face.pose.roll;
            float yaw = face.pose.yaw;
            //  Log.d("MainActivity203", "pitch:" + pitch);
            //  Log.d("MainActivity203", "roll:" + roll);
            //  Log.d("MainActivity203", "yaw:" + yaw);
            if (pitch < 30 && pitch > -30 && roll < 30 && roll > -30 && yaw < 30 && yaw > -30 && face.blur < 0.4) {
                try {
                    //  Log.d("MainActivity203", "tID:" + tID);
                    //  Log.d("MainActivity203", "face.trackId:" + face.trackId);
                    //   Log.d("MainActivity203", "isLink:" + isLink);
                    if (tID == face.trackId && isLink) {  //入库成功后将 tID=-1;
                        isLink = false;
                        tID = -1;
                    //    l1=System.currentTimeMillis();
                       // Log.d("MainActivity203", "进来");
                        //获取图片
                        YuvImage image2 = new YuvImage(image.image, ImageFormat.NV21, image.width, image.height, null);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        image2.compressToJpeg(new Rect(0, 0, image.width, image.height), 100, stream);
                        final Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
                        stream.close();

                        int x1, y1, x2, y2 = 0;
                        x1 = (int) srect2.left;
                        y1 = (int) srect2.top;
                        //是宽高，不是坐标
                        x2 = (srect2.left + (srect2.right - srect2.left)) > image.width ? (int) (image.width - srect2.left) : (int) (srect2.right - srect2.left);
                        y2 = (srect2.top + (srect2.bottom - srect2.top)) > image.height ? (int) (image.height - srect2.top) : (int) (srect2.bottom - srect2.top);
                        //截取单个人头像
                        final Bitmap bitmap = Bitmap.createBitmap(bmp, x1, y1, x2, y2);

                        Bitmap fileBitmap = FileUtil.adjustPhotoRotation(bitmap, 270);

                        File file=null;
                        try {
                            Log.d("fffffff", "dadasad");
                            file=compressImage(fileBitmap);
                            link_P2(file);
                        }catch (Exception e){
                            Log.d("ffffgggg", e.getMessage());
                            if (file!=null)
                                file.delete();
                            isLink = true;
                        }


                    }


                } catch (Exception ex) {
                    isLink = true;
                    Log.e("Sys", "Error:" + ex.getMessage());
                }

            }
        }


    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        // Toast.makeText(this, "Contact permission is granted", Toast.LENGTH_SHORT).show();
        //开启副屏
        Log.d("MainActivity", "fffff");

       // initEngine();
      //  initCamera();

       // FacePassHandler.getAuth(authIP, apiKey, apiSecret);
        FacePassHandler.initSDK(getApplicationContext());

        FacePassUtil util=new FacePassUtil();
        util.init(MainActivity.this, getApplicationContext(),  null);


        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivity(intent);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(20000);
                        EventBus.getDefault().post("quanxian");
                    }
                }).start();
            } else {

                CustomerEngine.getInstance(getApplicationContext(), MainActivity.this,dw,dh);

          }
        } else {
            CustomerEngine.getInstance(getApplicationContext(), MainActivity.this, dw, dh);
        }

//        if (cameraHelper != null) {
//            cameraHelper.start();
//        }
       // Log.d(TAG, "gggg");
       // manager.open(getWindowManager(), false, cameraWidth, cameraHeight);
        manager.open(getWindowManager(), true, cameraWidth, cameraHeight);
    }

    //广播
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(String event) {

        if (event.equals("quanxian")) {
            CustomerEngine.getInstance(getApplicationContext(), MainActivity.this, dw, dh);
            return;
        }

        if (event.equals("mFacePassHandler")) {
            mFacePassHandler = MyApplication.myApplication.getFacePassHandler();
            return;
        }
        Toast.makeText(this, event, Toast.LENGTH_LONG).show();
    }


    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        Log.d("MainActivity", "dddddd");
        Toast.makeText(this, "Contact permission is not granted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        if (cameraHelper != null) {
            cameraHelper.release();
            cameraHelper = null;
        }

     //   unInitEngine();

        EventBus.getDefault().unregister(this);//解除订阅

        super.onDestroy();
    }


    @Override
    protected void onResume() {
       // baoCunBean = baoCunBeanDao.get(123456L);

        screen_token = sharedPreferencesHelper.getSharedPreference("screen_token", "").toString().trim();
        url = sharedPreferencesHelper.getSharedPreference("url", "http://192.168.2.61").toString().trim();


        Log.d(TAG, url);
       // Log.d(TAG, baoCunBean.getTouxiangzhuji() + "hhh");
       // Log.d(TAG, screen_token);
        super.onResume();

    }

    private static final int TIMEOUT2 = 1000 * 5;

    // 1:N 对比
    private void link_P2(final File file) {
        if (screen_token.equals("") || url.equals("")) {
            Log.d("rrrrrr", "gfdgfdg3333");
            file.delete();

          //  SystemClock.sleep(1000);
            isLink=true;
            return;
        }

        Log.d("rrrrrr", "etetetert");

        MultipartBody mBody;
        final MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream"), file);

        builder.addFormDataPart("image", file.getName(), fileBody1);
        builder.addFormDataPart("screen_token", screen_token);
        mBody = builder.build();

        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .post(mBody)
                .url(url + ":8866/recognize");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                file.delete();
              //  Log.d("CustomerDisplay", "file.delete():" + );
                Log.d("AllConnects", "请求识别失败" + e.getMessage()+call.request().url());
                Runtime.getRuntime().gc();
             //   SystemClock.sleep(1100);
                isLink=true;
                mFacePassHandler.reset();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("AllConnects", "请求识别成功" + call.request().toString());
                //获得返回体
                String s2="";
                try {
                    ResponseBody body = response.body();
                    String ss = body.string();
                    Log.d("AllConnects", "传照片" + ss);
                     s2 = ss.replace("\\\\u", "@!@#u").replace("\\", "")
                            .replace("tag\": \"{", "tag\":{")
                            .replace("jpg\"}\"", "jpg\"}")
                            .replace("@!@#", "\\")
                            .replace("0}\"", "0}");


                        Log.d("AllConnects", "传照片2" + s2);

                    JsonObject jsonObject = GsonUtil.parse(s2).getAsJsonObject();
                    Gson gson = new Gson();
                    MenBean menBean = gson.fromJson(jsonObject, MenBean.class);


                    if (menBean.isCan_door_open() || (menBean.getPerson() != null && menBean.getPerson().getConfidence() > 75)) {
//                        counts=0;
//                        Message message=Message.obtain();
//                        message.arg1=1;
//                        message.obj=menBean;
//                        handler.sendMessage(message);
                    //    Log.d("CustomerDisplay", "识别");

                        Message message = Message.obtain();
                        message.obj = menBean;
                        message.what = 111;
                        weakHandler.sendMessage(message);

//                        synchronized (okHttpClient){
//                            if (longList.size()==0){
//
//                                longList.add(Long.valueOf(menBean.getPerson().getId()));
//                            }
//                            boolean is=false;
//                            for (Long ll : longList){
//                                if (ll.equals(Long.valueOf(menBean.getPerson().getId()))){
//                                    is=true;
//                                    break;
//                                }
//                            }
//                            if (!is){
//                                Message message = Message.obtain();
//                                message.obj = menBean;
//                                message.what = 111;
//                                weakHandler.sendMessage(message);
//                                longList.add(Long.valueOf(menBean.getPerson().getId()));
//                            }
//                        }
                      //  linkedBlockingQueue.offer(menBean);
                     //   Log.d("MainActivity", "longList.size():" + linkedBlockingQueue.size());
//                        if (longList.size() == 0) {
//                            longList.add(Long.valueOf(menBean.getPerson().getId()));
//                            //开始弹窗
//                            // menBean.setBitmap(bitmap);
//                            Message message = Message.obtain();
//                            message.obj = menBean;
//                            message.what = 111;
//                            weakHandler.sendMessage(message);
//
//                        }
//
//
//                        for (long ll : longList) {
//                            if (ll != Long.valueOf(menBean.getPerson().getId())) {
//                                longList.add(Long.valueOf(menBean.getPerson().getId()));
//                                //开始弹窗
//                                // menBean.setBitmap(bitmap);
//                                Message message = Message.obtain();
//                                message.obj = menBean;
//                                message.what = 111;
//                                weakHandler.sendMessage(message);
//                                //开闸机
//
//                            } else {
//                                SystemClock.sleep(300);
//                                isLink1 = true;
//                            }
//
//                        }
                       // Log.d("MainActivity", "longList.size():" + longList.size());

                    } else {
                       // SystemClock.sleep(300);
                      //  isLink1 = true;
                        Log.d("CustomerDisplay", "陌生人222");

                     //   if (menBean.getError() == 7) {
                            //陌生人频率过快稍微降低点
//                            counts++;
                       //     Log.d("CustomerDisplay", "陌生人");
//                            if (counts>=2){
//                                counts=0;
//                                if (isMsr){
//                                    isYG=false;
//                                    isMsr=false;
//                                    Message message=Message.obtain();
//                                    message.what=2;
//                                    message.obj=new BitmapsBean(bitmap.copy(Bitmap.Config.ARGB_8888,false));
//                                    handler.sendMessage(message);
//                                }
//                            }

                     //   }
                    }


                } catch (Exception e) {
                    try {
                        String s3=s2.replace("null}\"","null}");
                        JsonObject jsonObject = GsonUtil.parse(s3).getAsJsonObject();
                        Gson gson = new Gson();
                        MenBean menBean = gson.fromJson(jsonObject, MenBean.class);

                        if (menBean.isCan_door_open() || (menBean.getPerson() != null && menBean.getPerson().getConfidence() > 75)) {

                            Message message = Message.obtain();
                            message.obj = menBean;
                            message.what = 111;
                            weakHandler.sendMessage(message);

                        } else {
                            // SystemClock.sleep(300);
                            //  isLink1 = true;
                            Log.d("CustomerDisplay", "陌生人222");
                        }

                    }catch (Exception ee){
                        ee.printStackTrace();
                        mFacePassHandler.reset();
                    }
                    Log.d("WebsocketPushMsg", e.getMessage() + "json异常");

                } finally {
                    file.delete();
                    Runtime.getRuntime().gc();
                   // SystemClock.sleep(1000);
                    isLink=true;
                   // l2=System.currentTimeMillis();
                  //  Log.d("MainActivity", "l2-l1:" + (l2 - l1));
                }

            }
        });
    }

    /**
     * 压缩图片（质量压缩）
     *
     * @param bitmap
     */
    private static File compressImage(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory()+File.separator+"sanjiaoji", "mm"+System.currentTimeMillis() + ".png");//将要保存图片的路径
        try {

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage() + "ffffgg");
        } finally {

            if (bitmap != null){
                bitmap.recycle();
                bitmap=null;
            }

        }
        return file;

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        while (baos.toByteArray().length / 1024 > 300) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
//            baos.reset();//重置baos即清空baos
//            options -= 10;//每次都减少10
//            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
//            //long length = baos.toByteArray().length;
//        }
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date(System.currentTimeMillis());
//        String filename = format.format(date);
//        File file = new File(Environment.getExternalStorageDirectory(),filename+".png");
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            try {
//                fos.write(baos.toByteArray());
//                fos.flush();
//                fos.close();
//            } catch (IOException e) {
//
//                e.printStackTrace();
//            }
//        } catch (FileNotFoundException e) {
//
//            e.printStackTrace();
//        }
//        //	recycleBitmap(bitmap);
//        return file;
    }




    public static class MyReceiver extends BroadcastReceiver {
        public MyReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                Intent i = new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }

}
