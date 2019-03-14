package com.ruitong.huiyi3.ui;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.YuvImage;


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
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.badoo.mobile.util.WeakHandler;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.plattysoft.leonids.ParticleSystem;
import com.ruitong.huiyi3.MyApplication;
import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.beans.BaoCunBean;
import com.ruitong.huiyi3.beans.BenDiJiLuBean;
import com.ruitong.huiyi3.beans.GuanHuai;
import com.ruitong.huiyi3.beans.GuanHuai_;
import com.ruitong.huiyi3.beans.LunBoBean;
import com.ruitong.huiyi3.beans.MSRBean;
import com.ruitong.huiyi3.beans.ShiBieBean;
import com.ruitong.huiyi3.beans.Subject;
import com.ruitong.huiyi3.beans.Subject_;
import com.ruitong.huiyi3.beans.TianQiBean;
import com.ruitong.huiyi3.beans.TodayBean;
import com.ruitong.huiyi3.beans.WBBean;
import com.ruitong.huiyi3.beans.WeiShiBieBean;
import com.ruitong.huiyi3.beans.XGBean;
import com.ruitong.huiyi3.beans.XinXiAll;
import com.ruitong.huiyi3.beans.XinXiIdBean;
import com.ruitong.huiyi3.beans.XinXiIdBean_;
import com.ruitong.huiyi3.ljkplay.widget.media.IjkVideoView;
import com.ruitong.huiyi3.service.AlarmReceiver;
import com.ruitong.huiyi3.tts.control.InitConfig;
import com.ruitong.huiyi3.tts.control.MySyntherizer;
import com.ruitong.huiyi3.tts.control.NonBlockSyntherizer;
import com.ruitong.huiyi3.tts.listener.UiMessageListener;
import com.ruitong.huiyi3.tts.util.OfflineResource;
import com.ruitong.huiyi3.utils.Contents;
import com.ruitong.huiyi3.utils.DateUtils;
import com.ruitong.huiyi3.utils.FileUtil;
import com.ruitong.huiyi3.utils.GlideUtils;
import com.ruitong.huiyi3.utils.GsonUtil;
import com.ruitong.huiyi3.utils.RandomDataUtil;
import com.ruitong.huiyi3.utils.TSXXChuLi;
import com.ruitong.huiyi3.utils.ValueAnimatorIntface;
import com.ruitong.huiyi3.utils.ValueAnimatorUtils;
import com.ruitong.huiyi3.view.FKTopView_204;
import com.ruitong.huiyi3.view.GlideCircleTransform;
import com.ruitong.huiyi3.view.GlideRoundTransform;
import com.ruitong.huiyi3.view.MyTest2;
import com.ruitong.huiyi3.view.ViewUpadte;
import com.ruitong.huiyi3.view.YGTopView_204;
import com.sdsmdg.tastytoast.TastyToast;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;
import io.objectbox.query.LazyList;
import io.objectbox.query.Query;
import me.grantland.widget.AutofitTextView;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import sun.misc.BASE64Decoder;
import tv.danmaku.ijk.media.player.IMediaPlayer;


public class MainActivity204 extends AppCompatActivity  {


    @BindView(R.id.companyName)
    AutofitTextView companyName;
    @BindView(R.id.register_im)
    ImageView registerIm;
    @BindView(R.id.ijkplayview)
    IjkVideoView ijkplayview;
    @BindView(R.id.bottom_ll)
    LinearLayout bottomLl;
    @BindView(R.id.top_ll)
    FlexboxLayout hengLiebiao;
    @BindView(R.id.mytest)
    MyTest2 mytest;
    @BindView(R.id.rl_02)
    RelativeLayout rl02;
    @BindView(R.id.dabg)
    ImageView dabg;
    @BindView(R.id.xiaoshi)
    TextView xiaoshi;
    @BindView(R.id.riqi)
    TextView riqi;
    @BindView(R.id.xingqi)
    TextView xingqi;
    @BindView(R.id.tianqi_im)
    ImageView tianqiIm;
    @BindView(R.id.tianqi)
    AutofitTextView tianqi;
    @BindView(R.id.wendu)
    AutofitTextView wendu;
    @BindView(R.id.shidu)
    AutofitTextView shidu;
    @BindView(R.id.tianqiRL)
    LinearLayout tianqiRL;
    @BindView(R.id.fgrt)
    LinearLayout fgrt;
    @BindView(R.id.shipingrl)
    RelativeLayout shipingrl;
    @BindView(R.id.fgrt2)
    LinearLayout fgrt2;
    @BindView(R.id.vip_ll)
    RelativeLayout vipLl;
    @BindView(R.id.surfaceView)
    SurfaceView surfaceView;
    private LottieAnimationView wangluo;
    private static WebSocketClient webSocketClient = null;
    private Button shezhiTv;
    private Banner banner;
    protected Handler mainHandler;
    private final Timer timer = new Timer();
    private TimerTask task;
    private List<String> imagesList = new ArrayList<>();
    private Box<Subject> subjectBox = null;
    private static boolean isSC = true;
    private static boolean isLianJie = true;
    private MySyntherizer synthesizer;
    //  private static Vector<Subject> dibuList = new Vector<>();//下面的弹窗
    //  private static Vector<View> allList = new Vector<>();//中间的弹窗
    private RequestOptions myOptions = new RequestOptions()
            .fitCenter()
            .error(R.drawable.erroy_bg)
            .transform(new GlideCircleTransform(2, Color.parseColor("#ffffff")));
    // .transform(new GlideRoundTransform(MainActivity.this,10));
    //  private ImageView ceshi;
    private RequestOptions myOptions2 = new RequestOptions()
            .fitCenter()
            .error(R.drawable.erroy_bg)
            //   .transform(new GlideCircleTransform(MyApplication.myApplication, 2, Color.parseColor("#ffffffff")));
            .transform(new GlideRoundTransform(MainActivity204.this, 20));
    // private ValueAnimatorUtils utils = null;
    private int bot_ll_width, bot_ll_hight;
    private LinkedBlockingQueue<ShiBieBean.PersonBeanSB> linkedBlockingQueue;
    /* 人脸识别Group */

    private boolean isAnXia = true;
    private ConcurrentHashMap<Long, Integer> concurrentHashMap = new ConcurrentHashMap<Long, Integer>();
    private static boolean cameraFacingFront = true;
    private int cameraRotation;
    private static final int cameraWidth = 1280;
    private static final int cameraHeight = 720;
    // private IjkVideoView shipingView;
    private int heightPixels;
    private int widthPixels;
    int screenState = 0;// 0 横 1 竖
    /* 网络请求队列*/
    /*Toast 队列*/
    LinkedBlockingQueue<Toast> mToastBlockQueue;
    // private static boolean isDH = false;
    private static boolean isLink = true;
    private long tID = -1;
    // private boolean isNet = false;
    /*DetectResult queue*/

    /*recognize thread*/

    TanChuangThread tanChuangThread;
    private int dw, dh;
    private Box<BaoCunBean> baoCunBeanDao = null;
    private Box<TodayBean> todayBeanBox = null;
    private Box<BenDiJiLuBean> benDiJiLuBeanBox = null;
    private BaoCunBean baoCunBean = null;
    private TodayBean todayBean = null;
    private IntentFilter intentFilter;
    private TimeChangeReceiver timeChangeReceiver;
    private WeakHandler mHandler;

    private List<Integer> topZuoBiao = new ArrayList<>();
    private List<Integer> bootomZuoBiao = new ArrayList<>();

    private ImageView logo;
    private ShengRiThierd shengRiThierd = null;
    //   private VipThired vipThired = null;
    //  private FangkeThired fangkeThired = null;
    private Query<Subject> query = null;
    private NetWorkStateReceiver netWorkStateReceiver = null;
    private Box<GuanHuai> guanHuaiBox = null;
    private Box<XinXiAll> xinXiAllBox = null;
    //  private List<GuanHuai> guanHuaiList = new ArrayList<>();
    //   private List<String> bumenString = new ArrayList<>();
    //  private String leixing[] = new String[]{"员工", "普通访客", "白名单", "陌生人"};
    private Box<XinXiIdBean> xinXiIdBeanBox = null;
    // private List<Subject> subjectList = new ArrayList<>();
    private Box<LunBoBean> lunBoBeanBox = null;
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .readTimeout(30000, TimeUnit.MILLISECONDS)
//				    .cookieJar(new CookiesManager())
            //     .retryOnConnectionFailure(true)
            .build();
    private int count = -1;
    private Contents contents = null;
    private TSXXChuLi tsxxChuLi=new TSXXChuLi();
    private MediaPlayer mediaPlayer = null;
    private IVLCVout vlcVout = null;
    private IVLCVout.Callback callback;
    private LibVLC libvlc;
    private Media media;
    private WebsocketPushMsg websocketPushMsg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mImageCache = new FaceImageCache();
        mToastBlockQueue = new LinkedBlockingQueue<>();

        todayBeanBox = MyApplication.myApplication.getTodayBeanBox();
        todayBean = todayBeanBox.get(123456L);
        benDiJiLuBeanBox = MyApplication.myApplication.getBenDiJiLuBeanBox();
        guanHuaiBox = MyApplication.myApplication.getGuanHuaiBox();
        xinXiAllBox = MyApplication.myApplication.getXinXiAllBox();
        xinXiIdBeanBox = MyApplication.myApplication.getXinXiIdBeanBox();
        baoCunBeanDao = MyApplication.myApplication.getBaoCunBeanBox();
        lunBoBeanBox = MyApplication.myApplication.getLunBoBeanBox();

        mainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

        contents = new Contents();
        baoCunBean = baoCunBeanDao.get(123456L);
        subjectBox = MyApplication.myApplication.getSubjectBox();
        //网络状态关闭
        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetWorkStateReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netWorkStateReceiver, filter);
        }
      //  Log.d("MainActivity102", "网络地址" + FileUtil.getMacAddr());


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
                sendBroadcast(new Intent(MainActivity204.this, AlarmReceiver.class));
                //	synthesizer.speak("吃饭了吗");
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

      //  tsxxChuLi = new TSXXChuLi();

        //初始化动画坐标
        new Thread(new Runnable() {
            @Override
            public void run() {
                int temp = dw / 9;
                for (int i = 1; i < 10; i++) {
                    if (i == 9) {
                        topZuoBiao.add(temp * 9 - 80);
                    } else {
                        topZuoBiao.add(temp * i);
                    }
                }

                int temp2 = dw / 6;
                for (int i = 1; i < 7; i++) {
                    if (i == 6) {
                        bootomZuoBiao.add(temp2 * 6 - 200);
                    } else {
                        bootomZuoBiao.add(temp2 * i);
                    }
                }
            }
        }).start();

        websocketPushMsg = new WebsocketPushMsg();

        /* 初始化界面 */
        initView();
        wangluo =  findViewById(R.id.wangluo);
        wangluo.setSpeed(1.8f);


        bottomLl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bot_ll_width = bottomLl.getWidth();
                bot_ll_hight = (int) (bottomLl.getHeight() * 0.6f);
                Log.d("MainActivity102", "bottomLl.getWidth():" + bottomLl.getWidth());
                Log.d("MainActivity102", "bottomLl.getWidth():" + bottomLl.getHeight());
                //只需要获取一次高度，获取后移除监听器
                bottomLl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        rl02.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //  Log.d("MainActivity102", "bottomLl.getWidth():" + rl02.getWidth());
                //  Log.d("MainActivity102", "bottomLl.getWidth():" + rl02.getHeight());
                //  Log.d("MainActivity102", "(int)(dh*0.21f):" + (int) (dh * 0.21f) + "   " + dh);
                mytest.setData(rl02.getWidth(), (int) (rl02.getHeight() * 0.40f));
                RelativeLayout.LayoutParams zdp = (RelativeLayout.LayoutParams) surfaceView.getLayoutParams();
                zdp.topMargin = (int) (rl02.getHeight() * 0.08f);
                zdp.width = (int)(((rl02.getWidth()-40)*0.34f)+2);
                zdp.height = (int)(((rl02.getWidth()-40)*0.34f)*1.33f);
                surfaceView.setLayoutParams(zdp);
                surfaceView.invalidate();
                //只需要获取一次高度，获取后移除监听器
                rl02.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        shipingrl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
               // Log.d("MainActivity102", "shipingrl:" + dw);
               // Log.d("MainActivity102", "shipingrl.getWidth():" + shipingrl.getWidth());
                //中间视频大小
           //     ViewUpadte.updataViewGroup("LinearLayout", shipingrl, 0, (int) (shipingrl.getWidth() * 0.56f), 0, 0, 0, 0);
//                Log.d("MainActivity102", "bottomLl.getWidth():" + bottomLl.getWidth());
//                Log.d("MainActivity102", "bottomLl.getWidth():" + bottomLl.getHeight());
                //只需要获取一次高度，获取后移除监听器
                shipingrl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        if (baoCunBean != null)
            initialTts();


        play();

        tanChuangThread = new TanChuangThread();
        tanChuangThread.start();


        mHandler = new WeakHandler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 111: {
                        final ShiBieBean.PersonBeanSB bean2 = (ShiBieBean.PersonBeanSB) msg.obj;

//                        if (shengRiThierd != null) {
//                            shengRiThierd.interrupt();
//                            shengRiThierd = null;
//                        }
//                        if (fangkeThired != null) {
//                            fangkeThired.interrupt();
//                            fangkeThired = null;
//                        }
//                        if (vipThired != null) {
//                            vipThired.interrupt();
//                            vipThired = null;
//                        }
//                        vipThired = new VipThired();
//                        vipThired.start();

                        boolean isRT = false;
                        synchronized (MainActivity204.this) {
                            int sizes = vipLl.getChildCount();
                            for (int i = 0; i < sizes; i++) {
                                if ((bean2.getId() + "").equals(vipLl.getChildAt(i).getTag().toString())) {
                                    isRT = true;
                                    break;
                                }
                            }
                        }
                        if (isRT)
                            break;


                        final View view_dk = View.inflate(MainActivity204.this, R.layout.vipfangke_item204, null);
                        AutofitTextView name = view_dk.findViewById(R.id.name);
                        AutofitTextView hyy = view_dk.findViewById(R.id.gongsi);
                        AutofitTextView tishi = view_dk.findViewById(R.id.tishi);
                        ImageView touxiang = view_dk.findViewById(R.id.touxiang);
                        final RelativeLayout rootLayout = view_dk.findViewById(R.id.root_rl);
                        tishi.setText("欢迎你的来访,已通知你的被访人,请到会客区等候,谢谢!");
                        LinearLayout viprlrl = view_dk.findViewById(R.id.gghh);


                        name.setText(bean2.getName());
                        if (baoCunBean.getWenzi1() != null) {
                            hyy.setText("欢迎来访" + baoCunBean.getWenzi1());
                        } else {
                            hyy.setText("欢迎来您访本公司");
                        }

                        synthesizer.speak("欢迎贵宾来访");


                        try {
                            if (bean2.getAvatar() != null) {

                                Glide.with(MainActivity204.this)
                                        .load(baoCunBean.getTouxiangzhuji()+bean2.getAvatar())
                                        .apply(myOptions)
                                        .into(touxiang);
                            } else {
//                                Bitmap bitmap = mFacePassHandler.getFaceImage(bean2.getTeZhengMa().getBytes());
//                                //   mianBanJiView.setBitmap(FileUtil.toRoundBitmap(bitmap), 0);
//                                //     Bitmap bitmap=BitmapFactory.decodeByteArray(bean2.getBitmap(),0,bean2.getBitmap().length);
//                                //    mianBanJiView.setBitmap(FileUtil.toRoundBitmap(bitmap), 0);
//                                Glide.with(MainActivity204.this)
//                                        .load(new BitmapDrawable(getResources(), bitmap))
//                                        .apply(myOptions)
//                                        .into(touxiang);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        view_dk.setTag(bean2.getId() + "");
                        vipLl.addView(view_dk);
                        dabg.setVisibility(View.VISIBLE);

                        ViewUpadte.updataViewGroup("RelativeLayout", rootLayout, dw-(int) ((dw * 0.036f)*2f),dh- (int) ((dh * 0.08f)*2f), 0, 0, 0, 0);

                        ViewUpadte.updataView("LinearLayout", name, 0, 0, 0, (int) ((float) dh * 0.04f), 0, 0);
                        ViewUpadte.updataView("LinearLayout", tishi, (int) ((float) dw * 0.4f), 0, 0, (int) ((float) dh * 0.22f), 0, 0);

                        ViewUpadte.updataView("LinearLayout", touxiang, (int) ((float) dw * 0.30f), (int) ((float) dw * 0.30f), 0, (int) ((float) dw * 0.2f), 0, 0);

                        ViewUpadte.updataViewGroup("RelativeLayout", viprlrl, 0, 0, (int) ((float) dw * 0.23f), 0,0,0);

                        //动画
                        SpringSystem springSystem3 = SpringSystem.create();
                        final Spring spring3 = springSystem3.createSpring();
                        //两个参数分别是弹力系数和阻力系数
                        spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(100, 6));
                        // 添加弹簧监听器
                        spring3.addListener(new SimpleSpringListener() {
                            @Override
                            public void onSpringUpdate(Spring spring) {
                                // value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
                                float value = (float) spring.getCurrentValue();
                                //  Log.d("kkkk", "value:" + value);
                                //基于Y轴的弹簧阻尼动画
                                //	helper.itemView.setTranslationY(value);
                                // 对图片的伸缩动画
                                //float scale = 1f - (value * 0.5f);
                                view_dk.setScaleX(value);
                                view_dk.setScaleY(value);
                            }
                        });
                        // 设置动画结束值
                        spring3.setEndValue(1f);


                        Message message = Message.obtain();
                        message.what = 900;

                        mHandler.sendMessageDelayed(message, 10000);
                        count++;
                        //底部对列表
                        setBootonView(bean2);

//                        //入场动画(从右往左)
//                        ValueAnimator anim = ValueAnimator.ofInt(dh - (int) ((float) dh * 0.08f), rootLayout.getHeight() - (int) ((float) dw * 0.058f));
//                        anim.setDuration(300);
//                        anim.setRepeatMode(ValueAnimator.RESTART);
//                        Interpolator interpolator = new DecelerateInterpolator(2f);
//                        anim.setInterpolator(interpolator);
//                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                            @Override
//                            public void onAnimationUpdate(ValueAnimator animation) {
//                                int currentValue = (Integer) animation.getAnimatedValue();
//                                rootLayout.setY(currentValue);
//                                // 步骤5：刷新视图，即重新绘制，从而实现动画效果
//                                rootLayout.requestLayout();
//                            }
//                        });
//                        anim.addListener(new Animator.AnimatorListener() {
//                            @Override
//                            public void onAnimationStart(Animator animation) {
//                                rootLayout.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                //启动定时器或重置定时器
//                                if (task != null) {
//                                    task.cancel();
//                                    //timer.cancel();
//                                    task = new TimerTask() {
//                                        @Override
//                                        public void run() {
//
//                                            Message message = new Message();
//                                            message.what = 900;
//                                            mHandler.sendMessage(message);
//                                        }
//                                    };
//                                    timer.schedule(task, 8000);
//
//                                } else {
//                                    task = new TimerTask() {
//                                        @Override
//                                        public void run() {
//
//                                            Message message = new Message();
//                                            message.what = 900;
//                                            mHandler.sendMessage(message);
//
//                                        }
//                                    };
//                                    timer.schedule(task, 8000);
//                                }
//                            }
//
//                            @Override
//                            public void onAnimationCancel(Animator animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animator animation) {
//
//                            }
//                        });
//                        anim.start();


                        break;
                    }

                    case 444: {

                        dabg.setVisibility(View.VISIBLE);



                        //普通打卡
                        final ShiBieBean.PersonBeanSB bean2 = (ShiBieBean.PersonBeanSB) msg.obj;
                        boolean isRT = false;
                        synchronized (MainActivity204.this) {
                            int sizes = hengLiebiao.getChildCount();
                            for (int i = 0; i < sizes; i++) {
                                if ((bean2.getId() + "").equals(hengLiebiao.getChildAt(i).getTag().toString())) {
                                    isRT = true;
                                    break;
                                }
                            }
                        }
                        if (isRT)
                            break;

                        //0小邮局 1生日提醒 2入职关怀 3节日关怀
                        boolean isSR = false;
                        final List<GuanHuai> ygguanHuaiList = guanHuaiBox.query().equal(GuanHuai_.employeeId, bean2.getId()).build().find();
                        final List<GuanHuai> ygguanHuaiList2 = guanHuaiBox.query().equal(GuanHuai_.employeeId, 0L).build().find();
                        if (ygguanHuaiList2.size() > 0) {
                            ygguanHuaiList.addAll(ygguanHuaiList2);
                        }
                        //信息推送
                        List<XinXiAll> xinXiAlls = xinXiAllBox.getAll();
                        for (XinXiAll all : xinXiAlls) {
                            if (all.getEmployeeId().equals("0") && all.getStartTime() <= System.currentTimeMillis()) {
                                GuanHuai guanHuai1 = new GuanHuai();
                                guanHuai1.setMarkedWords(all.getEditNews());
                                guanHuai1.setNewsStatus("");
                                guanHuai1.setProjectileStatus("4");
                                ygguanHuaiList.add(guanHuai1);

                            } else {
                                //查出来
                                final List<XinXiIdBean> xinXiIdBeans = xinXiIdBeanBox.query().equal(XinXiIdBean_.ygid, bean2.getId()).build().find();
                                for (XinXiIdBean idBean : xinXiIdBeans) {
                                    if (idBean.getUuid().equals(all.getId()) && all.getStartTime() <= System.currentTimeMillis()) {
                                        GuanHuai guanHuai1 = new GuanHuai();
                                        guanHuai1.setMarkedWords(all.getEditNews());
                                        guanHuai1.setNewsStatus("");
                                        guanHuai1.setProjectileStatus("4");
                                        ygguanHuaiList.add(guanHuai1);
                                    }
                                }
                            }
                        }
                        //有没有生日
                        final int si = ygguanHuaiList.size();
                        for (GuanHuai huai : ygguanHuaiList) {
                            if (huai.getProjectileStatus().equals("1")) {
                                isSR = true;
                                break;
                            }
                        }

                        if (isSR) {
                            shengRiThierd = new ShengRiThierd();
                            shengRiThierd.start();
                        }

                        final View view_dk = View.inflate(MainActivity204.this, R.layout.yuangong_item_204, null);
                        final YGTopView_204 ygTopView = view_dk.findViewById(R.id.ygtopview);
                        final RelativeLayout root_rl = view_dk.findViewById(R.id.root_rl);
                        final RelativeLayout left_ll = view_dk.findViewById(R.id.left_rl);
                        LottieAnimationView wangluo = view_dk.findViewById(R.id.wangluo);
                        final AutofitTextView xiaoxi = view_dk.findViewById(R.id.xiaoxi);
                        final AutofitTextView biaoqian = view_dk.findViewById(R.id.biaoqian);
                        final AutofitTextView biaoti = view_dk.findViewById(R.id.biaoti);
                        RelativeLayout vvff = view_dk.findViewById(R.id.vfvv);
                        ImageView laba = view_dk.findViewById(R.id.laba);

                        wangluo.setPerformanceTrackingEnabled(true);
                        wangluo.setSpeed(0.6f);

                     //   ygTopView.setHight((int) ((float) dw * 0.20f), (int) ((float) dh * 0.26f));

                        count++;
                        vvff.setBackgroundColor(Color.parseColor(contents.getCocos()[count % 7]));

                        if (isSR) {
                            ygTopView.setName(bean2.getName(), bean2.getDepartment(), true);
                        } else {
                            ygTopView.setName(bean2.getName(), bean2.getDepartment(), false);
                        }


                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (bean2.getAvatar() != null) {

                                    try {

                                        Bitmap bitmapLog = Glide.with(MainActivity204.this).asBitmap()
                                                .load(baoCunBean.getTouxiangzhuji() + bean2.getAvatar())
                                                // .sizeMultiplier(0.5f)
                                                .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                                .get();
                                        ygTopView.setBitmapTX(FileUtil.toRoundBitmap(bitmapLog));

                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }

                            }
                        }).start();


                        //  final LinearLayout xiaoxi_ll = view_dk.findViewById(R.id.xiaoxi_ll);
                        // view_dk.setX(dw);
                        //动画
                        SpringSystem springSystem3 = SpringSystem.create();
                        final Spring spring3 = springSystem3.createSpring();
                        //两个参数分别是弹力系数和阻力系数
                        spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(100, 6));
                        // 添加弹簧监听器
                        spring3.addListener(new SimpleSpringListener() {
                            @Override
                            public void onSpringUpdate(Spring spring) {
                                // value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
                                float value = (float) spring.getCurrentValue();
                                //  Log.d("kkkk", "value:" + value);
                                //基于Y轴的弹簧阻尼动画
                                //	helper.itemView.setTranslationY(value);
                                // 对图片的伸缩动画
                                //float scale = 1f - (value * 0.5f);
                                view_dk.setScaleX(value);
                                view_dk.setScaleY(value);
                            }
                        });
                        // 设置动画结束值
                        spring3.setEndValue(1f);
                        view_dk.setTag(bean2.getId() + "");
                        hengLiebiao.addView(view_dk);

                        if (si > 0) {
                            //有消息
                            view_dk.setEnabled(false);
                            FlexboxLayout.LayoutParams rlLayoutParams = (FlexboxLayout.LayoutParams) root_rl.getLayoutParams();
                            rlLayoutParams.width = (int) ((float) dw * 0.88f);
                            rlLayoutParams.height = (int) ((float) dh * 0.26f);
                            rlLayoutParams.topMargin = (int) ((float) dw * 0.02f);
                            rlLayoutParams.bottomMargin = (int) ((float) dw * 0.02f);
                            root_rl.setLayoutParams(rlLayoutParams);
                            root_rl.invalidate();
                            laba.setVisibility(View.VISIBLE);

                        } else {
                            //没消息
                            FlexboxLayout.LayoutParams rlLayoutParams = (FlexboxLayout.LayoutParams) root_rl.getLayoutParams();
                            rlLayoutParams.width = (int) ((float) dw * 0.88f);
                            rlLayoutParams.height = (int) ((float) dh * 0.26f);
                            rlLayoutParams.topMargin = (int) ((float) dw * 0.02f);
                            rlLayoutParams.bottomMargin = (int) ((float) dw * 0.02f);
                            root_rl.setLayoutParams(rlLayoutParams);
                            root_rl.invalidate();
                            laba.setVisibility(View.INVISIBLE);
                            xiaoxi.setText(contents.getTexts());
                        }

                        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) left_ll.getLayoutParams();
                        layoutParams2.width = (int) ((float) dw * 0.28f);
                        left_ll.setLayoutParams(layoutParams2);
                        left_ll.invalidate();

                        //中间打勾动画的高宽
                        RelativeLayout.LayoutParams wangluop = (RelativeLayout.LayoutParams) wangluo.getLayoutParams();
                        wangluop.leftMargin = (int) ((float) dw * 0.28f)-((int) (((float) dw * 0.07f)/2f));
                        wangluop.width = (int) ((float) dw * 0.07f);
                        wangluop.height = (int) ((float) dw * 0.07f);
                        wangluo.setLayoutParams(wangluop);
                        wangluo.invalidate();


                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < si; i++) {

                                    final int finalI = i;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            // final View view_xiaoxi = View.inflate(MainActivity102.this, R.layout.xiaoxi_item, null);
                                            // LinearLayout rl_xiaoxi = view_xiaoxi.findViewById(R.id.rl_xiaoxi);
                                            // TextView neirong = view_xiaoxi.findViewById(R.id.neirong);
                                            // TextView lingqu = view_xiaoxi.findViewById(R.id.lingqu);
                                            // TextView biaoti = view_xiaoxi.findViewById(R.id.biaoti);
                                            //  ImageView xiaoxi_im = view_xiaoxi.findViewById(R.id.xiaoxi_im);
                                            switch (ygguanHuaiList.get(finalI).getProjectileStatus()) {
                                                case "0":
                                                    //小邮局
                                                    //  xiaoxi_im.setBackgroundResource(R.drawable.youjian_bg);
                                                    try {
                                                        biaoti.setText("邮件");
                                                        biaoqian.setText((finalI + 1) + "/" + si);
                                                        //lingqu.setText(ygguanHuaiList.get(finalI).getNewsStatus());
                                                        xiaoxi.setText(ygguanHuaiList.get(finalI).getMarkedWords());
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    break;
                                                case "1":
                                                    // 生日提醒
                                                    //  xiaoxi_im.setBackgroundResource(R.drawable.shengri_bg2);
                                                    try {
                                                        biaoti.setText("生日");
                                                        xiaoxi.setText(ygguanHuaiList.get(finalI).getMarkedWords());
                                                        // lingqu.setText("");
                                                        biaoqian.setText((finalI + 1) + "/" + si);
                                                        // lingqu.setVisibility(View.GONE);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    break;
                                                case "2":
                                                    //入职关怀
                                                    //  xiaoxi_im.setBackgroundResource(R.drawable.guanhuai_bg);
                                                    try {
                                                        biaoti.setText("入职关怀");
                                                        // lingqu.setText("");
                                                        // lingqu.setVisibility(View.GONE);
                                                        biaoqian.setText((finalI + 1) + "/" + si);
                                                        xiaoxi.setText(ygguanHuaiList.get(finalI).getMarkedWords());
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    break;
                                                case "3":
                                                    //节日关怀
                                                    //  xiaoxi_im.setBackgroundResource(R.drawable.jieri_xx);
                                                    try {
                                                        biaoti.setText("节日关怀");
                                                        //  lingqu.setText("");
                                                        //  lingqu.setVisibility(View.GONE);
                                                        biaoqian.setText((finalI + 1) + "/" + si);
                                                        xiaoxi.setText(ygguanHuaiList.get(finalI).getMarkedWords());
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    break;
                                                case "4":
                                                    //节日关怀
                                                    // xiaoxi_im.setBackgroundResource(R.drawable.ruzhiguahuai_b);
                                                    try {
                                                        biaoti.setText("信息推送");
                                                        //  lingqu.setText("");
                                                        // lingqu.setVisibility(View.GONE);
                                                        xiaoxi.setText(ygguanHuaiList.get(finalI).getMarkedWords());
                                                        biaoqian.setText((finalI + 1) + "/" + si);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    break;
                                            }


                                        }
                                    });
                                    SystemClock.sleep(3000);

                                }
                            }
                        }).start();

                        //消失
                        if (si > 0) {
                            Message message = Message.obtain();
                            message.obj = bean2.getId() + "";
                            message.what = 999;
                            mHandler.sendMessageDelayed(message, 12000);
                        } else {
                            Message message = Message.obtain();
                            message.obj = bean2.getId() + "";
                            message.what = 999;
                            mHandler.sendMessageDelayed(message, 8000);
                        }


                        //////////////////////////////////////////////
                        //底部对列表
                        setBootonView(bean2);



                    }

//
                    case 666: {
                        //普通访客

//                        if (shengRiThierd != null) {
//                            shengRiThierd.interrupt();
//                            shengRiThierd = null;
//                        }
//                        if (fangkeThired != null) {
//                            fangkeThired.interrupt();
//                            fangkeThired = null;
//                        }
//                        if (vipThired != null) {
//                            vipThired.interrupt();
//                            vipThired = null;
//                        }
//
//                        fangkeThired = new FangkeThired();
//                        fangkeThired.start();

                        dabg.setVisibility(View.VISIBLE);

                        final ShiBieBean.PersonBeanSB bean2 = (ShiBieBean.PersonBeanSB) msg.obj;
                        boolean isRT = false;
                        synchronized (MainActivity204.this) {
                            int sizes = hengLiebiao.getChildCount();
                            for (int i = 0; i < sizes; i++) {
                                if ((bean2.getId() + "").equals(hengLiebiao.getChildAt(i).getTag().toString())) {
                                    isRT = true;
                                    break;
                                }
                            }
                        }
                        if (isRT)
                            break;


                        final View view_dk = View.inflate(MainActivity204.this, R.layout.fangke_item_204, null);
                        final FKTopView_204 ygTopView = view_dk.findViewById(R.id.ygtopview);
                        final RelativeLayout root_rl = view_dk.findViewById(R.id.root_rl);
                        AutofitTextView wangluo = view_dk.findViewById(R.id.bumen);
                        wangluo.setText("欢迎你的来访,已通知你的被访人,请到会客区等候,谢谢!");
                        //  wangluo.setPerformanceTrackingEnabled(true);
                        //  wangluo.setSpeed(0.6f);
                        // final ScrollView scrollView_03 = view_dk.findViewById(R.id.scrollview_03);
                        ygTopView.setHight((int) ((float) dw * 0.28f),  (int) ((float) dh * 0.26f));
                        ygTopView.setBitmapHG();
                        ygTopView.setName(bean2.getName(), bean2.getDepartment(), false);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (bean2.getAvatar() != null) {

                                    try {

                                        Bitmap bitmapLog = Glide.with(MainActivity204.this).asBitmap()
                                                .load(baoCunBean.getTouxiangzhuji() + bean2.getAvatar())
                                                // .sizeMultiplier(0.5f)
                                                .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                                .get();
                                        ygTopView.setBitmapTX(FileUtil.toRoundBitmap(bitmapLog));

                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }

                            }
                        }).start();

                        //动画
                        SpringSystem springSystem3 = SpringSystem.create();
                        final Spring spring3 = springSystem3.createSpring();
                        //两个参数分别是弹力系数和阻力系数
                        spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(100, 6));
                        // 添加弹簧监听器
                        spring3.addListener(new SimpleSpringListener() {
                            @Override
                            public void onSpringUpdate(Spring spring) {
                                // value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
                                float value = (float) spring.getCurrentValue();
                                //  Log.d("kkkk", "value:" + value);
                                //基于Y轴的弹簧阻尼动画
                                //	helper.itemView.setTranslationY(value);
                                // 对图片的伸缩动画
                                //float scale = 1f - (value * 0.5f);
                                view_dk.setScaleX(value);
                                view_dk.setScaleY(value);

                            }
                        });
                        // 设置动画结束值
                        spring3.setEndValue(1f);
                        view_dk.setTag(bean2.getId() + "");
                        hengLiebiao.addView(view_dk);


                        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) ygTopView.getLayoutParams();
                        layoutParams2.width = (int) ((float) dw * 0.28f);
                        layoutParams2.leftMargin = (int) ((float) dw * 0.05f);
                        layoutParams2.topMargin = (int) ((float) dw * 0.01f);
                        ygTopView.setLayoutParams(layoutParams2);
                        ygTopView.invalidate();

                        //中间打勾动画的高宽
                        RelativeLayout.LayoutParams wangluop = (RelativeLayout.LayoutParams) wangluo.getLayoutParams();
                        wangluop.leftMargin = (int) ((float) dw * 0.10f);
                        wangluop.rightMargin = (int) ((float) dw * 0.10f);
                      //  wangluop.width = (int) ((float) dw * 0.18f);
                        wangluo.setLayoutParams(wangluop);
                        wangluo.invalidate();



                        ViewUpadte.updataViewGroup("FlexboxLayout", root_rl, (int) ((float) dw * 0.88f),
                                (int) ((float) dh * 0.26f), 0, (int) ((float) dw * 0.02f), 0, (int) ((float) dw * 0.02f));


//                        boolean sskk = false;
//                        for (int i = 0; i < hengLiebiao.getChildCount(); i++) {
//                            if (!hengLiebiao.getChildAt(i).isEnabled()) {
//                                sskk = true;
//                                break;
//                            }
//                        }
//                        if (sskk) {
//                            while (true) {
//                                if (hengLiebiao.getChildCount() > 2) {
//                                    hengLiebiao.removeViewAt(0);
//                                } else {
//                                    break;
//                                }
//                            }
//                        } else {
//                            while (true) {
//                                if (hengLiebiao.getChildCount() > 3) {
//                                    hengLiebiao.removeViewAt(0);
//                                } else {
//                                    break;
//                                }
//                            }
//                        }

                        //消失
                        Message message = Message.obtain();
                        message.obj = bean2.getId() + "";
                        message.what = 999;
                        mHandler.sendMessageDelayed(message, 8000);

                        count++;
                        setBootonView(bean2);

                        break;
                    }
                    case 999: {
                        // Log.d("MainActivity101", "999");

                        final String tag = (String) msg.obj;
                        for (int i = 0; i < hengLiebiao.getChildCount(); i++) {
                            if (hengLiebiao.getChildAt(i).getTag().toString().equals(tag)) {
                                final View vieww = hengLiebiao.findViewWithTag(tag);
                                ValueAnimatorUtils utils = new ValueAnimatorUtils();
                                utils.setIntface(new ValueAnimatorIntface() {
                                    @Override
                                    public void end() {
                                        synchronized (MainActivity204.this) {
                                            hengLiebiao.removeView(vieww);

                                            if (hengLiebiao.getChildCount() == 0 && vipLl.getChildCount() == 0) {
                                                dabg.setVisibility(View.GONE);
                                            }

                                        }
                                    }

                                    @Override
                                    public void update(float value) {
                                        try {
                                            vieww.setX(value);
                                        } catch (Exception e) {
                                            Log.d("MainActivity101", e.getMessage());
                                        }
                                    }

                                    @Override
                                    public void start() {
                                        // boxfargment.setVisibility(View.GONE);
                                    }
                                });
                                utils.animator(vieww.getLeft(), -vieww.getWidth(), 400, 0, 0);
                            }

                        }


                        break;
                    }
                    case -100: {

                        dabg.setVisibility(View.VISIBLE);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.get(MainActivity204.this).clearDiskCache();
                            }
                        }).start();
                        //陌生人
                        final ShiBieBean.PersonBeanSB bean2 = (ShiBieBean.PersonBeanSB) msg.obj;

                        final View view_dk = View.inflate(MainActivity204.this, R.layout.moshengren_item_jj, null);
                        TextView name2 = (TextView) view_dk.findViewById(R.id.name);
                        ImageView touxiang2 = (ImageView) view_dk.findViewById(R.id.touxiang);
                        TextView xingbie = (TextView) view_dk.findViewById(R.id.xingbie);
                        TextView nianling = (TextView) view_dk.findViewById(R.id.nianling);
                        name2.setText(bean2.getName());
                       // xingbie.setText("性别:" + bean2.getSex());
                       // nianling.setText("年龄:" + bean2.getAge());

                        Glide.get(MainActivity204.this).clearMemory();
                        Glide.with(MainActivity204.this)
                                .load(bean2.getImage())
                                .apply(myOptions2)
                                .into(touxiang2);

                        view_dk.setTag(bean2.getId()+"");
                        hengLiebiao.addView(view_dk);

                        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) touxiang2.getLayoutParams();
                        layoutParams2.width = dw / 5;
                        layoutParams2.height = dw / 4;
                        touxiang2.setLayoutParams(layoutParams2);
                        touxiang2.invalidate();

                        //动画
                        SpringSystem springSystem3 = SpringSystem.create();
                        final Spring spring3 = springSystem3.createSpring();
                        //两个参数分别是弹力系数和阻力系数
                        spring3.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(80, 6));
                        // 添加弹簧监听器
                        spring3.addListener(new SimpleSpringListener() {
                            @Override
                            public void onSpringUpdate(Spring spring) {
                                // value是一个符合弹力变化的一个数，我们根据value可以做出弹簧动画
                                float value = (float) spring.getCurrentValue();
                                //  Log.d("kkkk", "value:" + value);
                                //基于Y轴的弹簧阻尼动画
                                //	helper.itemView.setTranslationY(value);
                                // 对图片的伸缩动画
                                //float scale = 1f - (value * 0.5f);
                                view_dk.setScaleX(value);
                                view_dk.setScaleY(value);
                                if (value == 1) {


                                    final View view_dk = View.inflate(MainActivity204.this, R.layout.shulianbiao_jj, null);
                                    TextView name = view_dk.findViewById(R.id.name);
                                    ImageView touxiang = view_dk.findViewById(R.id.touxiang);
                                    name.setText("陌生人");

                                    Glide.with(MainActivity204.this)
                                            .load(bean2.getImage())
                                            .apply(GlideUtils.getRequestOptions())
                                            .into(touxiang);

                                    //         shuLiebiao.addView(view_dk, 0);

                                    RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) touxiang.getLayoutParams();
                                    layoutParams2.width = dw / 9;
                                    layoutParams2.topMargin = 30;
                                    layoutParams2.height = dw / 9;
                                    touxiang.setLayoutParams(layoutParams2);
                                    touxiang.invalidate();
                                    //消失
                                    Message message = Message.obtain();
                                    message.obj = bean2.getId() + "";
                                    message.what = 999;
                                    mHandler.sendMessageDelayed(message, 9000);

                                }
                            }
                        });
                        // 设置动画结束值
                        spring3.setEndValue(1f);
                        break;
                    }
                    case 123: {
                        //生日粒子动画
                        Collections.shuffle(topZuoBiao);
                        for (int i = 0; i < 9; i++) {
                            int min = 0;
                            int max = 8;
                            Random random = new Random();
                            int num = random.nextInt(max) % (max - min + 1) + min;
                            new ParticleSystem(MainActivity204.this, 100, contents.getTopIm()[i], 3000)
                                    .setSpeedModuleAndAngleRange(0.05f, 0.2f, 45, 135)
                                    .setRotationSpeed(30)
                                    .setFadeOut(1000, new LinearInterpolator())
                                    .setAcceleration(0.0001f, 90)
                                    .emit(topZuoBiao.get(num), 0, 1, 100);
                        }

                        break;
                    }
                    case 122: {
                        //vip粒子动画
                        List<Bitmap> tempLists = ((List<Bitmap>) msg.obj);
                        if (tempLists == null)
                            break;
                        int size = tempLists.size();
                        for (int i = 0; i < size; i++) {
                            int min = 0;
                            int max = 5;
                            Random random = new Random();
                            int num = random.nextInt(max) % (max - min + 1) + min;
                            new ParticleSystem(MainActivity204.this, 100, tempLists.get(i), 6000)
                                    .setSpeedModuleAndAngleRange(0.08f, 0.16f, 250, 290)
                                    .setRotationSpeed(0)
                                    .setFadeOut(1000, new LinearInterpolator())
                                    .setAcceleration(0.000004f, 270)
                                    .emit(bootomZuoBiao.get(num), dh + 50, 1, 100);
                        }
                        break;
                    }
                    case 121: {
                        //普通访客粒子动画
                        for (int i = 0; i < 5; i++) {
                            int min = 0;
                            int max = 5;
                            Random random = new Random();
                            int num = random.nextInt(max) % (max - min + 1) + min;

                            int min2 = 0;
                            int max2 = 4;
                            Random random2 = new Random();
                            int num2 = random2.nextInt(max2) % (max2 - min2 + 1) + min2;

                            new ParticleSystem(MainActivity204.this, 100, contents.getQQim()[num2], 7000)
                                    .setSpeedModuleAndAngleRange(0.08f, 0.18f, 250, 290)
                                    .setRotationSpeed(0)
                                    .setFadeOut(1000, new LinearInterpolator())
                                    .setAcceleration(0.000004f, 270)
                                    .emit(bootomZuoBiao.get(num), dh + 260, 1, 100);
                        }

                        break;
                    }

                    case 900: {

                        try {
                            final View vvvv=vipLl.getChildAt(0);

                            ValueAnimator anim = ValueAnimator.ofFloat(1.0f,0f);
                            anim.setDuration(400);
                            anim.setRepeatMode(ValueAnimator.RESTART);
                            Interpolator interpolator = new LinearInterpolator();
                            anim.setInterpolator(interpolator);
                            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    float currentValue = (Float) animation.getAnimatedValue();
                                    vvvv.setScaleX(currentValue);
                                    vvvv.setScaleY(currentValue);
                                    // 步骤5：刷新视图，即重新绘制，从而实现动画效果
                                    vvvv.requestLayout();
                                }
                            });
                            anim.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    vipLl.removeView(vvvv);

                                    if (vipLl.getChildCount() == 0 && hengLiebiao.getChildCount() == 0) {
                                        dabg.setVisibility(View.GONE);
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
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        break;
                    }
                }
                return false;
            }
        });

        isSC = true;

    }

    @Override
    protected void onResume() {
        /* 打开相机 */
      //  manager.open(getWindowManager(), cameraFacingFront, cameraWidth, cameraHeight);
        query = subjectBox.query().equal(Subject_.peopleType, "员工").build();



        super.onResume();
    }







//    private class RecognizeThread extends Thread {
//
//        boolean isInterrupt;
//
//        @Override
//        public void run() {
//            while (!isInterrupt) {
//                try {
//
//                    FacePassDetectionResult detectionResult = mDetectResultQueue.take();
//                    // byte[] detectionResult = mDetectResultQueue.take();
//                    FacePassRecognitionResult[] recognizeResult = mFacePassHandler.recognize(group_name, detectionResult.message);
//                    // Log.d("RecognizeThread", "识别线程");
//                    if (recognizeResult != null && recognizeResult.length > 0) {
//                        // Log.d("RecognizeThread", "recognizeResult.length:" + recognizeResult.length);
//                        for (FacePassRecognitionResult result : recognizeResult) {
//                            //String faceToken = new String(result.faceToken);
//                            if (FacePassRecognitionResultType.RECOG_OK == result.facePassRecognitionResultType) {
//                                //识别的
//                                //  getFaceImageByFaceToken(result.trackId, faceToken);
//                                Log.d("RecognizeThread", "识别了");
//                                //  Log.d("RecognizeThread", subjectBox.getAll().get(0).toString());
//                                Subject subject = subjectBox.query().equal(Subject_.teZhengMa, new String(result.faceToken)).build().findUnique();
//                                // Log.d("RecognizeThread", "subject:" + subject);
//
//                                if (subject != null) {
//                                    linkedBlockingQueue.offer(subject);
//                                    link_shangchuanjilu(subject);
//                                }
//
//
//                            } else {
//                                Log.d("RecognizeThread", "未识别");
//                                //未识别的
//                                // 防止concurrentHashMap 数据过多 ,超过一定数据 删除没用的
//                                if (concurrentHashMap.size() > 10) {
//                                    concurrentHashMap.clear();
//                                }
//                                if (concurrentHashMap.get(result.trackId) == null) {
//                                    //找不到新增
//                                    concurrentHashMap.put(result.trackId, 1);
//                                } else {
//                                    //找到了 把value 加1
//                                    concurrentHashMap.put(result.trackId, (concurrentHashMap.get(result.trackId)) + 1);
//                                }
//                                //判断次数超过3次
//                                if (concurrentHashMap.get(result.trackId) == 2) {
//                                    tID = result.trackId;
//                                    isLink = true;
//                                    //   Log.d("RecognizeThread", "入库"+tID);
//                                }
//
//                            }
//
//                        }
//                    }
//
//                } catch (InterruptedException | FacePassException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        @Override
//        public void interrupt() {
//            isInterrupt = true;
//            super.interrupt();
//        }
//
//    }


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
                                messagey.what = 444;
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
                                        Glide.get(MainActivity204.this).clearDiskCache();
                                    }
                                }).start();
                                Message message = Message.obtain();
                                message.obj = subject;
                                message.what = -100;
                                mHandler.sendMessage(message);

                                break;
                            case -2:

                                break;
                            default:
                                EventBus.getDefault().post("没有对应身份类型,无法弹窗");

                        }

                    SystemClock.sleep(100);

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

    @Override
    protected void onPause() {

        synchronized (MainActivity204.this) {
            //  hengLiebiao.removeAllViews();
        }
        super.onPause();
    }


    private void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        int windowRotation = ((WindowManager) (getApplicationContext().getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getRotation() * 90;

        //  Log.i("orientation", String.valueOf(windowRotation));
        final int mCurrentOrientation = getResources().getConfiguration().orientation;
        if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            screenState = 1;
        } else if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            screenState = 0;
        }

        setContentView(R.layout.activity_main204);
        //  ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        AssetManager mgr = getAssets();
        //Univers LT 57 Condensed
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Univers LT 57 Condensed.ttf");
        Typeface tf2 = Typeface.createFromAsset(mgr, "fonts/hua.ttf");
        Typeface tf3 = Typeface.createFromAsset(mgr, "fonts/kai.ttf");
        // String riqi2 = DateUtils.timesTwo(System.currentTimeMillis() + "") + "   " + DateUtils.getWeek(System.currentTimeMillis());
        //  riqi.setTypeface(tf);
        //    riqi.setText(riqi2);
        xingqi.setText(DateUtils.getWeek(System.currentTimeMillis()));
        riqi.setText(DateUtils.timesTwodian(System.currentTimeMillis() + ""));
        xiaoshi.setTypeface(tf);
        xiaoshi.setText(DateUtils.timeMinute(System.currentTimeMillis() + ""));
        shezhiTv=findViewById(R.id.shezhi_tv);
        // TableLayout mHudView = findViewById(R.id.hud_view);
        // shipingView = findViewById(R.id.ijkplayview);
        // shipingView.setVisibility(View.GONE);

        // rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        //   @Override
        //  public void onGlobalLayout() {


        //只需要获取一次高度，获取后移除监听器
        //   rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        //   rootLayout.setVisibility(View.GONE);
        //    }

        //   });
        logo=findViewById(R.id.logo);
        banner = findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        //设置轮播时间
        banner.setDelayTime(2500);
        //banner设置方法全部调用完毕时最后调用

        if (baoCunBean.getWenzi1() != null)
            companyName.setText(baoCunBean.getWenzi1());
        if (lunBoBeanBox.getAll().size() > 0) {
            imagesList.clear();
            banner.setVisibility(View.VISIBLE);
            ijkplayview.setVisibility(View.GONE);
            List<LunBoBean> strings = lunBoBeanBox.getAll();
            for (LunBoBean ss : strings) {
                imagesList.add(ss.getPath());
            }
            banner.setImages(imagesList);
            banner.start();
        } else {
            banner.setImages(imagesList);
            banner.start();
            banner.setVisibility(View.GONE);
            ijkplayview.setVisibility(View.VISIBLE);
        }

        //  Log.d("MainActivity102", baoCunBean.getShiPingWeiZhi()+"hhhhhhh");
        if (baoCunBean.getShiPingWeiZhi() != null) {
            banner.setVisibility(View.GONE);
            ijkplayview.setVisibility(View.VISIBLE);

            ijkplayview.setVideoPath(baoCunBean.getShiPingWeiZhi());
            ijkplayview.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(IMediaPlayer iMediaPlayer) {
                    ijkplayview.setVideoPath(baoCunBean.getShiPingWeiZhi());
                    ijkplayview.start();

                }
            });
            ijkplayview.start();

        }

        //背景
        // daBg.setBackgroundResource(R.color.dabg);
        //  scrollView.setSmoothScrollingEnabled(true);
        //   ceshi = findViewById(R.id.ceshi);
        //   ceshi.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //       startActivity(new Intent(MainActivity102.this, SheZhiActivity.class));
        //      finish();

//                if (usbPath != null) {
//
//                    ToastUtils2.getInstances().showDialog("获取图片", "获取图片", 0);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            List<String> strings = new ArrayList<>();
//                            FileUtil.getAllFiles(usbPath + File.separator + "入库照片2", strings);
//                            FacePassHandler facePassHandler = MyApplication.myApplication.getFacePassHandler();
//
//                            int size = strings.size();
//                            for (int i = 0; i < size; i++) {
//                                try {
//                                    String sp = strings.get(i);
//                                    Log.d("SheZhiActivity", sp);
//                                    Log.d("SheZhiActivity", "i" + i);
//                                    FacePassAddFaceResult result = facePassHandler.addFace(BitmapFactory.decodeFile(sp));
//                                    if (result.result == 0) {
//                                        facePassHandler.bindGroup(group_name, result.faceToken);
//                                        Subject subject = new Subject();
//                                        int oo = sp.length();
//                                        subject.setName(sp.substring(oo - 6, oo - 1));
//                                        subject.setTeZhengMa(new String(result.faceToken));
//                                        subject.setPeopleType("员工");
//                                        subject.setId(System.currentTimeMillis());
//                                        subjectBox.put(subject);
//
//                                    } else {
//                                        shibai++;
//                                    }
//
//                                    ToastUtils2.getInstances().showDialog("入库中", "失败了:" + shibai, (i / size) * 100);
//                                } catch (FacePassException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }
//
//                    }).start();
//                }
        //       }
        //  });


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        heightPixels = displayMetrics.heightPixels;
        widthPixels = displayMetrics.widthPixels;




        if (baoCunBean.getXiaBanTime() != null) {
            logo.setImageBitmap(BitmapFactory.decodeFile(baoCunBean.getXiaBanTime()));
        }

        shidu.setText("湿度:50%");
        tianqiIm.setBackgroundResource(R.drawable.qing);
        wendu.setText("20C/22C");
        tianqi.setText("晴转多云");

        if (todayBean != null) {
            //更新天气界面
            wendu.setTypeface(tf2);
            tianqi.setTypeface(tf2);
            tianqi.setText(todayBean.getWeather());
            // fengli.setTypeface(tf2);
            // ziwaixian.setTypeface(tf2);
            shidu.setTypeface(tf2);
            //  jianyi.setTypeface(tf3);
            shidu.setText("湿度:" + todayBean.getHumidity());
            //  xingqi2.setText(DateUtils.getWeek(System.currentTimeMillis() + 86400000L));
            //  xingqi3.setText(DateUtils.getWeek(System.currentTimeMillis() + 86400000L + 86400000L));
            wendu.setText(todayBean.getTemperature().replace("℃~", "/"));
            // wendu2.setText(todayBean.getTianqi2_wendu().replace("℃~", "/"));
            // wendu3.setText(todayBean.getTianqi3_wendu().replace("℃~", "/"));

            if (todayBean.getWeather().contains("晴")) {
                tianqiIm.setBackgroundResource(R.drawable.qing);
            } else if (todayBean.getWeather().contains("雨")) {
                tianqiIm.setBackgroundResource(R.drawable.xiayu);
            } else if (todayBean.getWeather().contains("多云")) {
                tianqiIm.setBackgroundResource(R.drawable.duoyun);
            } else if (todayBean.getWeather().contains("阴")) {
                tianqiIm.setBackgroundResource(R.drawable.yintian);
            }



//            if (todayBean.getTianqi2().contains("晴")) {
//                tianqiIm2.setBackgroundResource(R.drawable.qing);
//            } else if (todayBean.getTianqi2().contains("雨")) {
//                tianqiIm2.setBackgroundResource(R.drawable.xiayu);
//            } else if (todayBean.getTianqi2().contains("多云")) {
//                tianqiIm2.setBackgroundResource(R.drawable.duoyun);
//            } else if (todayBean.getTianqi2().contains("阴")) {
//                tianqiIm2.setBackgroundResource(R.drawable.yintian);
//            }
//
//
//            if (todayBean.getTianqi3().contains("晴")) {
//                tianqiIm3.setBackgroundResource(R.drawable.qing);
//            } else if (todayBean.getTianqi3().contains("雨")) {
//                tianqiIm3.setBackgroundResource(R.drawable.xiayu);
//            } else if (todayBean.getTianqi3().contains("多云")) {
//                tianqiIm3.setBackgroundResource(R.drawable.duoyun);
//            } else if (todayBean.getTianqi3().contains("阴")) {
//                tianqiIm3.setBackgroundResource(R.drawable.yintian);
//            }

//            shezhiTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("MainActivity102", "粉色的房贷首付");
//                    startActivity(new Intent(MainActivity204.this,SheZhiActivity.class));
//                    finish();
//                }
//            });

        }

//        RelativeLayout.LayoutParams ppp0 = (RelativeLayout.LayoutParams) zidongtext.getLayoutParams();
//        ppp0.width = (int) ((float) dw * 0.7f);
//        ppp0.height = (int) ((float) dw * 0.21f);
//        ppp0.topMargin = -10;
//        zidongtext.setLayoutParams(ppp0);
//        zidongtext.invalidate();
//     //   Log.d("MainActivity101", baoCunBean.getWenzi1() + "jjjjj");
//        if (baoCunBean.getWenzi1() != null)
//            zidongtext.setText(baoCunBean.getWenzi1());
//        if (baoCunBean.getTouxiangzhuji() != null)
//            daBg.setImageBitmap(BitmapFactory.decodeFile(baoCunBean.getTouxiangzhuji()));

        // ViewUpadte.updataViewGroup("RelativeLayout", fgrt, 0, (int) ((dh * 0.15f)/3.0), 0, 0, 0, 0);


        ViewUpadte.updataView("LinearLayout", tianqiIm, (int) (dw * 0.050f), (int) (dw * 0.050f), 0, 0, 0, 0);

        // ViewUpadte.updataView("LinearLayout", wendu,(int) (dw * 0.10f),0, 0, 0, 0, 0);
        //  ViewUpadte.updataView("LinearLayout", shidu, (int) (dw * 0.10f), 0, 0, 0, 0, 0);


        ViewUpadte.updataViewGroup("LinearLayout", tianqiRL, (int) (dh * 0.12f), 0, 0, 0, 0, 0);


        ViewUpadte.updataViewGroup("RelativeLayout", hengLiebiao, (int) ((float) dw * 0.88f), 0, 0, 0, 0, 0);

        ViewUpadte.updataViewGroup("LinearLayout", bottomLl, 0, 0, 10, 0, 10, 0);

        ViewUpadte.updataViewGroup("RelativeLayout", vipLl, 0, 0, (int) (dw * 0.036f), (int) (dh * 0.08f), (int) (dw * 0.036f), (int) (dh * 0.08f));


    }


    public void oncv(View view){
      //  Log.d("MainActivity102", "粉色的房贷首付");
        startActivity(new Intent(MainActivity204.this,SheZhiActivity.class));
        finish();

    }


    @Override
    protected void onStop() {


        mToastBlockQueue.clear();

        linkedBlockingQueue.clear();

        // marqueeView.stopFlipping();
        if (synthesizer != null)
            synthesizer.release();

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


        if (ijkplayview!=null)
        ijkplayview.release(true);

        //  shipingView.release(true);
        unregisterReceiver(timeChangeReceiver);
        unregisterReceiver(netWorkStateReceiver);
        EventBus.getDefault().unregister(this);//解除订阅


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
        Toast.makeText(MainActivity204.this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                startActivity(new Intent(MainActivity204.this, SheZhiActivity.class));
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        //  Log.d("MainActivity", "ev.getPointerCount()1:" + ev.getPointerCount());
        //   Log.d("MainActivity", "ev.getAction()1:" + ev.getAction());

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            isAnXia = true;
        }
        if (isAnXia) {
            if (ev.getPointerCount() == 4) {
                isAnXia = false;
                startActivity(new Intent(MainActivity204.this, SheZhiActivity.class));
                finish();
            }
        }
        return super.dispatchTouchEvent(ev);
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
        InitConfig initConfig = new InitConfig(contents.appId, contents.appKey, contents.secretKey, contents.ttsMode, params, listener);
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
        OfflineResource offlineResource = createOfflineResource(contents.offlineVoice);
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


    //信鸽信息处理
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(XGBean xgBean) {

            try {

               tsxxChuLi.setData(xgBean, MainActivity204.this);
            }catch (Exception e){
                e.printStackTrace();
            }



    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(String event) {
        if (event.equals("mFacePassHandler")) {
          //  mFacePassHandler = MyApplication.myApplication.getFacePassHandler();
            // diBuAdapter = new DiBuAdapter(dibuList, MainActivity202.this, dibuliebiao.getWidth(), dibuliebiao.getHeight(), mFacePassHandler);
            //  dibuliebiao.setLayoutManager(gridLayoutManager);
            //  dibuliebiao.setAdapter(diBuAdapter);
            return;
        }

        if (event.equals("ditu123")) {
            // if (baoCunBean.getTouxiangzhuji() != null)
            //    daBg.setImageBitmap(BitmapFactory.decodeFile(baoCunBean.getTouxiangzhuji()));
            baoCunBean = baoCunBeanDao.get(123456L);
            if (baoCunBean.getWenzi1() != null)
                companyName.setText(baoCunBean.getWenzi1());

            if (baoCunBean.getXiaBanTime() != null) {
                logo.setImageBitmap(BitmapFactory.decodeFile(baoCunBean.getXiaBanTime()));
            }
            //   Log.d("MainActivity101", "dfgdsgfdgfdgfdg");
            return;
        }

        if (event.equals("lunbotu")) {
            if (lunBoBeanBox.getAll().size() > 0) {
                banner.releaseBanner();
                imagesList.clear();
                banner.setVisibility(View.VISIBLE);
                ijkplayview.setVisibility(View.GONE);
                if (ijkplayview != null && ijkplayview.isPlaying()) {
                    ijkplayview.pause();
                }
                List<LunBoBean> strings = lunBoBeanBox.getAll();
                for (LunBoBean ss : strings) {
                    imagesList.add(ss.getPath());
                }
                banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                //设置轮播时间
                banner.setDelayTime(2500);
                banner.setImages(imagesList);
                banner.start();
                // banner.start();
            } else {
                banner.setVisibility(View.GONE);
                ijkplayview.setVisibility(View.VISIBLE);
            }
            // Log.d("MainActivity101", "dfgdsgfdgfdgfdg");

            return;
        }
        if (event.equals("shiping")) {
            baoCunBean = baoCunBeanDao.get(123456L);

            banner.setVisibility(View.GONE);
            ijkplayview.setVisibility(View.VISIBLE);
            ijkplayview.setVideoPath(baoCunBean.getShiPingWeiZhi());
            ijkplayview.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(IMediaPlayer iMediaPlayer) {
                    ijkplayview.setVideoPath(baoCunBean.getShiPingWeiZhi());
                    ijkplayview.start();
                }
            });
            ijkplayview.start();

            return;
        }


        Toast tastyToast = TastyToast.makeText(MainActivity204.this, event, TastyToast.LENGTH_LONG, TastyToast.INFO);
        tastyToast.setGravity(Gravity.CENTER, 0, 0);
        tastyToast.show();

    }


    class TimeChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (Objects.requireNonNull(intent.getAction())) {
                case Intent.ACTION_TIME_TICK:

                    AssetManager mgr = getAssets();
                    //Univers LT 57 Condensed
                    Typeface tf = Typeface.createFromAsset(mgr, "fonts/Univers LT 57 Condensed.ttf");
                    //   Typeface tf2 = Typeface.createFromAsset(mgr, "fonts/hua.ttf");
                    //   Typeface tf3 = Typeface.createFromAsset(mgr, "fonts/kai.ttf");
                    //   String riqi2 = DateUtils.timesTwo(System.currentTimeMillis() + "") + "   " + DateUtils.getWeek(System.currentTimeMillis());
                    //  riqi.setTypeface(tf);
                    //  riqi.setText(riqi2);
                    //    xiaoshi.setTypeface(tf);

                    String xiaoshiss = DateUtils.timeMinute(System.currentTimeMillis() + "");
                    if (xiaoshiss.split(":")[0].equals("06") && xiaoshiss.split(":")[1].equals("30")) {

                        final List<BenDiJiLuBean> benDiJiLuBeans = benDiJiLuBeanBox.getAll();
                        final int size = benDiJiLuBeans.size();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < size; i++) {
                                    while (isSC) {
                                        isSC = false;
                                        link_shangchuanjilu2(benDiJiLuBeans.get(i));
                                    }

                                }

                            }
                        }).start();

                    }

                    if (xiaoshiss.split(":")[0].equals("22") && xiaoshiss.split(":")[1].equals("30")){
                        //删除今天的访客
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                List<Subject> subjectList=subjectBox.query().contains(Subject_.birthday,DateUtils.nyr(System.currentTimeMillis()+"")).and()
                                        .equal(Subject_.peopleType,"普通访客")
                                        .or().equal(Subject_.peopleType,"白名单").build().find();
                                for (Subject s:subjectList){
                                    Log.d("TimeChangeReceiver", s.toString());
                                    try {
                                       // mFacePassHandler.deleteFace(s.getTeZhengMa().getBytes());
                                        subjectBox.remove(s);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }

                            }
                        }).start();

                    }
                    if (xiaoshiss.split(":")[0].equals("10") && xiaoshiss.split(":")[1].equals("01")){
                        //如果用户在22：30 前关了机 就需要第二天早上删除昨天的访客
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                List<Subject> subjectList=subjectBox.query().contains(Subject_.birthday,DateUtils.nyr((System.currentTimeMillis()-86400000)+"")).and()
                                        .equal(Subject_.peopleType,"普通访客")
                                        .or().equal(Subject_.peopleType,"白名单").build().find();
                                for (Subject s:subjectList){
                                    Log.d("TimeChangeReceiver", s.toString());
                                    try {
                                      //  mFacePassHandler.deleteFace(s.getTeZhengMa().getBytes());
                                        subjectBox.remove(s);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }

                            }
                        }).start();

                    }



                    xiaoshi.setText(xiaoshiss);
                    xingqi.setText(DateUtils.getWeek(System.currentTimeMillis()));
                    riqi.setText(DateUtils.timesTwodian(System.currentTimeMillis() + ""));
                    //  xingqi2.setText(DateUtils.getWeek(System.currentTimeMillis() + 86400000L));
                    //  xingqi3.setText(DateUtils.getWeek(System.currentTimeMillis() + 86400000L + 86400000L));


                    Date date = new Date();
                    date.setTime(System.currentTimeMillis());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int t = calendar.get(Calendar.HOUR_OF_DAY);

                    //每过一分钟 触发
                    if (baoCunBean != null && baoCunBean.getDangqianShiJian() != null && !baoCunBean.getDangqianShiJian().equals(DateUtils.timesTwo(System.currentTimeMillis() + "")) && t >= 6) {

                        //一天请求一次
                        try {
                            if (baoCunBean.getDangqianChengShi2() == null) {
                                Toast tastyToast = TastyToast.makeText(MainActivity204.this, "获取天气失败,没有设置当前城市", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();
                                return;
                            }
                            //  Log.d("TimeChangeReceiver", baoCunBean.getDangqianChengShi());
                            OkHttpClient okHttpClient = new OkHttpClient();
                            Request.Builder requestBuilder = new Request.Builder()
                                    .get()
                                    .url("http://v.juhe.cn/weather/index?format=1&cityname=" + baoCunBean.getDangqianChengShi() + "&key=356bf690a50036a5cfc37d54dc6e8319");
                            // step 3：创建 Call 对象
                            Call call = okHttpClient.newCall(requestBuilder.build());
                            //step 4: 开始异步请求
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Log.d("AllConnects", "请求失败" + e.getMessage());
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    Log.d("AllConnects", "请求成功" + call.request().toString());
                                    //获得返回体
                                    try {

                                        ResponseBody body = response.body();
                                        String ss = body.string().trim();
                                        Log.d("AllConnects", "天气" + ss);
                                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                                        Gson gson = new Gson();
                                        final TianQiBean renShu = gson.fromJson(jsonObject, TianQiBean.class);
                                        final TodayBean todayBean = new TodayBean();
                                        todayBean.setId(123456L);
                                        todayBean.setTemperature(renShu.getResult().getToday().getTemperature());//温度
                                        todayBean.setWeather(renShu.getResult().getToday().getWeather()); //天气
                                        todayBean.setWind(renShu.getResult().getToday().getWind()); //风力
                                        todayBean.setUv_index(renShu.getResult().getToday().getUv_index()); //紫外线
                                        todayBean.setHumidity(renShu.getResult().getSk().getHumidity());//湿度
                                        todayBean.setDressing_advice(renShu.getResult().getToday().getDressing_advice());
                                        String tianqi1 = jsonObject.get("result").getAsJsonObject().get("future").getAsJsonObject().get("day_" + DateUtils.yrn((System.currentTimeMillis() + 86400000L) + ""))
                                                .getAsJsonObject().get("weather").getAsString();
                                        String tianqi1_wendu = jsonObject.get("result").getAsJsonObject().get("future").getAsJsonObject().get("day_" + DateUtils.yrn((System.currentTimeMillis() + 86400000L) + ""))
                                                .getAsJsonObject().get("temperature").getAsString();
                                        String tianqi2 = jsonObject.get("result").getAsJsonObject().get("future").getAsJsonObject().get("day_" + DateUtils.yrn((System.currentTimeMillis() + 86400000L + 86400000L) + ""))
                                                .getAsJsonObject().get("weather").getAsString();
                                        String tianqi2_wendu = jsonObject.get("result").getAsJsonObject().get("future").getAsJsonObject().get("day_" + DateUtils.yrn((System.currentTimeMillis() + 86400000L + 86400000L) + ""))
                                                .getAsJsonObject().get("temperature").getAsString();
                                        Log.d("TimeChangeReceiver", tianqi1 + "retretretretre" + tianqi1_wendu);
                                        todayBean.setTianqi2(tianqi1);
                                        todayBean.setTianqi2_wendu(tianqi1_wendu);
                                        todayBean.setTianqi3(tianqi2);
                                        todayBean.setTianqi3_wendu(tianqi2_wendu);
                                        // todayBean.setTianqi2();

                                        todayBeanBox.put(todayBean);
                                        baoCunBean.setDangqianShiJian(DateUtils.timesTwo(System.currentTimeMillis() + ""));
                                        baoCunBeanDao.put(baoCunBean);
                                        //更新界面
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                AssetManager mgr = getAssets();
                                                //  Univers LT 57 Condensed
                                                Typeface tf = Typeface.createFromAsset(mgr, "fonts/Univers LT 57 Condensed.ttf");
                                                Typeface tf2 = Typeface.createFromAsset(mgr, "fonts/hua.ttf");
                                                String riqi2 = DateUtils.timesTwo(System.currentTimeMillis() + "") + "   " + DateUtils.getWeek(System.currentTimeMillis());

                                                wendu.setTypeface(tf2);
                                                tianqi.setTypeface(tf2);
                                                tianqi.setText(todayBean.getWeather());
                                                //  fengli.setTypeface(tf2);
                                                // ziwaixian.setTypeface(tf2);
                                                shidu.setTypeface(tf2);
                                                // jianyi.setTypeface(tf2);

                                                xingqi.setText(DateUtils.getWeek(System.currentTimeMillis()));
                                                riqi.setText(DateUtils.timesTwodian(System.currentTimeMillis() + ""));
                                                shidu.setText("湿度：" + todayBean.getHumidity());
                                                // xingqi2.setText(DateUtils.getWeek(System.currentTimeMillis() + 86400000L));
                                                // xingqi3.setText(DateUtils.getWeek(System.currentTimeMillis() + 86400000L + 86400000L));
                                                wendu.setText(todayBean.getTemperature().replace("℃~", "/"));
                                                // wendu2.setText(todayBean.getTianqi2_wendu().replace("℃~", "/"));
                                                //  wendu3.setText(todayBean.getTianqi3_wendu().replace("℃~", "/"));

                                                if (todayBean.getWeather().contains("晴")) {
                                                    tianqiIm.setBackgroundResource(R.drawable.qing);
                                                } else if (todayBean.getWeather().contains("雨")) {
                                                    tianqiIm.setBackgroundResource(R.drawable.xiayu);
                                                } else if (todayBean.getWeather().contains("多云")) {
                                                    tianqiIm.setBackgroundResource(R.drawable.duoyun);
                                                } else if (todayBean.getWeather().contains("阴")) {
                                                    tianqiIm.setBackgroundResource(R.drawable.yintian);
                                                }
//
//                                                if (todayBean.getTianqi2().contains("晴")) {
//                                                    tianqiIm2.setBackgroundResource(R.drawable.qing);
//                                                } else if (todayBean.getTianqi2().contains("雨")) {
//                                                    tianqiIm2.setBackgroundResource(R.drawable.xiayu);
//                                                } else if (todayBean.getTianqi2().contains("多云")) {
//                                                    tianqiIm2.setBackgroundResource(R.drawable.duoyun);
//                                                } else if (todayBean.getTianqi2().contains("阴")) {
//                                                    tianqiIm2.setBackgroundResource(R.drawable.yintian);
//                                                }
//
//
//                                                if (todayBean.getTianqi3().contains("晴")) {
//                                                    tianqiIm3.setBackgroundResource(R.drawable.qing);
//                                                } else if (todayBean.getTianqi3().contains("雨")) {
//                                                    tianqiIm3.setBackgroundResource(R.drawable.xiayu);
//                                                } else if (todayBean.getTianqi3().contains("多云")) {
//                                                    tianqiIm3.setBackgroundResource(R.drawable.duoyun);
//                                                } else if (todayBean.getTianqi3().contains("阴")) {
//                                                    tianqiIm3.setBackgroundResource(R.drawable.yintian);
//                                                }

                                            }
                                        });
                                        //把所有人员的打卡信息重置
                                        List<Subject> subjectList = subjectBox.getAll();
                                        for (Subject s : subjectList) {
                                            s.setDaka(0);
                                            subjectBox.put(s);
                                        }

                                    } catch (Exception e) {
                                        Log.d("WebsocketPushMsg", e.getMessage() + "ttttt");
                                    }

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast tastyToast = TastyToast.makeText(MainActivity204.this, "获取天气失败,没有设置当前城市", TastyToast.LENGTH_LONG, TastyToast.INFO);
                            tastyToast.setGravity(Gravity.CENTER, 0, 0);
                            tastyToast.show();
                            return;
                        }

                    }
                    //  Toast.makeText(context, "1 min passed", Toast.LENGTH_SHORT).show();
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
                Log.d("ggg", "刷脸监听1");
                //断线重连
                Log.d("ggg", "刷脸监听");
                if (isLianJie) {
                    Log.d("ggg", "进来2");
                    try {
                        play();
                        Log.d("ggg", baoCunBean.getTouxiangzhuji() + "ddddd");
                        Log.d("ggg", baoCunBean.getShipingIP());
                        if (baoCunBean.getTouxiangzhuji() != null && baoCunBean.getShipingIP() != null) {
                            Log.d("ggg", "jin");
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


//    //轮播适配器
//    private class TestNomalAdapter extends StaticPagerAdapter {
//        private int[] imgs = {
//                R.drawable.dbg_1,
//                R.drawable.ceshi,
//                R.drawable.ceshi3,
//        };
//
//        @Override
//        public View getView(ViewGroup container, int position) {
//            ImageView view = new ImageView(container.getContext());
//            view.setImageResource(imgs[position]);
//            view.setScaleType(ImageView.ScaleType.FIT_XY);
//            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            return view;
//        }
//
//        @Override
//        public int getCount() {
//            return imgs.length;
//        }
//    }


    //上传识别记录
    private void link_shangchuanjilu(final Subject subject) {
      //  final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = null;

        body = new FormBody.Builder()
                //.add("name", subject.getName()) //
                //.add("companyId", subject.getCompanyId()+"") //公司di
                //.add("companyName",subject.getCompanyName()+"") //公司名称
                //.add("storeId", subject.getStoreId()+"") //门店id
                //.add("storeName", subject.getStoreName()+"") //门店名称
                .add("subjectId", subject.getId() + "") //员工ID
                .add("subjectType", subject.getPeopleType()) //人员类型
                // .add("department", subject.getPosition()+"") //部门
                .add("discernPlace",baoCunBean.getTuisongDiZhi()+"")//识别地点
                // .add("discernAvatar",  "") //头像
                .add("identificationTime", DateUtils.time(System.currentTimeMillis() + ""))//时间
                .build();


        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .post(body)
                .url(baoCunBean.getHoutaiDiZhi() + "/app/historySave");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                BenDiJiLuBean bean = new BenDiJiLuBean();
                bean.setSubjectId(subject.getId());
                bean.setDiscernPlace(baoCunBean.getTuisongDiZhi()+"");
                bean.setSubjectType(subject.getPeopleType());
                bean.setIdentificationTime(DateUtils.time(System.currentTimeMillis() + ""));
                benDiJiLuBeanBox.put(bean);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "上传识别记录" + ss);


                } catch (Exception e) {
                    BenDiJiLuBean bean = new BenDiJiLuBean();
                    bean.setSubjectId(subject.getId());
                    bean.setDiscernPlace(baoCunBean.getTuisongDiZhi()+"");
                    bean.setSubjectType(subject.getPeopleType());
                    bean.setIdentificationTime(DateUtils.time(System.currentTimeMillis() + ""));
                    benDiJiLuBeanBox.put(bean);

                    Log.d("WebsocketPushMsg", e.getMessage() + "gggg");
                }
            }
        });
    }

    //上传识别记录2
    private void link_shangchuanjilu2(final BenDiJiLuBean subject) {
      //  final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = null;

        body = new FormBody.Builder()
                //.add("name", subject.getName()) //
                //.add("companyId", subject.getCompanyId()+"") //公司di
                //.add("companyName",subject.getCompanyName()+"") //公司名称
                //.add("storeId", subject.getStoreId()+"") //门店id
                //.add("storeName", subject.getStoreName()+"") //门店名称
                .add("subjectId", subject.getSubjectId() + "") //员工ID
                .add("subjectType", subject.getSubjectType() + "") //人员类型
                // .add("department", subject.getPosition()+"") //部门
                .add("discernPlace", baoCunBean.getTuisongDiZhi() + "")//识别地点
                // .add("discernAvatar",  "") //头像
                .add("identificationTime", subject.getIdentificationTime() + "")//时间
                .build();


        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .post(body)
                .url(baoCunBean.getHoutaiDiZhi() + "/app/historySave");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                isSC = true;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "上传识别记录" + ss);
                    //成功的话 删掉本地保存的记录
                    benDiJiLuBeanBox.remove(subject.getId());

                } catch (Exception e) {

                    Log.d("WebsocketPushMsg", e.getMessage() + "gggg");

                } finally {
                    isSC = true;
                }
            }
        });
    }


    private class ShengRiThierd extends Thread {
        @Override
        public void run() {
            int i = 0;
            while (!this.isInterrupted()) {
                Message message = Message.obtain();
                message.what = 123;
                mHandler.sendMessage(message);
                SystemClock.sleep(1000);
                i++;
                if (i >= 6) {
                    break;
                }
            }

        }
    }

    private class FangkeThired extends Thread {
        @Override
        public void run() {
            int i = 0;
            while (!this.isInterrupted()) {
                Message message = Message.obtain();
                message.what = 121;
                mHandler.sendMessage(message);
                SystemClock.sleep(1000);
                i++;
                if (i >= 4) {
                    break;
                }
            }
        }
    }

    private class VipThired extends Thread {
        @Override
        public void run() {
            LazyList<Subject> subjects = query.findLazy();
            RandomDataUtil util = new RandomDataUtil();
            List<Bitmap> bitmapListAmin = new ArrayList<>();
            //  Log.d("VipThired", "subjects:" + subjects.size());
            if (subjects.size() > 30) {
                List<Subject> subjectList = util.generateRandomDataNoRepeat(subjects, 30);
                //  Log.d("VipThired", "subjectList.size():" + subjectList.size());
                for (Subject subject : subjectList) {
//                    try {
//                        Bitmap bitmap = mFacePassHandler.getFaceImage(subject.getTeZhengMa().getBytes());
//                        bitmapListAmin.add(FileUtil.toRoundBitmap2(bitmap, BitmapFactory.decodeResource(getResources(), R.drawable.xinxinxin), 100, 100));
//                    } catch (FacePassException e) {
//                        Log.d("VipThired", e.getMessage() + "获取vip动画bitmap异常");
//                    }
                }
            } else {

                for (Subject subject : subjects) {
//                    try {
//                        Bitmap bitmap = mFacePassHandler.getFaceImage(subject.getTeZhengMa().getBytes());
//                        bitmapListAmin.add(FileUtil.toRoundBitmap2(bitmap, BitmapFactory.decodeResource(getResources(), R.drawable.xinxinxin), 100, 100));
//                    } catch (FacePassException e) {
//                        Log.d("VipThired", e.getMessage() + "获取vip动画bitmap异常");
//                    }
                }
                //Collections.shuffle(bitmapListAmin);
            }
            //   Log.d("VipThired", "subjects.size():" + subjects.size());
            int j = 0;
            while (!this.isInterrupted()) {
                //   Collections.shuffle(bitmapListAmin);
                List<Bitmap> tempLS = new ArrayList<>();
                int tem = j * 6;
                int tesi = bitmapListAmin.size();
                for (int i = 0; i < 6; i++) {
                    if (tesi > tem + i) {
                        tempLS.add(bitmapListAmin.get(tem + i));
                    }
                }
                Message message = Message.obtain();
                message.what = 122;
                message.obj = tempLS;
                mHandler.sendMessage(message);
                SystemClock.sleep(1000);

                j++;
                if (j >= 5) {
                    break;
                }

            }


        }
    }


    public static class UsbBroadCastReceiver extends BroadcastReceiver {

        public UsbBroadCastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() != null && intent.getAction().equals("android.intent.action.MEDIA_MOUNTED")) {
                usbPath = intent.getData().getPath();
                List<String> sss = FileUtil.getMountPathList();
                int size = sss.size();
                for (int i = 0; i < size; i++) {

                    if (sss.get(i).contains(usbPath)) {
                        usbPath = sss.get(i);
                    }

                }

                Log.d("UsbBroadCastReceiver", usbPath);
            }


        }
    }

    private static String usbPath = null;


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


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Glide 加载图片简单用法
            Log.d("GlideImageLoader", ((String) path));
            Glide.with(context).load(new File((String) path)).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            // Uri uri = Uri.parse((String) path);
            // imageView.setImageURI(uri);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
//        @Override
//        public ImageView createImageView(Context context) {
//            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//            return simpleDraweeView;
//        }
    }

    private void setBootonView(ShiBieBean.PersonBeanSB bean2) {
        if (count >= 7) {
            count = -1;
            count++;
        }

        int ontWidth = (bot_ll_width / 6) - 20;

        final View view_bottom = View.inflate(MainActivity204.this, R.layout.botton_item204, null);
       // ImageView topbg = view_bottom.findViewById(R.id.topbg);
        ImageView touxiang = view_bottom.findViewById(R.id.touxiang);
        AutofitTextView name = view_bottom.findViewById(R.id.name);
        AutofitTextView riqi = view_bottom.findViewById(R.id.riqi);
        RelativeLayout toprl = view_bottom.findViewById(R.id.toprl);

        name.setText(bean2.getName());
        riqi.setText(DateUtils.timeMinute(System.currentTimeMillis() + ""));
        try {
            if (bean2.getAvatar() != null) {
                Glide.with(MainActivity204.this)
                        .load(baoCunBean.getTouxiangzhuji()+bean2.getAvatar())
                        .apply(myOptions)
                        .into(touxiang);
            } else {
                if (bean2.getImage()!=null){
                    Glide.with(MainActivity204.this)
                            .load(bean2.getImage())
                            .apply(myOptions)
                            .into(touxiang);
                }

//                Bitmap bitmap = mFacePassHandler.getFaceImage(bean2.getTeZhengMa().getBytes());
//                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
//                Glide.with(MainActivity204.this)
//                        .load(drawable)
//                        .apply(myOptions)
//                        .into(touxiang);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        bottomLl.addView(view_bottom, 0);

        if (bottomLl.getChildCount() > 6) {
            bottomLl.removeViewAt(bottomLl.getChildCount() - 1);
        }


        ViewUpadte.updataViewGroup("LinearLayout",
                toprl, ontWidth,
                0, 10, 0, 10, 0);


        ViewUpadte.updataView("RelativeLayout",
                touxiang, (int) ((float) ontWidth * 0.65f),
                (int) ((float) ontWidth * 0.65f), 0, (int) ((float) ontWidth * 0.2f), 0, 0);

        ViewUpadte.updataView("RelativeLayout",
                name, (int) ((float) ontWidth * 0.85f),
                0, 0, (int) ((float) ontWidth * 0.14f), 0, 0);

        ViewUpadte.updataView("RelativeLayout",
                riqi, (int) ((float) ontWidth * 0.85f),
                0, 0, (int) ((float) ontWidth * 0.04f), 0, 0);



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
                                if (!MainActivity204.this.isFinishing())
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
                                    Log.d("WebsocketPushMsg", e.getMessage());
                                    //e.printStackTrace();
                                }
                         //   }
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
                                if (!MainActivity204.this.isFinishing()) {
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
                                if (!MainActivity204.this.isFinishing()) {
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



    private void play() {
        if (mediaPlayer!=null){
            mediaPlayer.stop();
        }
        if (vlcVout!=null){
            vlcVout.removeCallback(callback);
            vlcVout.detachViews();
        }

        libvlc = new LibVLC(MainActivity204.this);
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
        vlcVout.setVideoView(surfaceView);
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


}
