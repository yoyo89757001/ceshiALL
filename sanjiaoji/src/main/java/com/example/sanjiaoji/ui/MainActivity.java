package com.example.sanjiaoji.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
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

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.VersionInfo;
import com.badoo.mobile.util.WeakHandler;

import com.example.sanjiaoji.R;

import com.example.sanjiaoji.model.MenBean;
import com.example.sanjiaoji.utils.ConfigUtil;
import com.example.sanjiaoji.utils.Constants;
import com.example.sanjiaoji.utils.CustomerEngine;

import com.example.sanjiaoji.utils.GsonUtil;
import com.example.sanjiaoji.utils.SettingVar;
import com.example.sanjiaoji.utils.SharedPreferenceHelper;
import com.example.sanjiaoji.utils.camera.CameraHelper;
import com.example.sanjiaoji.utils.camera.CameraListener;
import com.example.sanjiaoji.view.MyFaceview;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PreviewActivity";
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.rlrlrl)
    RelativeLayout rlrlrl;
    @BindView(R.id.myface)
    MyFaceview myface;
    @BindView(R.id.dabg)
    ImageView dabg;
    @BindView(R.id.logo)
    ImageView logo;
    private CameraHelper cameraHelper;
    //private DrawHelper drawHelper;
    private Camera.Size previewSize;
    private FaceEngine faceEngine;
    private int afCode = -1;
    private int dw, dh;
    private int fd;
 //   private int processMask = FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS;

    /**
     * 相机预览显示的控件，可为SurfaceView或TextureView
     */
    private View previewView;
    // private FaceRectView faceRectView;
    private static boolean isLink1 = true;
    private ImageView ceshi;
   // private BaoCunBean baoCunBean = null;
   // private Box<BaoCunBean> baoCunBeanDao = null;
    private static Vector<Long> longList = new Vector<>();
    private final Timer timer = new Timer();
    private TimerTask task;
    private SharedPreferenceHelper sharedPreferencesHelper = null;
    private String screen_token = null;
    private String url="";
    private TanChuangThread tanChuangThread;
    private LinkedBlockingQueue<MenBean> linkedBlockingQueue;


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
                    //开灯
                    humansensor_manager.set_gpio2_value(fd, 1);

                    //开继电器，即闸机开关
                    humansensor_manager.set_gpio3_value(fd, 1);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(500);
                            //关灯
                            humansensor_manager.set_gpio2_value(fd, 0);
                            //关继电器
                            humansensor_manager.set_gpio3_value(fd, 0);

                            SystemClock.sleep(4500);

                            synchronized (tanChuangThread){
                                longList.remove(0);
                            }

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

        linkedBlockingQueue = new LinkedBlockingQueue<>();


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

        myface.setDate(dw, dh);

      //  baoCunBeanDao = MyApplication.myApplication.getBoxStore().boxFor(BaoCunBean.class);

        FaceEngine faceEngine = new FaceEngine();
        int activeCode = faceEngine.active(MainActivity.this, Constants.APP_ID, Constants.SDK_KEY);
        Log.d(TAG, "activeCode:" + activeCode);

        previewView = findViewById(R.id.texture_preview);
        // faceRectView = findViewById(R.id.face_rect_view);
        ceshi = findViewById(R.id.ceshi);
        ceshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SheZhiActivity.class));
            }
        });

        //虹软的SharedPreferences
        ConfigUtil.setFtOrient(MainActivity.this, 1);
        fd = humansensor_manager.open();
        quanxian();

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlrlrl.getLayoutParams();
        params.height = (int) (dh * 0.3f);
        rlrlrl.setLayoutParams(params);
        rlrlrl.invalidate();

        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) previewView.getLayoutParams();
        params2.topMargin = (int) (dh * 0.3f);
        params2.height = (int) (dh * 0.7f);
        previewView.setLayoutParams(params2);
        previewView.invalidate();


        tanChuangThread=new TanChuangThread();
        tanChuangThread.start();


    }

    private void initEngine() {


        faceEngine = new FaceEngine();
        afCode = faceEngine.init(this.getApplicationContext(), FaceEngine.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(this),
                16, 10, FaceEngine.ASF_FACE_DETECT);
        VersionInfo versionInfo = new VersionInfo();
        faceEngine.getVersion(versionInfo);
        Log.i(TAG, "initEngine:  init: " + afCode + "  version:" + versionInfo);
        if (afCode != ErrorInfo.MOK) {
            Toast.makeText(this, afCode + "初始化代码", Toast.LENGTH_SHORT).show();
        }
    }

    private void unInitEngine() {

        if (linkedBlockingQueue != null) {
            linkedBlockingQueue.clear();
        }

        if (tanChuangThread != null) {
            tanChuangThread.isRing = true;
            tanChuangThread.interrupt();
        }

        if (afCode == 0) {
            afCode = faceEngine.unInit();
            Log.i(TAG, "unInitEngine: " + afCode);
        }
    }



    private class TanChuangThread extends Thread {
        boolean isRing;

        @Override
        public void run() {
            while (!isRing) {
                try {
                    //有动画 ，延迟到一秒一次
                    MenBean menBean = linkedBlockingQueue.take();
                    synchronized (TanChuangThread.this){
                        if (longList.size()==0){
                            Message message = Message.obtain();
                            message.obj = menBean;
                            message.what = 111;
                            weakHandler.sendMessage(message);
                            longList.add(Long.valueOf(menBean.getPerson().getId()));
                        }
                        boolean is=false;
                        for (Long ll : longList){
                            if (ll.equals(Long.valueOf(menBean.getPerson().getId()))){
                                is=true;
                                break;
                            }
                        }
                        if (!is){
                            Message message = Message.obtain();
                            message.obj = menBean;
                            message.what = 111;
                            weakHandler.sendMessage(message);
                            longList.add(Long.valueOf(menBean.getPerson().getId()));
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void interrupt() {
            isRing = true;
            super.interrupt();
        }
    }



    private void initCamera() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                Log.i(TAG, "onCameraOpened: " + cameraId + "  " + displayOrientation + " " + isMirror);
                previewSize = camera.getParameters().getPreviewSize();
              //  drawHelper = new DrawHelper(previewSize.width, previewSize.height, previewView.getWidth(), previewView.getHeight(), displayOrientation
                   //     , cameraId, isMirror);
            }


            @Override
            public void onPreview(final byte[] nv21, final Camera camera) {
//
//
//                if (faceRectView != null) {
//                    faceRectView.clearFaceInfo();
//                }


                final List<FaceInfo> faceInfoList = new ArrayList<>();
                int code = faceEngine.detectFaces(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList);
                if (code != ErrorInfo.MOK && faceInfoList.size() <= 0) {

                    return;
                }
//                if (code == ErrorInfo.MOK && faceInfoList.size() > 0) {
//                    code = faceEngine.process(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList, processMask);
//                    if (code != ErrorInfo.MOK) {
//                        return;
//                    }
//                }else {
//                    return;
//                }


                if (isLink1) {

                    isLink1 = false;
                    if (faceInfoList.size() == 0) {
                        myface.setVisibility(View.GONE);
                        isLink1 = true;
                        return;
                    } else {
                        myface.setVisibility(View.VISIBLE);
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            int cwidth = 640;
                            int cheight = 480;

                            // Log.d(TAG, "cwidth:" + cwidth);
                            // Log.d(TAG, "cheight:" + cheight);
//                            if (cwidth == 0) {
//                                isLink1 = true;
//                                return;
//                            }

                            try {

                                YuvImage image2 = new YuvImage(nv21, ImageFormat.NV21, cwidth, cheight, null);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                image2.compressToJpeg(new Rect(0, 0, cwidth, cheight), 100, stream);
                                final Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
                                stream.close();

                                for (FaceInfo faceInfo : faceInfoList) {
                                    //头像加宽加高点
                                    Rect rect = new Rect(faceInfo.getRect().left - 40 < 0 ? 0 : faceInfo.getRect().left - 40,
                                            faceInfo.getRect().top - 100 < 0 ? 0 : faceInfo.getRect().top - 100,
                                            faceInfo.getRect().right + 40 > cwidth ?
                                                    cwidth : faceInfo.getRect().right + 40,
                                            faceInfo.getRect().bottom + 100 > cheight
                                                    ? cheight : faceInfo.getRect().bottom + 100);
                                    int x1, y1, x2, y2 = 0;
                                    x1 = rect.left;
                                    y1 = rect.top;
                                    //是宽高，不是坐标
                                    x2 = (rect.left + (rect.right - rect.left)) > cwidth ? (int) (cwidth - rect.left) : (int) (rect.right - rect.left);
                                    y2 = (rect.top + (rect.bottom - rect.top)) > cheight ? (int) (cheight - rect.top) : (int) (rect.bottom - rect.top);
                                    //截取单个人头像
                                    final Bitmap bitmap = Bitmap.createBitmap(bmp, x1, y1, x2, y2);
                                    //Log.d(TAG, "bitmap.getWidth():" + bitmap.getWidth());


                                    File file=null;
                                    try {
                                        file=compressImage(bitmap);
                                        link_P2(file);
                                    }catch (Exception e){
                                        if (file!=null)
                                            file.delete();
                                        isLink1 = true;
                                    }

                                }
                            } catch (IOException e) {
                                isLink1 = true;
                                Log.d(TAG, e.getMessage() + "yiccvcvc");
                            }

                        }
                    }).start();
                }


            }

            @Override
            public void onCameraClosed() {
                Log.i(TAG, "onCameraClosed: ");
            }

            @Override
            public void onCameraError(Exception e) {
                Log.i(TAG, "onCameraError: " + e.getMessage());
            }

            @Override
            public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {
//                if (drawHelper != null) {
//                    drawHelper.setCameraDisplayOrientation(displayOrientation);
//                }
                Log.i(TAG, "onCameraConfigurationChanged: " + cameraID + "  " + displayOrientation);
            }
        };

        cameraHelper = new CameraHelper.Builder()
                .metrics(metrics)
                .rotation(0)
                .specificCameraId(0)
                .isMirror(false)
                .previewOn(previewView)
                .cameraListener(cameraListener)
                .build();
        cameraHelper.init();
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


    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        // Toast.makeText(this, "Contact permission is granted", Toast.LENGTH_SHORT).show();
        //开启副屏
        Log.d("MainActivity", "fffff");

        initEngine();
        initCamera();


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
              // CustomerEngine.getInstance(getApplicationContext(), MainActivity.this,dw,dh);
            }
        } else {
          //  CustomerEngine.getInstance(getApplicationContext(), MainActivity.this,dw,dh);
        }

        if (cameraHelper != null) {
            cameraHelper.start();
        }


    }

    //广播
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(String event) {
        if (event.equals("quanxian")) {
           // CustomerEngine.getInstance(getApplicationContext(), MainActivity.this,dw,dh);
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
        unInitEngine();
        EventBus.getDefault().unregister(this);//解除订阅
        super.onDestroy();
    }


    @Override
    protected void onResume() {
       // baoCunBean = baoCunBeanDao.get(123456L);

        screen_token = sharedPreferencesHelper.getSharedPreference("screen_token", "").toString().trim();
        url = sharedPreferencesHelper.getSharedPreference("url", "http://192.168.2.2").toString().trim();


        Log.d(TAG, url);
       // Log.d(TAG, baoCunBean.getTouxiangzhuji() + "hhh");
       // Log.d(TAG, screen_token);
        super.onResume();

    }

    private static final int TIMEOUT2 = 1000 * 5;

    // 1:N 对比
    private void link_P2(final File file) {
        if (screen_token.equals("") || url.equals("")) {
            //Log.d(TAG, "gfdgfdg3333");
            file.delete();

            SystemClock.sleep(1000);
            isLink1=true;
            return;
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT2, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT2, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT2, TimeUnit.MILLISECONDS)
                //  .cookieJar(new CookiesManager())
                .retryOnConnectionFailure(true)
                .build();
        ;
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
                Log.d("AllConnects", "请求识别失败" + e.getMessage());
                SystemClock.sleep(1100);
                isLink1=true;

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("AllConnects", "请求识别成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string();
                  //  Log.d("AllConnects", "传照片" + ss);
                    String s2 = ss.replace("\\\\u", "@!@#u").replace("\\", "")
                            .replace("tag\": \"{", "tag\":{")
                            .replace("jpg\"}\"", "jpg\"}")
                            .replace("@!@#", "\\")
                            .replace("0}\"", "0}");


                    //    Log.d("AllConnects", "传照片2" + s2);

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

                        linkedBlockingQueue.offer(menBean);
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

                    Log.d("WebsocketPushMsg", e.getMessage() + "klklklkl");
                } finally {
                    file.delete();

                    SystemClock.sleep(700);
                    isLink1=true;
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
            if (bitmap != null)
                bitmap.recycle();
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
