package com.ruitong.huiyi3.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.badoo.mobile.util.WeakHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruitong.huiyi3.MyApplication;
import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.adapter.DemoAdapter;
import com.ruitong.huiyi3.beans.BaoCunBean;


import com.ruitong.huiyi3.beans.KaoLaBeans;
import com.ruitong.huiyi3.beans.ShiBieBean;



import com.ruitong.huiyi3.beans.WBBean;
import com.ruitong.huiyi3.beans.WeiShiBieBean;

import com.ruitong.huiyi3.beans.XGBean;
import com.ruitong.huiyi3.service.AlarmReceiver;
import com.ruitong.huiyi3.utils.FileUtil;
import com.ruitong.huiyi3.utils.GsonUtil;
import com.ruitong.huiyi3.view.GlideCircleTransform;
import com.ruitong.huiyi3.view.GlideRoundTransform;
import com.ruitong.huiyi3.web.HttpServer;
import com.ruitong.huiyi3.web.MyWebServer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import io.objectbox.Box;
import okhttp3.OkHttpClient;
import sun.misc.BASE64Decoder;


public class MainActivitykuangshi2 extends AppCompatActivity  {

    private LinearLayoutManager manager;
    private LottieAnimationView wangluo;
    private static WebSocketClient webSocketClient = null;
    private ImageView shezhiTv;
    private final Timer timer = new Timer();
    private TimerTask task;
    private RelativeLayout top_relaLayout;
    private static boolean isLianJie = true;
    private RequestOptions myOptions = new RequestOptions()
            .fitCenter()
            .error(R.drawable.erroy_bg)
            .transform(new GlideCircleTransform(2, Color.parseColor("#ffffff")));

    private RequestOptions myOptions2 = new RequestOptions()
            .fitCenter()
            .error(R.drawable.ren)
            //   .transform(new GlideCircleTransform(MyApplication.myApplication, 2, Color.parseColor("#ffffffff")));
            .transform(new GlideRoundTransform(MainActivitykuangshi2.this, 10));

    private LinkedBlockingQueue<ShiBieBean.PersonBeanSB> linkedBlockingQueue;
    /* 人脸识别Group */
    LinkedBlockingQueue<Toast> mToastBlockQueue;
    TanChuangThread tanChuangThread;
    private int dw, dh;
    private Box<BaoCunBean> baoCunBeanDao = null;
    private BaoCunBean baoCunBean = null;
    private IntentFilter intentFilter;
    private TimeChangeReceiver timeChangeReceiver;
    private WeakHandler mHandler;
    private NetWorkStateReceiver netWorkStateReceiver = null;
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(20000, TimeUnit.MILLISECONDS)
            .connectTimeout(20000, TimeUnit.MILLISECONDS)
            .readTimeout(20000, TimeUnit.MILLISECONDS)
//				    .cookieJar(new CookiesManager())
            //     .retryOnConnectionFailure(true)
            .build();
    private static WebsocketPushMsg websocketPushMsg = null;
   // private static boolean isDH = false;
   // private ShiBieBean.PersonBeanSB subjectli;
    private ImageView dialogBg;
    private RecyclerView mRecyclerView;
    private List<ShiBieBean.PersonBeanSB> personBeanSBList=new ArrayList<>();
    private DemoAdapter adapter=null;
    private int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        mToastBlockQueue = new LinkedBlockingQueue<>();
        baoCunBeanDao = MyApplication.myApplication.getBaoCunBeanBox();
        baoCunBean = baoCunBeanDao.get(123456L);

        //网络状态关闭
        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetWorkStateReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netWorkStateReceiver, filter);
        }

        intentFilter=new IntentFilter();

        intentFilter.addAction("duanxianchonglian");
        intentFilter.addAction("gxshipingdizhi");
        intentFilter.addAction("shoudongshuaxin");
        intentFilter.addAction("updateGonggao");
        intentFilter.addAction("updateTuPian");
        intentFilter.addAction("updateShiPing");
        intentFilter.addAction("delectShiPing");
        intentFilter.addAction("guanbi");


        new Thread(new Runnable() {
            @Override
            public void run() {

                SystemClock.sleep(10000);
                sendBroadcast(new Intent(MainActivitykuangshi2.this, AlarmReceiver.class));

            }
        }).start();

        intentFilter.addAction(Intent.ACTION_TIME_TICK);//每分钟变化
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);//设置了系统时区
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);//设置了系统时间
        timeChangeReceiver = new TimeChangeReceiver();
        registerReceiver(timeChangeReceiver, intentFilter);

        linkedBlockingQueue = new LinkedBlockingQueue<>();

        EventBus.getDefault().register(this);//订阅
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        dw = dm.widthPixels;
        dh = dm.heightPixels;

        websocketPushMsg = new WebsocketPushMsg();

        /* 初始化界面 */
        initView();
        wangluo =  findViewById(R.id.wangluo);
        wangluo.setSpeed(1.8f);

        tanChuangThread = new TanChuangThread();
        tanChuangThread.start();


        mHandler = new WeakHandler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 111: {
                        final ShiBieBean.PersonBeanSB bean = (ShiBieBean.PersonBeanSB) msg.obj;
                          //subjectli = (ShiBieBean.PersonBeanSB) msg.obj;
                          top_relaLayout.removeAllViews();

                        final View view1 = View.inflate(MainActivitykuangshi2.this, R.layout.dialog2, null);
                        ImageView touxiang = view1.findViewById(R.id.touxiang);
                        TextView name = view1.findViewById(R.id.name);
                        TextView gongsi = view1.findViewById(R.id.gongsi);
                        TextView beifangren = view1.findViewById(R.id.beifangren);
                        ImageView gou = view1.findViewById(R.id.gou);
                        TextView yuyue = view1.findViewById(R.id.yuyue);
                        RelativeLayout bg = view1.findViewById(R.id.llllll);

                        top_relaLayout.addView(view1);

                        try {
                            if (bean.getAvatar() != null) {

                                Glide.with(MainActivitykuangshi2.this)
                                        .load(baoCunBean.getTouxiangzhuji()+bean.getAvatar())
                                        .apply(myOptions2)
                                        .into(touxiang);
                            }else {
                                Glide.get(MainActivitykuangshi2.this).clearMemory();
                                Glide.with(MainActivitykuangshi2.this)
                                        .load("http://www.123.cc.cc.cc.com")
                                        .apply(myOptions2)
                                        .into(touxiang);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (bean.getSubject_type()==-1) {
                            //-1陌生人
                            bg.setBackgroundResource(R.drawable.tc22);
                            yuyue.setTextColor(Color.parseColor("#ffe827"));
                            yuyue.setText("未预约");
                            gou.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.xxx));
                            beifangren.setText("请到保安亭进行预约登记");
                        } else {
                            gou.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.yyy));
                            bg.setBackgroundResource(R.drawable.tc11);
                            yuyue.setText("已预约");
                            yuyue.setTextColor(Color.parseColor("#00ff00"));
                            beifangren.setText("被访人:");
                        }
                        name.setText(bean.getName());
                        gongsi.setText("公司:");
                        beifangren.setText("被访人: "+ bean.getRemark());

                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bg.getLayoutParams();
                        params.width = (int) (dw*0.6);
                        params.height = (int) (dh*0.56);
                        dialogBg.setVisibility(View.VISIBLE);

                        //启动定时器或重置定时器
                        if (task != null) {
                            task.cancel();
                            //timer.cancel();
                            task = new TimerTask() {
                                @Override
                                public void run() {
                                    Message message = new Message();
                                    message.what = 222;
                                    mHandler.sendMessage(message);
                                }
                            };
                            timer.schedule(task, 4000);
                        } else {
                            task = new TimerTask() {
                                @Override
                                public void run() {
                                    Message message = new Message();
                                    message.what = 222;
                                    mHandler.sendMessage(message);
                                }
                            };
                            timer.schedule(task, 4000);
                        }

                        break;
                    }

                    case 222: {
                        //关掉弹窗
                        top_relaLayout.removeAllViews();
                        dialogBg.setVisibility(View.GONE);

                        break;

                    }

                }
                return false;
            }
        });


        guanPing();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpServer server = new HttpServer();
//                server.await();
//            }
//        }).start();

        try {
            MyWebServer server =new MyWebServer();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {

        super.onResume();
    }





    private class TanChuangThread extends Thread {
        boolean isRing;

        @Override
        public void run() {
            while (!isRing) {
                try {
                    //有动画 ，延迟到一秒一次

                    ShiBieBean.PersonBeanSB subject = linkedBlockingQueue.take();

                        switch (subject.getSubject_type()) {
                            case 0:

                                //普通打卡
                                Message messagey = Message.obtain();
                                messagey.what = 111;
                                messagey.obj = subject;
                                mHandler.sendMessage(messagey);

                                break;

                            case 1: {
                                //普通访客
                                Message message2 = Message.obtain();
                                message2.what = 666;
                                message2.obj = subject;
                                mHandler.sendMessage(message2);

                                break;
                            }
                            case 2:
                                //vip

                                Message message2 = Message.obtain();
                                message2.what = 111;
                                message2.obj = subject;
                                mHandler.sendMessage(message2);

                                break;
                            case -1:
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Glide.get(MainActivitykuangshi2.this).clearDiskCache();
                                    }
                                }).start();
                                Message message = Message.obtain();
                                message.obj = subject;
                                message.what = 111;
                                mHandler.sendMessage(message);

                                break;
                            case -2:

                                break;
                            default:
                                EventBus.getDefault().post("没有对应身份类型,无法弹窗");

                        }

                    SystemClock.sleep(600);

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


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(KaoLaBeans beans) {

        if (beans.getName().equals("")){//陌生人

            ShiBieBean.PersonBeanSB personBeanSB = new ShiBieBean.PersonBeanSB();
            personBeanSB.setName("陌生人");
            personBeanSB.setSubject_type(-1);
            personBeanSB.setId(System.currentTimeMillis());
            linkedBlockingQueue.offer(personBeanSB);

        }else {

            ShiBieBean.PersonBeanSB personBeanSB = new ShiBieBean.PersonBeanSB();
            personBeanSB.setRemark(beans.getInterviewees());
            personBeanSB.setName(beans.getName());
            personBeanSB.setAvatar(beans.getHeadImage());
            personBeanSB.setSubject_type(0);
            personBeanSB.setId(System.currentTimeMillis());
            linkedBlockingQueue.offer(personBeanSB);
        }



            Log.d("MainActivitykuangshi2", beans.toString());


    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void initView() {

        setContentView(R.layout.fsdfsd);
        ButterKnife.bind(this);

        shezhiTv=findViewById(R.id.shezhi_tv);
        shezhiTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivitykuangshi2.this,SheZhiActivity.class));
               finish();
            }
        });
        dialogBg = findViewById(R.id.dialog_bg);

        mRecyclerView = findViewById(R.id.boot_layout);

        top_relaLayout = findViewById(R.id.top_layout);
        adapter = new DemoAdapter(MyApplication.getAppContext(),personBeanSBList);
        // 定义一个线性布局管理器
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setAdapter(adapter);



    }


    public void oncv(View view){
      //  Log.d("MainActivity102", "房贷首付");
        startActivity(new Intent(MainActivitykuangshi2.this,SheZhiActivity.class));
        finish();

    }


    @Override
    protected void onStop() {


        mToastBlockQueue.clear();

        linkedBlockingQueue.clear();



        super.onStop();
    }

    @Override
    protected void onRestart() {
        //faceView.clear();
        // faceView.invalidate();
        //  if (shipingView!=null)
        // shipingView.start();

        super.onRestart();
    }

    @Override
    public void onStart() {
        super.onStart();

        //marqueeView.startFlipping();
    }

    @Override
    protected void onDestroy() {
        if (timer != null)
            timer.cancel();
        if (task != null)
            task.cancel();

        if (mToastBlockQueue != null) {
            mToastBlockQueue.clear();
        }
        if (linkedBlockingQueue != null) {
            linkedBlockingQueue.clear();
        }


        if (tanChuangThread != null) {
            tanChuangThread.isRing = true;
            tanChuangThread.interrupt();
        }

        EventBus.getDefault().unregister(this);//解除订阅

        //  shipingView.release(true);
        unregisterReceiver(timeChangeReceiver);
        unregisterReceiver(netWorkStateReceiver);



        super.onDestroy();
    }




    private static final int REQUEST_CODE_CHOOSE_PICK = 1;


    //图片转为二进制数据
    public byte[] bitmabToBytes2(Bitmap bitmap) {
        //将图片转化为位图
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        //创建一个字节数组输出流,流的大小为size
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        try {
            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //将字节数组输出流转化为字节数组byte[]
            return baos.toByteArray();
        } catch (Exception ignored) {
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //从相册选取照片后读取地址
            case REQUEST_CODE_CHOOSE_PICK:
                if (resultCode == RESULT_OK) {
                    String path = "";
                    Uri uri = data.getData();
                    String[] pojo = {MediaStore.Images.Media.DATA};
                    CursorLoader cursorLoader = new CursorLoader(this, uri, pojo, null, null, null);
                    Cursor cursor = cursorLoader.loadInBackground();
                    if (cursor != null) {
                        cursor.moveToFirst();
                        path = cursor.getString(cursor.getColumnIndex(pojo[0]));
                    }
                    if (!TextUtils.isEmpty(path) && "file".equalsIgnoreCase(uri.getScheme())) {
                        path = uri.getPath();
                    }
                    if (TextUtils.isEmpty(path)) {
                        try {
                            path = FileUtil.getPath(getApplicationContext(), uri);
                        } catch (Exception e) {
                        }
                    }
                    if (TextUtils.isEmpty(path)) {
                        toast("图片选取失败！");
                        return;
                    }

                }
                break;
        }
    }


    private void toast(String msg) {
        Toast.makeText(MainActivitykuangshi2.this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                startActivity(new Intent(MainActivitykuangshi2.this, SheZhiActivity.class));
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }




    class TimeChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())) {
                case Intent.ACTION_TIME_TICK:


                    break;
                case Intent.ACTION_TIME_CHANGED:
                    //设置了系统时间
                    // Toast.makeText(context, "system time changed", Toast.LENGTH_SHORT).show();
                    break;
                case Intent.ACTION_TIMEZONE_CHANGED:
                    //设置了系统时区的action
                    //  Toast.makeText(context, "system time zone changed", Toast.LENGTH_SHORT).show();
                    break;
            }

            if (intent.getAction().equals("duanxianchonglian")) {
                //断线重连
                Log.d("ggg22", "刷脸监听");
                if (isLianJie) {
                  //  Log.d("ggg", "进来2");
                    try {

                      //  Log.d("ggg", baoCunBean.getTouxiangzhuji() + "ddddd");
                       // Log.d("ggg", baoCunBean.getShipingIP());
                        if (baoCunBean.getTouxiangzhuji() != null && baoCunBean.getShipingIP() != null) {
                          //  Log.d("ggg", "jin");
                            websocketPushMsg.startConnection(baoCunBean.getTouxiangzhuji(), baoCunBean.getShipingIP());
                        }
                    } catch (Exception e) {
                        Log.d("ggg", e.getMessage() + "aaa");

                    }
                }

            }

            if (intent.getAction().equals("guanbi")) {
                Log.d("ggg", "关闭");
                finish();
            }
        }
    }



    public class NetWorkStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                //获取ConnectivityManager对象对应的NetworkInfo对象
                //以太网
                NetworkInfo wifiNetworkInfo1 = connMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
                //获取WIFI连接的信息
                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //获取移动数据连接的信息
                NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (wifiNetworkInfo1.isConnected() || wifiNetworkInfo.isConnected() || dataNetworkInfo.isConnected()) {
                    // isNet = true;

                } else {
                    // isNet = false;
                }


                //				if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                //					Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
                //				} else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                //					Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
                //				} else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                //					Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
                //				} else {
                //					Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
                //				}
                //API大于23时使用下面的方式进行网络监听
            } else {

                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                //获取所有网络连接的信息
                Network[] networks = connMgr.getAllNetworks();
                //用于存放网络连接信息
                StringBuilder sb = new StringBuilder();
                //通过循环将网络信息逐个取出来
                //  Log.d(TAG, "networks.length:" + networks.length);
                if (networks.length == 0) {
                    //isNet = false;
                }
                for (int i = 0; i < networks.length; i++) {
                    //获取ConnectivityManager对象对应的NetworkInfo对象
                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);

                    if (networkInfo != null && networkInfo.isConnected()) {
                        //isNet = true;

                    }
                }

            }
        }
    }


    private void kaiPing(){

        sendBroadcast(new Intent("com.android.internal.policy.impl.showNavigationBar"));
        sendBroadcast(new Intent("com.android.systemui.statusbar.phone.statusopen"));
    }

    private void guanPing(){

        sendBroadcast(new Intent("com.android.internal.policy.impl.hideNavigationBar"));
        sendBroadcast(new Intent("com.android.systemui.statusbar.phone.statusclose"));
    }

    /**
     * 识别消息推送
     * 主机盒子端API ws://[主机ip]:9000/video
     * 通过 websocket 获取 识别结果
     *
     * @author Wangshutao
     */
    private class WebsocketPushMsg {

        private WebsocketPushMsg() {

        }

        /**
         * 识别消息推送
         *
         * @param wsUrl   websocket接口 例如 ws://192.168.1.50:9000/video
         * @param rtspUrl 视频流地址 门禁管理-门禁设备-视频流地址
         *                例如 rtsp://192.168.0.100/live1.sdp
         *                或者 rtsp://admin:admin12345@192.168.1.64/live1.sdp
         *                或者 rtsp://192.168.1.103/user=admin&password=&channel=1&stream=0.sdp
         *                或者 rtsp://192.168.1.100/live1.sdp
         *                ?__exper_tuner=lingyun&__exper_tuner_username=admin
         *                &__exper_tuner_password=admin&__exper_mentor=motion
         *                &__exper_levels=312,1,625,1,1250,1,2500,1,5000,1,5000,2,10000,2,10000,4,10000,8,10000,10
         *                &__exper_initlevel=6
         * @throws URISyntaxException
         * @throws
         * @throws ://192.168.2.52/user=admin&password=&channel=1&stream=0.sdp rtsp://192.166.2.55:554/user=admin_password=tljwpbo6_channel=1_stream=0.sdp?real_stream
         */


        private void startConnection(String wsUrl, String rtspUrl) throws URISyntaxException {
            URI uri = null;
            //当视频流地址中出现&符号时，需要进行进行url编码
            if (rtspUrl.contains("&")) {
                try {
                    //Log.d("WebsocketPushMsg", "dddddttttttttttttttt"+rtspUrl);
                    rtspUrl = URLEncoder.encode(rtspUrl, "UTF-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    //Log.d("WebsocketPushMsg", e.getMessage());
                }
            }

            try {

                uri = URI.create("ws://" + wsUrl.split("//")[1] + ":9000/video?url=" + rtspUrl);

                Log.d("WebsocketPushMsg", "url=" + uri);
                webSocketClient = new WebSocketClient(uri) {
                    //	private Vector vector=new Vector();

                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        isLianJie = false;
                        Log.d("WebsocketPushMsg", "打开");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!MainActivitykuangshi2.this.isFinishing())
                                    wangluo.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onMessage(String ss) {
                            Log.d("WebsocketPushMsg", "收到推送");
                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson = new Gson();
                        WBBean wbBean = gson.fromJson(jsonObject, WBBean.class);

                        if (wbBean.getType().equals("recognized")) { //识别
                            Log.d("WebsocketPushMsg", "识别出了");

                            final ShiBieBean dataBean = gson.fromJson(jsonObject, ShiBieBean.class);
                            // Log.d("WebsocketPushMsg", dataBean.getPerson().getSrc());
                            // BenDiQianDao qianDao = benDiQianDaoDao.load(dataBean.getPerson().getId());
                            dataBean.getPerson().setId(System.currentTimeMillis());
                            dataBean.getPerson().setSubject_type(0);
                            linkedBlockingQueue.offer(dataBean.getPerson());

                            personBeanSBList.add(dataBean.getPerson());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (personBeanSBList.size()>15){
                                        personBeanSBList.remove(0);
                                        personBeanSBList.remove(0);
                                        personBeanSBList.remove(0);
                                        personBeanSBList.remove(0);
                                        personBeanSBList.remove(0);
                                        personBeanSBList.remove(0);
                                    }
                                    adapter.notifyDataSetChanged();
                                    manager.scrollToPosition(personBeanSBList.size()-1);

                                }
                            });


                        } else if (wbBean.getType().equals("unrecognized")) {
                            Log.d("WebsocketPushMsg", "识别出了陌生人");
                           // if (!baoCunBean.getIsShowMoshengren()) {

                                JsonObject jsonObject1 = jsonObject.get("data").getAsJsonObject();

                                final WeiShiBieBean dataBean = gson.fromJson(jsonObject1, WeiShiBieBean.class);

                                try {
                                    // MoShengRenBean bean = new MoShengRenBean(dataBean.getTrack(), "sss");

                                    // daoSession.insert(bean);

                                    BASE64Decoder decoder = new BASE64Decoder();
                                    // Base64解码
                                    final byte[][] b;

                                    b = new byte[][]{decoder.decodeBuffer(dataBean.getFace().getImage())};
                                    for (int i = 0; i < b[0].length; ++i) {
                                        if (b[0][i] < 0) {// 调整异常数据
                                            b[0][i] += 256;
                                        }
                                    }
                                    //dataBean.setTrack2(b[0]);


                                    ShiBieBean.PersonBeanSB personBeanSB = new ShiBieBean.PersonBeanSB();
                                    personBeanSB.setImage(b[0]);
                                    personBeanSB.setName("陌生人");
                                    personBeanSB.setSubject_type(-1);
                                    personBeanSB.setId(System.currentTimeMillis());
                                    linkedBlockingQueue.offer(personBeanSB);

//                                    Message message = new Message();
//                                    message.what = 2;
//                                    message.obj = dataBean;
//                                    handler.sendMessage(message);


                                } catch (Exception e) {
                                    Log.d("WebsocketPushMsg", "onClose1:"+e.getMessage());
                                    //e.printStackTrace();
                                }
                         //   }
                        }
                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        isLianJie = true;
                        Log.d("WebsocketPushMsg", "onClose2:" + i);

                        closess();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!MainActivitykuangshi2.this.isFinishing()) {
                                    wangluo.setVisibility(View.VISIBLE);

                                }
                            }
                        });

                    }

                    @Override
                    public void onError(Exception e) {
                        isLianJie = true;
                        closess();
                        Log.d("WebsocketPushMsg", "出错" + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!MainActivitykuangshi2.this.isFinishing()) {
                                    wangluo.setVisibility(View.VISIBLE);
                                }

                            }
                        });


                    }
                };

                webSocketClient.connect();

            } catch (Exception e) {
                Log.d("hgf", e.getMessage() + "");
            }

        }

        private void closess() {

            if (webSocketClient != null) {
                webSocketClient.close();
                webSocketClient = null;
                System.gc();

            }

        }

    }



}
