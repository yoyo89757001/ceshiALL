package com.ruitong.huiyi3.ui;


import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.robinhood.ticker.TickerView;
import com.ruitong.huiyi3.MyApplication;
import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.beans.BaoCunBean;
import com.ruitong.huiyi3.beans.BaoCunBeanDao;
import com.ruitong.huiyi3.beans.BenDiQianDaoDao;
import com.ruitong.huiyi3.beans.ShiBieBean;
import com.ruitong.huiyi3.beans.TanChuangBean;
import com.ruitong.huiyi3.beans.WBBean;
import com.ruitong.huiyi3.beans.WeiShiBieBean;
import com.ruitong.huiyi3.interfaces.RecytviewCash;
import com.ruitong.huiyi3.service.AlarmReceiver;
import com.ruitong.huiyi3.tts.control.InitConfig;
import com.ruitong.huiyi3.tts.control.MySyntherizer;
import com.ruitong.huiyi3.tts.control.NonBlockSyntherizer;
import com.ruitong.huiyi3.tts.listener.UiMessageListener;
import com.ruitong.huiyi3.tts.util.OfflineResource;
import com.ruitong.huiyi3.utils.DateUtils;
import com.ruitong.huiyi3.utils.GsonUtil;
import com.ruitong.huiyi3.utils.Utils;
import com.ruitong.huiyi3.utils.ValueAnimatorIntface;
import com.ruitong.huiyi3.utils.ValueAnimatorUtils;
import com.ruitong.huiyi3.view.GlideCircleTransform;
import com.sdsmdg.tastytoast.TastyToast;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import sun.misc.BASE64Decoder;


public class GongDiYanShiActivity extends Activity implements RecytviewCash {
    private final static String TAG = "WebsocketPushMsg";
    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;
    @BindView(R.id.shu_liebiao)
    LinearLayout shuLiebiao;
    @BindView(R.id.scrollview)
    HorizontalScrollView scrollview;
    @BindView(R.id.root_layout)
    RelativeLayout rootLayout;
    @BindView(R.id.ceshi)
    ImageView ceshi;
    //	private IjkVideoView ijkVideoView;
    private MyReceiver myReceiver = null;
    //private SurfaceView surfaceview;
 //   private ScrollView recyclerView;
    //private MyAdapter adapter=null;
   // private HorizontalScrollView recyclerView2;
    //private MyAdapter2 adapter2=null;

    private static WebSocketClient webSocketClient = null;
    private static Vector<TanChuangBean> lingdaoList = null;
    private static Vector<TanChuangBean> yuangongList = null;
    private static Vector<View> viewList = new Vector<>();
    private int dw, dh;
    private static boolean isDH = false;
    private ImageView dabg;
    private BaoCunBeanDao baoCunBeanDao = null;
    private BaoCunBean baoCunBean = null;
    private BenDiQianDaoDao benDiQianDaoDao = null;
    private NetWorkStateReceiver netWorkStateReceiver = null;
    private LottieAnimationView wangluo;
    private static boolean isLianJie = true;
    private Typeface typeFace1;
    private TickerView y1;
    protected Handler mainHandler;
    private String appId = "10588094";
    private String appKey = "dfudSSFfNNhDCDoK7UG9G5jn";
    private String secretKey = "9BaCHNSTw3TGRgTKht4ZZvPEb2fjKEC8";
    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    private TtsMode ttsMode = TtsMode.MIX;
    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
    // assets目录下bd_etts_speech_female.data为离线男声模型；bd_etts_speech_female.data为离线女声模型
    private String offlineVoice = OfflineResource.VOICE_FEMALE;
    // 主控制类，所有合成控制方法从这个类开始
    private MySyntherizer synthesizer;
    WebsocketPushMsg websocketPushMsg = null;
    TanChuangThread tanChuangThread;
    private LinkedBlockingQueue<ShiBieBean.PersonBeanSB> linkedBlockingQueue;
    private final Timer timer = new Timer();
    private TimerTask task;
    private static Vector<ShiBieBean.PersonBeanSB> dibuList = new Vector<>();//下面的弹窗
    private ValueAnimatorUtils utils = null;

    private MediaPlayer mediaPlayer = null;
    private IVLCVout vlcVout = null;
    private IVLCVout.Callback callback;
    private LibVLC libvlc;
    private Media media;


    public Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(final Message msg) {
            switch (msg.what) {
                case 999: {
                    if (rootLayout.getChildCount() == 1) {
                        //大于1个就
                        utils = new ValueAnimatorUtils();
                        utils.setIntface(new ValueAnimatorIntface() {
                            @Override
                            public void end() {
                                rootLayout.removeViewAt(0);
                                isDH = false;
                            }

                            @Override
                            public void update(float value) {
                                rootLayout.getChildAt(0).setX(value);
                            }

                            @Override
                            public void start() {

                            }
                        });
                        utils.animator(0, -dw, 600, 0, 0);
                        isDH = true;
                    }

                    if (dibuList.size() > 9) {
                        dibuList.remove(9);
                    }
//                        if (shuLiebiao.getChildCount() > 9) {
//                            shuLiebiao.removeViewAt(shuLiebiao.getChildCount() - 1);
//                        }


                    break;
                }
                case 1:
                    final ShiBieBean.PersonBeanSB bean2 = (ShiBieBean.PersonBeanSB) msg.obj;

                    final View view_dk = View.inflate(GongDiYanShiActivity.this, R.layout.lixianhuiyi_item, null);
                    ScreenAdapterTools.getInstance().loadView(view_dk);
                    RelativeLayout rootrl = view_dk.findViewById(R.id.root_rlrl);
                    ImageView touxiang = view_dk.findViewById(R.id.touxiang);
                    TextView huanyingyu = view_dk.findViewById(R.id.huanyinyu);
                    TextView name = view_dk.findViewById(R.id.name);
                    name.setText(bean2.getName());
                  //  huanyingyu.setText("莅临指导工作");
                    Glide.get(GongDiYanShiActivity.this).clearMemory();
                    Glide.with(GongDiYanShiActivity.this)
                            .load(baoCunBean.getHoutaiDiZhi() + bean2.getAvatar())
                            .transform(new GlideCircleTransform(MyApplication.getAppContext(), 2, Color.parseColor("#ffffffff")))
                            .into(touxiang);

                    view_dk.setX(dw);
                    rootLayout.addView(view_dk);


                    RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) touxiang.getLayoutParams();
                    params1.width = (int) (dw * 0.28f);
                    params1.height = (int) (dw * 0.28f);
                    params1.topMargin = 100;
                    touxiang.setLayoutParams(params1);
                    touxiang.invalidate();

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rootrl.getLayoutParams();
                    params.width = dw - 40;
                    params.height = (int) (dh * 0.4f);
                    params.leftMargin = 20;
                    rootrl.setLayoutParams(params);
                    rootrl.invalidate();

                    //大于1个就
                    if (isDH) { //表示在执行动画
                        isDH = false;
                        utils.getValueAnimator().cancel();
                    }
                    if (rootLayout.getChildCount() > 1) {
                        ValueAnimatorUtils utils = new ValueAnimatorUtils();
                        utils.setIntface(new ValueAnimatorIntface() {
                            @Override
                            public void end() {
                                rootLayout.removeViewAt(0);
                                //入场动画(从右往左)
                                ValueAnimator anim = ValueAnimator.ofInt(dw, 0);
                                anim.setDuration(300);
                                anim.setRepeatMode(ValueAnimator.RESTART);
                                Interpolator interpolator = new DecelerateInterpolator(2f);
                                anim.setInterpolator(interpolator);
                                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        int currentValue = (Integer) animation.getAnimatedValue();
                                        view_dk.setX(currentValue);
                                        // 步骤5：刷新视图，即重新绘制，从而实现动画效果
                                        view_dk.requestLayout();
                                    }
                                });
                                anim.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        boolean cv = true;
                                        for (int i = 0; i < dibuList.size(); i++) {
                                            if (dibuList.get(i).getId().equals(bean2.getId())) {
                                                cv = false;
                                                break;
                                            }
                                        }
                                        if (cv) {
                                            final View view_dk = View.inflate(GongDiYanShiActivity.this, R.layout.shulianbiao_203, null);
                                            ScreenAdapterTools.getInstance().loadView(view_dk);
                                            TextView name = view_dk.findViewById(R.id.name);
                                            ImageView touxiang = view_dk.findViewById(R.id.touxiang);
                                            name.setText(bean2.getName());
                                            try {


                                                Glide.get(GongDiYanShiActivity.this).clearMemory();
                                                Glide.with(GongDiYanShiActivity.this)
                                                        .load(baoCunBean.getHoutaiDiZhi() + bean2.getAvatar())
                                                        .transform(new GlideCircleTransform(MyApplication.getAppContext(), 2, Color.parseColor("#ffffffff")))
                                                        .into(touxiang);


                                            } catch (Exception e) {
                                                e.printStackTrace();

                                            }

                                            shuLiebiao.addView(view_dk, 0);

                                            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) touxiang.getLayoutParams();
                                            layoutParams2.width = dw / 10 + 12;
                                            layoutParams2.topMargin = 30;
                                            layoutParams2.height = dw / 10 + 12;
                                            layoutParams2.leftMargin = 16;
                                            layoutParams2.rightMargin = 16;
                                            touxiang.setLayoutParams(layoutParams2);
                                            touxiang.invalidate();

                                            dibuList.add(0, bean2);
                                        }
                                        //启动定时器或重置定时器
                                        if (task != null) {
                                            task.cancel();
                                            //timer.cancel();
                                            task = new TimerTask() {
                                                @Override
                                                public void run() {
                                                    Message message = new Message();
                                                    message.what = 999;
                                                    handler.sendMessage(message);
                                                }
                                            };
                                            timer.schedule(task, 8000);
                                        } else {
                                            task = new TimerTask() {
                                                @Override
                                                public void run() {
                                                    Message message = new Message();
                                                    message.what = 999;
                                                    handler.sendMessage(message);
                                                }
                                            };
                                            timer.schedule(task, 8000);
                                        }
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }
                                });
                                anim.start();
                            }

                            @Override
                            public void update(float value) {
                                rootLayout.getChildAt(0).setX(value);
                            }

                            @Override
                            public void start() {
                            }
                        });
                        utils.animator(0, -dw, 300, 0, 0);
                    } else {

                        //入场动画(从右往左)
                        ValueAnimator anim = ValueAnimator.ofInt(dw, 0);
                        anim.setDuration(300);
                        anim.setRepeatMode(ValueAnimator.RESTART);
                        Interpolator interpolator = new DecelerateInterpolator(2f);
                        anim.setInterpolator(interpolator);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                int currentValue = (Integer) animation.getAnimatedValue();
                                view_dk.setX(currentValue);
                                // 步骤5：刷新视图，即重新绘制，从而实现动画效果
                                view_dk.requestLayout();
                            }
                        });
                        anim.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                boolean cv = true;
                                for (int i = 0; i < dibuList.size(); i++) {
                                    if (dibuList.get(i).getId().equals(bean2.getId())) {
                                        cv = false;
                                        break;
                                    }
                                }
                                if (cv) {
                                    final View view_dk = View.inflate(GongDiYanShiActivity.this, R.layout.shulianbiao_203, null);
                                    ScreenAdapterTools.getInstance().loadView(view_dk);
                                    TextView name = view_dk.findViewById(R.id.name);
                                    ImageView touxiang = view_dk.findViewById(R.id.touxiang);
                                    name.setText(bean2.getName());
                                    try {


                                        Glide.get(GongDiYanShiActivity.this).clearMemory();
                                        Glide.with(GongDiYanShiActivity.this)
                                                .load(baoCunBean.getHoutaiDiZhi() + bean2.getAvatar())
                                                .transform(new GlideCircleTransform(MyApplication.getAppContext(), 2, Color.parseColor("#ffffffff")))
                                                .into(touxiang);


                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }

                                    shuLiebiao.addView(view_dk, 0);

                                    RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) touxiang.getLayoutParams();
                                    layoutParams2.width = dw / 10 + 12;
                                    layoutParams2.topMargin = 30;
                                    layoutParams2.height = dw / 10 + 12;
                                    layoutParams2.leftMargin = 16;
                                    layoutParams2.rightMargin = 16;
                                    touxiang.setLayoutParams(layoutParams2);
                                    touxiang.invalidate();

                                    dibuList.add(0, bean2);
                                }
                                //启动定时器或重置定时器
                                if (task != null) {
                                    task.cancel();
                                    //timer.cancel();
                                    task = new TimerTask() {
                                        @Override
                                        public void run() {
                                            Message message = new Message();
                                            message.what = 999;
                                            handler.sendMessage(message);
                                        }
                                    };
                                    timer.schedule(task, 8000);
                                } else {
                                    task = new TimerTask() {
                                        @Override
                                        public void run() {
                                            Message message = new Message();
                                            message.what = 999;
                                            handler.sendMessage(message);
                                        }
                                    };
                                    timer.schedule(task, 8000);
                                }
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {
                            }
                        });
                        anim.start();
                    }

////                    BASE64Decoder decoder = new BASE64Decoder();
////                    // Base64解码
////                    final byte[][] b;
////
//        //            try {
//
////                        b = new byte[][]{decoder.decodeBuffer(dataBean.getSrc().replace("data:image/jpeg;base64,", ""))};
////                        for (int i = 0; i < b[0].length; ++i) {
////                            if (b[0][i] < 0) {// 调整异常数据
////                                b[0][i] += 256;
////                            }
////                        }
//
//
////                        Glide.with(GongDiYanShiActivity.this)
////                                .load(b[0])
////                                .into(im1);
////
////                        Glide.with(GongDiYanShiActivity.this)
////                                .load(baoCunBean.getHoutaiDiZhi() + dataBean.getAvatar())
////                                .into(im2);
////                        name2.setText(dataBean.getName());
//
//                 //   } catch (Exception e) {
//                  //      e.printStackTrace();
//                  //  }
//
//                    final View view3 = View.inflate(GongDiYanShiActivity.this, R.layout.item5, null);
//                    ScreenAdapterTools.getInstance().loadView(view3);
//                    TextView name = (TextView) view3.findViewById(R.id.name);
//                    TextView time = (TextView) view3.findViewById(R.id.time);
//                    ImageView touxiang = (ImageView) view3.findViewById(R.id.touxiang);
//                    name.setText(dataBean.getName());
//                    time.setText(DateUtils.tim(System.currentTimeMillis() + ""));
//                    Glide.with(GongDiYanShiActivity.this)
//                            .load(baoCunBean.getHoutaiDiZhi() + dataBean.getAvatar())
//                            .into(touxiang);
//
////                    ll1.addView(view3,0);
////                    ss1.fullScroll(ScrollView.FOCUS_LEFT);
////
////                    baoCunBean.setSize(baoCunBean.getSize() + 1);
////                    tickerview.setText(baoCunBean.getSize() + "");
////
////                    if (ll1.getChildCount() > 10) {
////                        ll1.removeViewAt(0);
////                    }

                    break;

                case 2:

                    final WeiShiBieBean dataBean2 = (WeiShiBieBean) msg.obj;

                    final View view = View.inflate(GongDiYanShiActivity.this, R.layout.item55, null);
                    ScreenAdapterTools.getInstance().loadView(view);

                    TextView time2 = (TextView) view.findViewById(R.id.time);
                    ImageView touxiang2 = (ImageView) view.findViewById(R.id.touxiang);

                    time2.setText(DateUtils.tim(System.currentTimeMillis() + ""));
                    Glide.with(GongDiYanShiActivity.this)
                            .load(dataBean2.getTrack2())
                            .into(touxiang2);

//                    ll2.addView(view,0);
//                    ss2.fullScroll(ScrollView.FOCUS_LEFT);
//
//
//                    if (ll2.getChildCount() > 18) {
//                        ll2.removeViewAt(0);
//                    }

                    break;

            }

            return false;
        }
    });


    @Override
    public void reset() {

        //数据重置
        chongzhi();
    }

    private void quanxian() {

        PermissionGen.with(GongDiYanShiActivity.this)
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
        Log.d(TAG, "权限好了");

    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
       // Log.d("MainActivity", "dddddd");
        Toast.makeText(this, "Contact permission is not granted", Toast.LENGTH_SHORT).show();
    }


    private void chongzhi() {
        //yuangongList.clear();
        //tanchuangList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        yuangongList.clear();
                        lingdaoList.clear();
                    }
                });

            }
        }).start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //	Log.d(TAG, "创建111");

        websocketPushMsg = new WebsocketPushMsg();

        baoCunBeanDao = MyApplication.myApplication.getDaoSession().getBaoCunBeanDao();
        benDiQianDaoDao = MyApplication.myApplication.getDaoSession().getBenDiQianDaoDao();
        baoCunBean = baoCunBeanDao.load(123456L);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        //DisplayMetrics dm = getResources().getDisplayMetrics();
        dw = Utils.getDisplaySize(GongDiYanShiActivity.this).x;
        dh = Utils.getDisplaySize(GongDiYanShiActivity.this).y;
        quanxian();
        setContentView(R.layout.activity_gong_di_yan_shi);
        ButterKnife.bind(this);
        ScreenAdapterTools.getInstance().reset(this);//如果希望android7.0分屏也适配的话,加上这句
        //在setContentView();后面加上适配语句
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        // rootLayout= (LinearLayout) findViewById(R.id.root_layout);
        //  rootLayout2= (LinearLayout) findViewById(R.id.root_layout2);
        //翻动动画

        TextView topzi = findViewById(R.id.trtrtt);
        // topzi.setTypeface(tf2);
        //topzi.setText("2018年基建\n精益管理现场交流会");

        dabg = (ImageView) findViewById(R.id.dabg);
        wangluo = (LottieAnimationView) findViewById(R.id.wangluo);
        wangluo.setSpeed(1.8f);
        typeFace1 = Typeface.createFromAsset(getAssets(), "fonts/xk.TTF");
        // time.setText(DateUtils.times(System.currentTimeMillis()));

        lingdaoList = new Vector<>();
        yuangongList = new Vector<>();

        Button button = (Button) findViewById(R.id.dddk);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chongzhi();

                startActivity(new Intent(GongDiYanShiActivity.this, SheZhiActivity.class));
                finish();
            }
        });

        //实例化过滤器并设置要过滤的广播
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("duanxianchonglian");
        intentFilter.addAction("gxshipingdizhi");
        intentFilter.addAction("shoudongshuaxin");
        intentFilter.addAction("updateGonggao");
        intentFilter.addAction("updateTuPian");
        intentFilter.addAction("updateShiPing");
        intentFilter.addAction("delectShiPing");
        intentFilter.addAction("guanbi");
        intentFilter.addAction(Intent.ACTION_TIME_TICK);

        // 注册广播
        registerReceiver(myReceiver, intentFilter);


        mainHandler = new Handler() {
            /*
             * @param msg
             */

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //Log.d(TAG, "msg:" + msg);
            }

        };


        initialTts();

        ceshi.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                benDiQianDaoDao.deleteAll();

                return false;
            }
        });
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) surfaceview.getLayoutParams();
        params2.topMargin = (int) ((float) dh * 0.2f);
//        params2.leftMargin = (int) ((float) dw * 0.3f / 2f);
        params2.width = (int) ((float) dw);
        params2.height = (int) ((float) dw * 0.8f);
        surfaceview.setLayoutParams(params2);
        surfaceview.invalidate();


        new Thread(new Runnable() {
            @Override
            public void run() {

                SystemClock.sleep(10000);
                sendBroadcast(new Intent(GongDiYanShiActivity.this, AlarmReceiver.class));
                //	synthesizer.speak("吃饭了吗");

            }
        }).start();

        linkedBlockingQueue = new LinkedBlockingQueue<>();

        RelativeLayout.LayoutParams ppp2 = (RelativeLayout.LayoutParams) rootLayout.getLayoutParams();
        ppp2.topMargin = 250;
        ppp2.height = (int) ((float) dh * 0.4f);
        rootLayout.setLayoutParams(ppp2);
        rootLayout.invalidate();

        play();

        tanChuangThread = new TanChuangThread();
        tanChuangThread.start();
    }

    private void play() {
        if (mediaPlayer!=null){
            mediaPlayer.stop();
        }
        if (vlcVout!=null){
            vlcVout.removeCallback(callback);
            vlcVout.detachViews();
        }

        libvlc = new LibVLC(GongDiYanShiActivity.this);
        mediaPlayer = new MediaPlayer(libvlc);
        vlcVout = mediaPlayer.getVLCVout();

        callback = new IVLCVout.Callback() {


            @Override
            public void onNewLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {

            }

            @Override
            public void onSurfacesCreated(IVLCVout ivlcVout) {
                try {

                    if (mediaPlayer != null && baoCunBean.getShipingIP() != null) {
                        Log.d("dddddd", baoCunBean.getShipingIP() + "gggg");

                        media = new Media(libvlc, Uri.parse(baoCunBean.getShipingIP()));
                        mediaPlayer.setMedia(media);
                        mediaPlayer.play();

                    }

                } catch (Exception e) {
                    Log.d("vlc-newlayout", e.toString());
                }
            }

            @Override
            public void onSurfacesDestroyed(IVLCVout ivlcVout) {

            }

            @Override
            public void onHardwareAccelerationError(IVLCVout vlcVout) {

            }

        };

        vlcVout.addCallback(callback);
        vlcVout.setVideoView(surfaceview);
        vlcVout.attachViews();
//        mediaPlayer.setEventListener(new MediaPlayer.EventListener() {
//            @Override
//            public void onEvent(MediaPlayer.Event event) {
//                Log.d(TAG, "event.getEsChangedID():" + event.getEsChangedID());
//                Log.d(TAG, "event.getEsChangedType():" + event.getEsChangedType());
//                Log.d(TAG, "event.getVoutCount():" + event.getVoutCount());
//                Log.d(TAG, "event.getSeekable():" + event.getSeekable());
//                Log.d(TAG, "event.getTimeChanged():" + event.getTimeChanged());
//                Log.d(TAG, "event.getPositionChanged():" + event.getPositionChanged());
//                Log.d(TAG, "event.type:" + event.type);
//                Log.d(TAG, "event.getBuffering():" + event.getBuffering());
//               // Log.d(TAG, "event.getPausable():" + event.getPausable());
//            }
//        });

    }




    private class TanChuangThread extends Thread {
        boolean isRing;

        @Override
        public void run() {
            while (!isRing) {
                try {
                    //有动画 ，延迟到一秒一次
                    // SystemClock.sleep(1100);
                    ShiBieBean.PersonBeanSB subject = linkedBlockingQueue.take();

                    Message messagey = Message.obtain();
                    messagey.what = 1;
                    messagey.obj = subject;
                    handler.sendMessage(messagey);

                    SystemClock.sleep(500);

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


    /**
     * 初始化引擎，需要的参数均在InitConfig类里
     * <p>
     * DEMO中提供了3个SpeechSynthesizerListener的实现
     * MessageListener 仅仅用log.i记录日志，在logcat中可以看见
     * UiMessageListener 在MessageListener的基础上，对handler发送消息，实现UI的文字更新
     * FileSaveListener 在UiMessageListener的基础上，使用 onSynthesizeDataArrived回调，获取音频流
     */
    protected void initialTts() {
        // 设置初始化参数
        SpeechSynthesizerListener listener = new UiMessageListener(mainHandler); // 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类
        Map<String, String> params = getParams();
        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
        InitConfig initConfig = new InitConfig(appId, appKey, secretKey, ttsMode, params, listener);
        synthesizer = new NonBlockSyntherizer(this, initConfig, mainHandler); // 此处可以改为MySyntherizer 了解调用过程

    }

    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        params.put(SpeechSynthesizer.PARAM_SPEAKER, baoCunBean.getBoyingren() + ""); // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_VOLUME, "8"); // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, baoCunBean.getYusu() + "");// 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, baoCunBean.getYudiao() + "");// 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);         // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        // 离线资源文件
        OfflineResource offlineResource = createOfflineResource(offlineVoice);
        // 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
        params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename());
        params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
                offlineResource.getModelFilename());

        return params;
    }

    protected OfflineResource createOfflineResource(String voiceType) {
        OfflineResource offlineResource = null;
        try {
            offlineResource = new OfflineResource(this, voiceType);
        } catch (IOException e) {
            // IO 错误自行处理
            e.printStackTrace();
            // toPrint("【error】:copy files from assets failed." + e.getMessage());
        }
        return offlineResource;
    }


//						Glide.with(MyApplication.getAppContext())
//								.load(R.drawable.zidonghuoqu1)
//								//.load("http://121.46.3.20"+item.getTouxiang())
//								//.apply(myOptions)
//								.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
//								//	.bitmapTransform(new GrayscaleTransformation(VlcVideoActivity.this))
//								.into(imageView);


    /**
     * 生成一个0 到 count 之间的随机数
     *
     * @param endNum
     * @return
     */
    public int getNum(int endNum) {
        if (endNum > 0) {
            Random random = new Random();
            return random.nextInt(endNum);
        }
        return 0;
    }

    int c = 0;
    boolean pp = true;


    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            //Log.d(TAG, "intent:" + intent.getAction());

            if (Objects.equals(intent.getAction(), Intent.ACTION_TIME_TICK)) {

                //time.setText(DateUtils.times(System.currentTimeMillis()));


            }
            if (intent.getAction().equals("duanxianchonglian")) {
                Log.d(TAG, "刷脸监听1");
                //断线重连
                Log.d(TAG, "刷脸监听");
                if (isLianJie) {
                    Log.d(TAG, "进来2");
                    try {
                        play();
                        Log.d(TAG, baoCunBean.getZhujiDiZhi() + "ddddd");
                        Log.d(TAG, baoCunBean.getShipingIP());
                        if (baoCunBean.getHoutaiDiZhi() != null && baoCunBean.getShipingIP() != null) {
                            Log.d(TAG, "jin");
                            websocketPushMsg.startConnection(baoCunBean.getHoutaiDiZhi(), baoCunBean.getShipingIP());
                        }
                    } catch (Exception e) {
                        Log.d(TAG, e.getMessage() + "aaa");

                    }
                }


            }

            if (intent.getAction().equals("guanbi")) {
                Log.d(TAG, "关闭");
                finish();
            }
        }

    }

    // 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
    public static void getAllFiles(File root, String nameaaa) {
        File files[] = root.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    getAllFiles(f, nameaaa);
                } else {
                    String name = f.getName();
                    if (name.equals(nameaaa)) {
                        Log.d(TAG, "视频文件删除:" + f.delete());
                    }
                }
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (KeyEvent.KEYCODE_MENU == keyCode) {  //如果按下的是菜单
            Log.d(TAG, "按下菜单键 ");
            chongzhi();
            //isTiaoZhuang=false;
            startActivity(new Intent(GongDiYanShiActivity.this, SheZhiActivity.class));
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {

        baoCunBean = baoCunBeanDao.load(123456L);

        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetWorkStateReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netWorkStateReceiver, filter);
        }

        if (baoCunBean != null && baoCunBean.getHoutaiDiZhi() != null) {
//            try {
//
//                WebsocketPushMsg websocketPushMsg = new WebsocketPushMsg();
//                websocketPushMsg.close();
//                if (baoCunBean.getHoutaiDiZhi() != null && baoCunBean.getShipingIP() != null) {
//                    websocketPushMsg.startConnection(baoCunBean.getHoutaiDiZhi(), baoCunBean.getShipingIP());
//                }
//            } catch (URISyntaxException e) {
//                Log.d(TAG, e.getMessage() + "ddd");
//
//            }
        } else {
            TastyToast.makeText(GongDiYanShiActivity.this, "没有设置后台地址", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
        }


        super.onResume();

    }


    @Override
    public void onPause() {
        if (baoCunBeanDao != null)
            baoCunBeanDao.update(baoCunBean);

        Log.d(TAG, "暂停");

        super.onPause();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (webSocketClient != null) {
            webSocketClient.close();
            webSocketClient = null;
        }

        if (timer != null)
            timer.cancel();
        if (task != null)
            task.cancel();


        Intent intent1 = new Intent("guanbi333"); //关闭监听服务
        sendBroadcast(intent1);
        synthesizer.release();
        handler.removeCallbacksAndMessages(null);
        if (myReceiver != null)
            unregisterReceiver(myReceiver);
        unregisterReceiver(netWorkStateReceiver);
        super.onDestroy();

    }


//	/**
//	 * websocket接口返回face.image
//	 * image为base64编码的字符串
//	 * 将字符串转为可以识别的图片
//	 * @param imgStr
//	 * @return
//	 */
//	public Bitmap generateImage(String imgStr, int cont, WBWeiShiBieDATABean dataBean, Context context) throws Exception {
//		// 对字节数组字符串进行Base64解码并生成图片
//		if (imgStr == null) // 图像数据为空
//			return null;
//		BASE64Decoder decoder = new BASE64Decoder();
//		try {
//			// Base64解码
//			final byte[][] b = {decoder.decodeBuffer(imgStr)};
//			for (int i = 0; i < b[0].length; ++i) {
//				if (b[0][i] < 0) {// 调整异常数据
//					b[0][i] += 256;
//				}
//			}
//			MoShengRenBean2 moShengRenBean2=new MoShengRenBean2();
//			moShengRenBean2.setId(dataBean.getTrack());
//			moShengRenBean2.setBytes(b[0]);
//			moShengRenBean2.setUrl("dd");
//
//			moShengRenBean2List.add(moShengRenBean2);
//
//				adapter.notifyDataSetChanged();
//
//
//
//
//
//			//   Bitmap bitmap= BitmapFactory.decodeByteArray(b[0],0, b[0].length);
//
//			//  Log.d("WebsocketPushMsg", "bitmap.getHeight():" + bitmap.getHeight());
//
//			// 生成jpeg图片
//			//  OutputStream out = new FileOutputStream(imgFilePath);
//			//   out.write(b);
//			//  out.flush();
//			//  out.close();
//
//
////			dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
////				@Override
////				public void onDismiss(DialogInterface dialog) {
////					Log.d("VlcVideoActivity", "Dialog销毁2");
////					b[0] =null;
////				}
////			});
//			//dialog.show();
//
//
//			return null;
//		} catch (Exception e) {
//			throw e;
//
//		}
//	}

    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 识别消息推送
     * 主机盒子端API ws://[主机ip]:9000/video
     * 通过 websocket 获取 识别结果
     *
     * @author Wangshutao
     */
    private class WebsocketPushMsg {


        public WebsocketPushMsg() {

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
                                if (!GongDiYanShiActivity.this.isFinishing())
                                    wangluo.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onMessage(String ss) {

                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson = new Gson();
                        WBBean wbBean = gson.fromJson(jsonObject, WBBean.class);

                        if (wbBean.getType().equals("recognized")) { //识别
                            Log.d("WebsocketPushMsg", "识别出了");

                            final ShiBieBean dataBean = gson.fromJson(jsonObject, ShiBieBean.class);
                            // Log.d("WebsocketPushMsg", dataBean.getPerson().getSrc());
                           // BenDiQianDao qianDao = benDiQianDaoDao.load(dataBean.getPerson().getId());
                            linkedBlockingQueue.offer(dataBean.getPerson());

//                            if (qianDao == null) {
//                                BenDiQianDao diQianDao = new BenDiQianDao();
//                                diQianDao.setId(dataBean.getPerson().getId());
//                                benDiQianDaoDao.insert(diQianDao);
//                            }

//                            Message message = new Message();
//                            message.what = 1;
//                            message.obj = dataBean.getPerson();
//                            handler.sendMessage(message);


                        } else if (wbBean.getType().equals("unrecognized")) {
                            Log.d("WebsocketPushMsg", "识别出了陌生人");
                            if (!baoCunBean.getIsShowMoshengren()) {

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
                                    dataBean.setTrack2(b[0]);
                                    Message message = new Message();
                                    message.what = 2;
                                    message.obj = dataBean;
                                    handler.sendMessage(message);


                                } catch (Exception e) {
                                    Log.d("WebsocketPushMsg", e.getMessage());
                                    //e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        isLianJie = true;
                        Log.d("WebsocketPushMsg", "onClose" + i);

                        closess();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!GongDiYanShiActivity.this.isFinishing()) {
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
                                if (!GongDiYanShiActivity.this.isFinishing()) {
                                    wangluo.setVisibility(View.VISIBLE);
                                }

                            }
                        });


                    }
                };

                webSocketClient.connect();

            } catch (Exception e) {
                Log.d(TAG, e.getMessage() + "");
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


    /**
     * 根据byte数组生成文件
     *
     * @param bytes 生成文件用到的byte数组
     * @param age
     */
    private void createFileWithByte(byte[] bytes, String filename, Long tt, String age) {
        /**
         * 创建File对象，其中包含文件所在的目录以及文件的命名
         */
        File file = null;
        String sdDir = this.getFilesDir().getAbsolutePath();//获取跟目录
        makeRootDirectory(sdDir);

        // 创建FileOutputStream对象
        FileOutputStream outputStream = null;
        // 创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;

        try {
            file = new File(sdDir + File.separator + filename);
            // 在文件系统中根据路径创建一个新的空文件
            //	file2.createNewFile();
            //	Log.d(TAG, file.createNewFile()+"");

            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(file);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(bytes);
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();
            //上传文件
            //addPhoto(sdDir,filename,bytes,tt,age);

        } catch (Exception e) {
            // 打印异常信息
            //Log.d(TAG, "ssssssssssssssssss"+e.getMessage());
        } finally {
            // 关闭创建的流对象
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

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
                    wangluo.setVisibility(View.GONE);

                } else {
                    isLianJie = false;

                    wangluo.setVisibility(View.VISIBLE);
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

                Log.d(TAG, "API23");
                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                //获取所有网络连接的信息
                Network[] networks = connMgr.getAllNetworks();
                //用于存放网络连接信息
                StringBuilder sb = new StringBuilder();
                //通过循环将网络信息逐个取出来
                Log.d(TAG, "networks.length:" + networks.length);
                if (networks.length == 0) {
                    isLianJie = false;
                    wangluo.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < networks.length; i++) {
                    //获取ConnectivityManager对象对应的NetworkInfo对象
                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);

                    if (networkInfo.isConnected()) {
                        wangluo.setVisibility(View.GONE);

                    }
                }

            }
        }
    }


}
