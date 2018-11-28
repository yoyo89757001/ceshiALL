package com.example.sanjiaoji.ui;

import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.VersionInfo;
import com.badoo.mobile.util.WeakHandler;

import com.bumptech.glide.Glide;
import com.example.sanjiaoji.R;

import com.example.sanjiaoji.model.MenBean;
import com.example.sanjiaoji.utils.ConfigUtil;
import com.example.sanjiaoji.utils.GsonUtil;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2018/6/8.
 */

public class CustomerDisplay extends Presentation {

    ImageView dabg;
    TextView name;
    RelativeLayout rlrlrl;
    MyFaceview myface;
    ImageView ceshi;
    private  TanChuangThread tanChuangThread;
    private final Activity activity;
    // private TextView tv;
    private final String TAG = this.getClass().getSimpleName();

  //  private int mWidth, mHeight, mFormat;
   // private Context context;
    private int dw, dh;
    private CameraHelper cameraHelper;
   // private DrawHelper drawHelper;
    private Camera.Size previewSize;
  //  private Integer cameraID = Camera.CameraInfo.CAMERA_FACING_BACK;
    private FaceEngine faceEngine;
    private int afCode = -1;
    private int fd;
  //  private int processMask = FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS;
    /**
     * 相机预览显示的控件，可为SurfaceView或TextureView
     */
    private View previewView;

    private static boolean isLink = true;
   // private ImageView ceshi;
   // private BaoCunBean baoCunBean = null;
   // private Box<BaoCunBean> baoCunBeanDao = null;
    private static Vector<Long> longList2 = new Vector<>();
    private final Timer timer = new Timer();
    private TimerTask task;
    private SharedPreferenceHelper sharedPreferencesHelper = null;
    private String screen_token = null;
    private ImageView logo;
    private String url="";
    private LinkedBlockingQueue<MenBean> linkedBlockingQueue;





    private WeakHandler weakHandler = new WeakHandler(  new Handler.Callback() {
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


//
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
                    humansensor_manager.set_gpio4_value(fd, 1);




                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SystemClock.sleep(500);
                                   //关灯
                                humansensor_manager.set_gpio2_value(fd, 0);
                                 //关继电器
                                humansensor_manager.set_gpio4_value(fd, 0);

                                SystemClock.sleep(4500);

                                synchronized (tanChuangThread){
                                    longList2.remove(0);
                                }

                            }
                        }).start();



                    break;

                case 999:
                    logo.setVisibility(View.VISIBLE);
                    dabg.setVisibility(View.VISIBLE);
                    rlrlrl.setVisibility(View.INVISIBLE);


//                    if (longList2.size() > 0)
//                        longList2.remove(0);
//                    //关灯
//                    humansensor_manager.set_gpio2_value(fd, 0);
//                    //关继电器
//                    humansensor_manager.set_gpio4_value(fd, 0);

                    break;

            }


            return false;
        }
    });


    public CustomerDisplay(Context outerContext, Display display, Activity activity,int w,int h) {
        super(outerContext, display);
       // context=outerContext;
        this.activity = activity;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        dw = dm.widthPixels;
        dh = dm.heightPixels;
        linkedBlockingQueue = new LinkedBlockingQueue<>();

        Log.d("CustomerDisplay22", "dw:" + dw);
        Log.d("CustomerDisplay22", "dh:" + dh);

        //baoCunBeanDao = MyApplication.myApplication.getBoxStore().boxFor(BaoCunBean.class);
        //baoCunBean = baoCunBeanDao.get(123456L);


        setContentView(R.layout.view_display_customer);
        //ScreenAdapterTools.getInstance().reset(this);//如果希望android7.0分屏也适配的话,加上这句
        //在setContentView();后面加上适配语句
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        EventBus.getDefault().register(this);//订阅

        previewView = findViewById(R.id.texture_preview);

       // FaceEngine faceEngine = new FaceEngine();
      //  int activeCode = faceEngine.active(mContext, Constants.APP_ID, Constants.SDK_KEY);
      //  Log.d(TAG, "activeCode222:" + activeCode);
        logo=findViewById(R.id.logo);

      //  ceshi=findViewById(R.id.ceshi);

        sharedPreferencesHelper = new SharedPreferenceHelper(
                activity, "xiaojun");

        screen_token = sharedPreferencesHelper.getSharedPreference("screen_token", "").toString().trim();
        url = sharedPreferencesHelper.getSharedPreference("url", "http://192.168.2.2").toString().trim();


        previewView = findViewById(R.id.texture_preview);
        //ceshi = findViewById(R.id.ceshi);
        name=findViewById(R.id.name);
        rlrlrl=findViewById(R.id.rlrlrl);
        myface=findViewById(R.id.myface);
        dabg=findViewById(R.id.dabg);


        Log.d("CustomerDisplay", "dw2:" + dw);
        Log.d("CustomerDisplay", "dh2:" + dh);


        fd = humansensor_manager.open();

        //虹软的SharedPreferences
        ConfigUtil.setFtOrient(activity, 1);

        initEngine();
        initCamera();

//
        if (cameraHelper != null) {
            cameraHelper.start();
        }

        tanChuangThread=new TanChuangThread();
        tanChuangThread.start();

    }

    @Override
    public void show() {
        super.show();

        myface.setDate(dw,dh);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlrlrl.getLayoutParams();
        params.height = (int) (dh * 0.3f);
        rlrlrl.setLayoutParams(params);
        rlrlrl.invalidate();
        Log.d("CustomerDisplay", "dw5:" + dw);
        Log.d("CustomerDisplay", "dh5:" + dh);
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) previewView.getLayoutParams();
        params2.topMargin = (int) (dh * 0.3f);;
        params2.height = (int) (dh * 0.7f);
        previewView.setLayoutParams(params2);
        previewView.invalidate();



        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);



//        Window window = this.getWindow();
//        //重新设置
//        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
//         window .setGravity(Gravity.CENTER );
//        // lp.x = 100; // 新位置X坐标
////        lp.y = 100; // 新位置Y坐标
//        lp.width = dw; // 宽度
//        lp.height = dh; // 高度
//        //   lp.alpha = 0.7f; // 透明度
//        // dialog.onWindowAttributesChanged(lp);
//        //(当Window的Attributes改变时系统会调用此函数)
//        window .setAttributes(lp);




    }

    private void initEngine() {


        faceEngine = new FaceEngine();
        afCode = faceEngine.init(activity.getApplicationContext(), FaceEngine.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(activity),
                16, 10, FaceEngine.ASF_FACE_DETECT );
        VersionInfo versionInfo = new VersionInfo();
        faceEngine.getVersion(versionInfo);
        Log.i(TAG, "initEngine:  init:副屏" + afCode + "  version:" + versionInfo);
        if (afCode != ErrorInfo.MOK) {
            Toast.makeText(activity, afCode + "初始化代码2", Toast.LENGTH_SHORT).show();
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
                    if (longList2.size()==0){
                        Message message = Message.obtain();
                        message.obj = menBean;
                        message.what = 111;
                        weakHandler.sendMessage(message);
                        longList2.add(Long.valueOf(menBean.getPerson().getId()));
                    }
                    boolean is=false;
                    for (Long ll : longList2){
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
                        longList2.add(Long.valueOf(menBean.getPerson().getId()));
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

    private void initCamera() {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                Log.i(TAG, "onCameraOpened: " + cameraId + "  " + displayOrientation + " " + isMirror);
                previewSize = camera.getParameters().getPreviewSize();
                //drawHelper = new DrawHelper(previewSize.width, previewSize.height, previewView.getWidth(), previewView.getHeight(), displayOrientation
                      //  , cameraId, isMirror);
            }


            @Override
            public void onPreview(final byte[] nv21, Camera camera) {

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


                if (isLink) {

                    isLink = false;
                    if (faceInfoList.size() == 0) {
                        myface.setVisibility(View.GONE);
                        isLink = true;
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
//                                isLink = true;
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
//                                    activity.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            ceshi.setImageBitmap(bitmap);
//                                        }
//                                    });

                                    File file=null;
                                    try {
                                        file=compressImage(bitmap);
                                        link_P2(file);
                                    }catch (Exception e){
                                        if (file!=null)
                                            file.delete();
                                        isLink = true;
                                    }

                                }
                            } catch (IOException e) {

                                isLink = true;
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
                .specificCameraId(1)
                .isMirror(false)
                .previewOn(previewView)
                .cameraListener(cameraListener)
                .build();
        cameraHelper.init();


    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(String event) {
        // Log.d("CustomerDisplay", event+"ttttttttttttttttttttttt");
        if (event.equals("dizhidizhi")){
            screen_token = sharedPreferencesHelper.getSharedPreference("screen_token", "").toString().trim();
            url = sharedPreferencesHelper.getSharedPreference("url", "http://192.168.2.2").toString().trim();
              return;
        }

        Toast.makeText(activity, event, Toast.LENGTH_LONG).show();

    }

    private static final int TIMEOUT2 = 1000 * 5;

    // 1:N 对比
    private void link_P2(final File file) {
        if (screen_token.equals("")|| url.equals("")) {
            //Log.d(TAG, "gfdgfdg3333");
            file.delete();

            SystemClock.sleep(1000);
            isLink=true;
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

                Log.d("AllConnects", "请求识别失败" + e.getMessage());
                SystemClock.sleep(1100);
                isLink=true;

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
                     //   Log.d("CustomerDisplay", "识别");

                        linkedBlockingQueue.offer(menBean);

//
//                        if (longList2.size() == 0) {
//                            longList2.add(Long.valueOf(menBean.getPerson().getId()));
//                            //开始弹窗
//                           // menBean.setBitmap(bitmap);
//                            Message message = Message.obtain();
//                            message.obj = menBean;
//                            message.what = 111;
//                            weakHandler.sendMessage(message);
//
//                        }
//
//
//                        for (long ll : longList2) {
//                            if (ll != Long.valueOf(menBean.getPerson().getId())) {
//                                longList2.add(Long.valueOf(menBean.getPerson().getId()));
//                                //开始弹窗
//                               // menBean.setBitmap(bitmap);
//                                Message message = Message.obtain();
//                                message.obj = menBean;
//                                message.what = 111;
//                                weakHandler.sendMessage(message);
//                                //开闸机
//
//                            } else {
//                                SystemClock.sleep(300);
//                                isLink = true;
//                            }
//
//                        }
                     //   Log.d("MainActivity", "longList2.size():" + linkedBlockingQueue.size());

                    } else {
                       // SystemClock.sleep(300);
                       // isLink = true;
                        Log.d("CustomerDisplay", "陌生人222");

                      //  if (menBean.getError() == 7) {
                            //陌生人频率过快稍微降低点
//                            counts++;
                         //   Log.d("CustomerDisplay", "陌生人");
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

                    //    }
                    }

                } catch (Exception e) {
                  //  SystemClock.sleep(300);
                   // isLink = true;
                    Log.d("WebsocketPushMsg", e.getMessage() + "klklklkl");


                } finally {
                    Log.d("CustomerDisplay", "file.delete():" + file.delete());
                    SystemClock.sleep(700);
                    isLink=true;
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
        File file = new File(Environment.getExternalStorageDirectory()+File.separator+"sanjiaoji", "cc"+System.currentTimeMillis() + ".png");//将要保存图片的路径
        try {

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            Log.d("ggg", e.getMessage() + "ffffgg");
        }finally {
            if (bitmap!=null)
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


    @Override
    protected void onStop() {

        super.onStop();

    }

    @Override
    public void onDisplayRemoved() {
        if (cameraHelper != null) {
            cameraHelper.release();
            cameraHelper = null;
        }
        unInitEngine();

        EventBus.getDefault().unregister(this);//解除订阅
        super.onDisplayRemoved();
    }


}