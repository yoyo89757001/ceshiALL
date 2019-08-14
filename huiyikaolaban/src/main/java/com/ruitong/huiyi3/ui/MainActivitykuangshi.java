package com.ruitong.huiyi3.ui;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;


import android.graphics.PixelFormat;
import android.graphics.Typeface;


import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

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
import com.bumptech.glide.request.target.SimpleTarget;

import com.bumptech.glide.request.transition.Transition;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.ruitong.huiyi3.MyApplication;
import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.adapter.MyPagerAdapter;
import com.ruitong.huiyi3.beans.BaoCunBean;
import com.ruitong.huiyi3.beans.BenDiJiLuBean;
import com.ruitong.huiyi3.beans.DaKaBean;
import com.ruitong.huiyi3.beans.GuanHuai;
import com.ruitong.huiyi3.beans.GuanHuai_;
import com.ruitong.huiyi3.beans.LunBoBean;
import com.ruitong.huiyi3.beans.ShiBieBean;
import com.ruitong.huiyi3.beans.SingInSubject;
import com.ruitong.huiyi3.beans.Subject;
import com.ruitong.huiyi3.beans.Subject_;

import com.ruitong.huiyi3.beans.TodayBean;
import com.ruitong.huiyi3.beans.WBBean;
import com.ruitong.huiyi3.beans.WeiShiBieBean;
import com.ruitong.huiyi3.beans.XGBean;
import com.ruitong.huiyi3.beans.XinXiAll;



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



import com.ruitong.huiyi3.view.GlideCircleTransform;
import com.ruitong.huiyi3.view.GlideRoundTransform;


import com.ruitong.huiyi3.view.X5WebView;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import java.util.Timer;
import java.util.TimerTask;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


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


public class MainActivitykuangshi extends AppCompatActivity  {

    private LinearLayout bottomLl;
    private TextView tishi;
    private RelativeLayout top_rl;
    private AutofitTextView riqi, xingqi;
    private AutofitTextView shijian, gongsi;
    private LottieAnimationView wangluo;
    private static WebSocketClient webSocketClient = null;
    private ImageView shezhiTv;
    private Banner banner;
    protected Handler mainHandler;
    private final Timer timer = new Timer();
    private TimerTask task;
    private Box<SingInSubject> singInSubjectBox = MyApplication.myApplication.getSingInSubjectBox();
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
            .transform(new GlideRoundTransform(MainActivitykuangshi.this, 20));
    // private ValueAnimatorUtils utils = null;
    private int bot_ll_width, bot_ll_hight;
    private LinkedBlockingQueue<ShiBieBean.PersonBeanSB> linkedBlockingQueue;
    /* 人脸识别Group */

    private boolean isAnXia = true;
   // private ConcurrentHashMap<Long, Integer> concurrentHashMap = new ConcurrentHashMap<Long, Integer>();
    private static boolean cameraFacingFront = true;
   // private int cameraRotation;
   // private static final int cameraWidth = 1280;
  //  private static final int cameraHeight = 720;
    // private IjkVideoView shipingView;
    private int heightPixels;
    private int widthPixels;
    int screenState = 0;// 0 横 1 竖
    /* 网络请求队列*/
    /*Toast 队列*/
    LinkedBlockingQueue<Toast> mToastBlockQueue;
    // private static boolean isDH = false;
    private static boolean isLink = true;
   // private long tID = -1;
    // private boolean isNet = false;
    /*DetectResult queue*/

    /*recognize thread*/
    private X5WebView webView;
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
    private Box<DaKaBean> daKaBeanBox = null;
    private Box<XinXiAll> xinXiAllBox = null;
    //  private List<GuanHuai> guanHuaiList = new ArrayList<>();
    //   private List<String> bumenString = new ArrayList<>();
    //  private String leixing[] = new String[]{"员工", "普通访客", "白名单", "陌生人"};
   // private Box<XinXiIdBean> xinXiIdBeanBox = null;
    // private List<Subject> subjectList = new ArrayList<>();
   // private Box<LunBoBean> lunBoBeanBox = null;
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
    private SurfaceView surfaceView;
    private LinearLayout linearLayout;
    private static boolean isDH = false;
    private static boolean isShow = true;
    private TextView tvMeetingName;
    private String meetingName;
    private String bgPath = "";
    private String logoPath = "";
    private List<Subject> subjectList = new ArrayList<>();
    private RelativeLayout newRL;
    private ImageView imgLogo;
    private String meetingId;
    private UpBgBroadcastReceiver upBgBroadcastReceiver = new UpBgBroadcastReceiver();
    private List<Subject> singInSubjectList = new ArrayList<>();
    private String zipName;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private ArrayList<Integer> scannedCodes = new ArrayList<Integer>();
    private final static int NO_CONNECT = 10;
    private static final int STATUS = 3;
    private boolean statusFlag = false;
    private boolean states = false;//开始打印标志


    private String printContent = "欢迎**先生/女士参加********会议" +
            "您已签到成功，请凭签到小票到会务组领取会议资料";
    private List<String> quChongList = new ArrayList<>();
    private Configuration newConfig;
    private String tiShiBiaoshi;

    private RecyclerView recyclerView2;
    private GridLayoutManager gridLayoutManager;
    private List<Subject> list = new ArrayList();

    private ShiBieBean.PersonBeanSB subjectli;
    private String time = "";
    private TextView  tvSon;
    private TextView tvSonTitle;
    private Typeface typeFace;
    private Typeface typeFace2;
    private RelativeLayout dialogBg;

    private List<View> viewList = new ArrayList<>();//ViewPager数据源
    private MyPagerAdapter myPagerAdapter;//适配器
    private LinearLayout relativeLayout1;
    private LinearLayout relativeLayout2;
    private LinearLayout relativeLayout3;
    private View viewRemove = null;
    private View addView = null;
    private int cishu = 0;
    private View view_bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        // mImageCache = new FaceImageCache();
        mToastBlockQueue = new LinkedBlockingQueue<>();

        todayBeanBox = MyApplication.myApplication.getTodayBeanBox();
        todayBean = todayBeanBox.get(123456L);
        benDiJiLuBeanBox = MyApplication.myApplication.getBenDiJiLuBeanBox();
        daKaBeanBox = MyApplication.myApplication.getDaKaBeanBox();
        xinXiAllBox = MyApplication.myApplication.getXinXiAllBox();
        //xinXiIdBeanBox = MyApplication.myApplication.getXinXiIdBeanBox();
        baoCunBeanDao = MyApplication.myApplication.getBaoCunBeanBox();
       // lunBoBeanBox = MyApplication.myApplication.getLunBoBeanBox();

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
                sendBroadcast(new Intent(MainActivitykuangshi.this, AlarmReceiver.class));
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
                        final ShiBieBean.PersonBeanSB bean = (ShiBieBean.PersonBeanSB) msg.obj;
                          subjectli = (ShiBieBean.PersonBeanSB) msg.obj;

                        if (relativeLayout1.getChildCount() > 0 ) {
                            addView(relativeLayout1, relativeLayout2, relativeLayout3);
                        }

                        final View view1 = View.inflate(MainActivitykuangshi.this, R.layout.dialog, null);
                        RelativeLayout llll = view1.findViewById(R.id.llllll);
                        TextView name = view1.findViewById(R.id.name);
                        ImageView touxiang = view1.findViewById(R.id.touxiang);
                        TextView bumen = view1.findViewById(R.id.bumen);
                        TextView tvTime = view1.findViewById(R.id.tv_time);

                        name.setTypeface(typeFace);
                        bumen.setTypeface(typeFace);
                        tvTime.setTypeface(typeFace);
                        touxiang.setBackground(getResources().getDrawable(R.drawable.yinying));

                        name.setText(bean.getName() + "");
                        bumen.setText(bean.getDepartment() + "");
                        tvTime.setText(""+ bean.getRemark());
                        time = DateUtils.timeMinuteSecond(System.currentTimeMillis() + "");

                        try {
                            if (bean.getAvatar() != null) {

                                Glide.with(MainActivitykuangshi.this)
                                        .load(baoCunBean.getTouxiangzhuji()+bean.getAvatar())
                                        .apply(myOptions)
                                        .into(touxiang);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (bean.getSubject_type()==-1) {
                            //-1陌生人
                            name.setVisibility(View.GONE);
                            tvTime.setVisibility(View.GONE);
                            llll.setBackgroundResource(R.drawable.new_dialog);
                        } else {
                            llll.setBackgroundResource(R.drawable.newbg11);
                        }
                        view1.setY(dh);
                        if (relativeLayout1.getChildCount() == 0) {
                            relativeLayout1.addView(view1, 0);
                            Log.d("addView", "view1:addView");
                        }

                        //竖屏
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) touxiang.getLayoutParams();
                        layoutParams.width = (int) (dw * 0.289f);
                        layoutParams.height = (int) (dw * 0.289f);
                        layoutParams.topMargin = (int) (dw * 0.15f);
                        touxiang.setLayoutParams(layoutParams);
                        touxiang.invalidate();

                        RelativeLayout.LayoutParams naeee = (RelativeLayout.LayoutParams) name.getLayoutParams();
//                            naeee.width = (int) (dw * 0.275f);
//                            naeee.leftMargin = (int) (dw * 0.13f);
                        naeee.topMargin = (int) (dh * 0.01f);
                        name.setLayoutParams(naeee);
                        name.invalidate();

                        RelativeLayout.LayoutParams bumenParams = (RelativeLayout.LayoutParams) bumen.getLayoutParams();
                        if (bean.getSubject_type()==-1) {
                            bumenParams.topMargin = (int) (dh * 0.05f);
//                                bumenParams.leftMargin = (int) (dw * 0.05f);
                            bumen.setLayoutParams(bumenParams);
                            bumen.invalidate();
                        } else {
                            bumen.setLayoutParams(bumenParams);
                            bumen.invalidate();
                        }


                        RelativeLayout.LayoutParams timeParams = (RelativeLayout.LayoutParams) tvTime.getLayoutParams();
                        timeParams.topMargin = (int) (dh * 0.37f);
                        tvTime.setLayoutParams(timeParams);
                        tvTime.invalidate();


                        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) llll.getLayoutParams();
                        layoutParams1.height = (int) (dh * 0.4f);
                        layoutParams1.width = (int) (dw * 0.5f);
//                            layoutParams1.width = (int) (dw * 0.25f);
                        llll.setLayoutParams(layoutParams1);
                        llll.invalidate();


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

//                        入场动画(从右往左)
                        ValueAnimator anim = ValueAnimator.ofInt(dh, (int) (dh * 0.02f));
                        anim.setDuration(400);
                        anim.setRepeatMode(ValueAnimator.RESTART);
                        Interpolator interpolator = new DecelerateInterpolator(2f);
                        anim.setInterpolator(interpolator);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                int currentValue = (Integer) animation.getAnimatedValue();
                                view1.setY(currentValue);
                                view1.requestLayout();
                            }
                        });
                        anim.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                //tvSon.setText(singInSubjectBox.count() + "");
                                if (dialogBg.getVisibility() == View.GONE)
                                    dialogBg.setVisibility(View.VISIBLE);


                                setBootonView(subjectli);

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {
                            }
                        });
                        anim.start();


                        break;
                    }

                    case 222: {


                        if (!isDH) {
                            if (relativeLayout3.getChildCount() > 0) {
                                viewRemove = relativeLayout3.getChildAt(relativeLayout3.getChildCount() - 1);
                            } else {
                                if (relativeLayout2.getChildCount() > 0) {
                                    viewRemove = relativeLayout2.getChildAt(relativeLayout2.getChildCount() - 1);
                                } else {
                                    if (relativeLayout1.getChildCount() > 0) {
                                        viewRemove = relativeLayout1.getChildAt(relativeLayout1.getChildCount() - 1);
                                    }
                                }
                            }
                            if (viewRemove != null) {
                                List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
                                final ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(viewRemove, "scaleX", 0.78f, 0f);
                                alphaAnim.setDuration(200);//设置持续时间
                                ObjectAnimator alphaAnim2 = ObjectAnimator.ofFloat(viewRemove, "scaleY", 0.75f, 0f);
                                alphaAnim2.setDuration(200);//设置持续时间
                                ObjectAnimator alpha = ObjectAnimator.ofFloat(viewRemove, "alpha", 1.0f, 0.0f);
                                alpha.setDuration(200);//设置持续时间
                                alphaAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        //底部列表的

                                    }
                                });
                                alphaAnim2.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                       // isDH = true;
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                       // isDH = false;
//                                            linearLayout.removeView(viewRemove);
                                        if (relativeLayout3.getChildCount() > 0) {
                                            viewRemove = relativeLayout3.getChildAt(relativeLayout3.getChildCount() - 1);
                                            if (viewRemove != null)
                                                relativeLayout3.removeView(viewRemove);
                                        } else {
                                            if (relativeLayout2.getChildCount() > 0) {
                                                viewRemove = relativeLayout2.getChildAt(relativeLayout2.getChildCount() - 1);
                                                if (viewRemove != null)
                                                    relativeLayout2.removeView(viewRemove);
                                            } else {
                                                if (relativeLayout1.getChildCount() > 0) {
                                                    viewRemove = relativeLayout1.getChildAt(relativeLayout1.getChildCount() - 1);
                                                    if (viewRemove != null)
                                                        relativeLayout1.removeView(viewRemove);
                                                }
                                            }
                                        }
                               //         isShow = true;
                                        if (relativeLayout1.getChildCount() > 0 || relativeLayout2.getChildCount() > 0 || relativeLayout2.getChildCount() > 0) {
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
                                        }

                                        boolean isChild = relativeLayout1.getChildCount() == 0 && relativeLayout2.getChildCount() == 0 && relativeLayout3.getChildCount() == 0;
                                        if (isChild && dialogBg.getVisibility() == View.VISIBLE) {
                                            dialogBg.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }

                                });
                                animators.add(alphaAnim);
                                animators.add(alphaAnim2);
                                animators.add(alpha);
                                AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
                                btnSexAnimatorSet.playTogether(animators);//设置一起播放
                                btnSexAnimatorSet.start();//开始播放
                            }

                        }

                        break;

                    }

//

                    case -100: {

                      //  dabg.setVisibility(View.VISIBLE);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.get(MainActivitykuangshi.this).clearDiskCache();
                            }
                        }).start();
                        //陌生人
                        final ShiBieBean.PersonBeanSB bean2 = (ShiBieBean.PersonBeanSB) msg.obj;

                        final View view_dk = View.inflate(MainActivitykuangshi.this, R.layout.moshengren_item_jj, null);
                        TextView name2 = (TextView) view_dk.findViewById(R.id.name);
                        ImageView touxiang2 = (ImageView) view_dk.findViewById(R.id.touxiang);
                        TextView xingbie = (TextView) view_dk.findViewById(R.id.xingbie);
                        TextView nianling = (TextView) view_dk.findViewById(R.id.nianling);
                        name2.setText(bean2.getName());
                       // xingbie.setText("性别:" + bean2.getSex());
                       // nianling.setText("年龄:" + bean2.getAge());

                        Glide.get(MainActivitykuangshi.this).clearMemory();
                        Glide.with(MainActivitykuangshi.this)
                                .load(bean2.getImage())
                                .apply(myOptions2)
                                .into(touxiang2);

                        view_dk.setTag(bean2.getId()+"");
                      //  hengLiebiao.addView(view_dk);

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


                                    final View view_dk = View.inflate(MainActivitykuangshi.this, R.layout.shulianbiao_jj, null);
                                    TextView name = view_dk.findViewById(R.id.name);
                                    ImageView touxiang = view_dk.findViewById(R.id.touxiang);
                                    name.setText("陌生人");

                                    Glide.with(MainActivitykuangshi.this)
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

        cishu = baoCunBean.getSize1();
        tvSon.setText(cishu+"");
        Log.d("MainActivitykuangshi", "cishu:" + cishu);

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
                                        Glide.get(MainActivitykuangshi.this).clearDiskCache();
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

    @Override
    protected void onPause() {

//        synchronized (MainActivitykuangshi.this) {
//
//        }
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

        setContentView(R.layout.new_mainactivity4_layout);
        //  ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        AssetManager mgr = getAssets();
        surfaceView = findViewById(R.id.preview);
        //Univers LT 57 Condensed
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Univers LT 57 Condensed.ttf");
        Typeface tf2 = Typeface.createFromAsset(mgr, "fonts/hua.ttf");
        Typeface tf3 = Typeface.createFromAsset(mgr, "fonts/kai.ttf");
        // String riqi2 = DateUtils.timesTwo(System.currentTimeMillis() + "") + "   " + DateUtils.getWeek(System.currentTimeMillis());
        //  riqi.setTypeface(tf);
        //    riqi.setText(riqi2);


       // xiaoshi.setTypeface(tf);
        //xiaoshi.setText(DateUtils.timeMinute(System.currentTimeMillis() + ""));
        shezhiTv=findViewById(R.id.shezhi_tv);
        shezhiTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivitykuangshi.this,SheZhiActivity.class));
               finish();
            }
        });
        //webView = findViewById(R.id.web5);
        dialogBg = findViewById(R.id.dialog_bg);
        linearLayout = findViewById(R.id.linearLayout);
        top_rl = findViewById(R.id.toprl);
        shijian = findViewById(R.id.shijian);
        riqi = findViewById(R.id.riqi);
        xingqi = findViewById(R.id.xingqi);
        gongsi = findViewById(R.id.gongsi);
        tishi = findViewById(R.id.tishi);
        tvMeetingName = findViewById(R.id.tv_meeting_name);
        bottomLl = findViewById(R.id.bottomsss);
        //tvSum = findViewById(R.id.tv_sum);
        tvSon = findViewById(R.id.tv_son);
        tvSonTitle = findViewById(R.id.tv_son_title);
       // tvSumTitle = findViewById(R.id.tv_sum_title);
        relativeLayout1 = findViewById(R.id.relative1);
        relativeLayout2 = findViewById(R.id.relative2);
        relativeLayout3 = findViewById(R.id.relative3);

        typeFace = Typeface.createFromAsset(getAssets(), "fonts/pingfangxxxxxh.ttf");
        typeFace2 = Typeface.createFromAsset(getAssets(), "fonts/pingfangxxxh.ttf");
        riqi.setTypeface(typeFace2);
        shijian.setTypeface(typeFace2);
        xingqi.setTypeface(typeFace2);
        tvMeetingName.setTypeface(typeFace);
      //  tvSum.setTypeface(typeFace2);
        tvSon.setTypeface(typeFace2);
        tvSonTitle.setTypeface(typeFace2);
      //  tvSumTitle.setTypeface(typeFace2);

        //tvSum.setText(subjectBox.count() + "");
       // tvSon.setText(singInSubjectBox.count() + "");

        tvMeetingName.setText(meetingName);
        xingqi.setText(DateUtils.getWeek(System.currentTimeMillis()));
        riqi.setText(DateUtils.timesTwodian(System.currentTimeMillis() + ""));
//        recyclerView2 = findViewById(R.id.recyclerView2);

        tvMeetingName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity4.this, singInSubjectBox.getAll().size() + "", Toast.LENGTH_LONG).show();
//                singInSubjectBox.removeAll();
//                Toast.makeText(MainActivity4.this, singInSubjectBox.getAll().size() + "", Toast.LENGTH_LONG).show();
             //   Log.d("CameraWH", "height():" + manager.getCameraheight() + "    Width():" + manager.getCameraWidth());
            }
        });

        if (baoCunBean.getWenzi1() != null)
            gongsi.setText(baoCunBean.getWenzi1());

        riqi.setText(DateUtils.timesThree(System.currentTimeMillis() + ""));
        shijian.setText(DateUtils.timeMinute(System.currentTimeMillis() + ""));
        xingqi.setText(DateUtils.getWeek(System.currentTimeMillis()));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        heightPixels = displayMetrics.heightPixels;
        widthPixels = displayMetrics.widthPixels;


        if (baoCunBean.getXiaBanTime() != null) {
            logo.setImageBitmap(BitmapFactory.decodeFile(baoCunBean.getXiaBanTime()));
        }



    }


    public void oncv(View view){
      //  Log.d("MainActivity102", "房贷首付");
        startActivity(new Intent(MainActivitykuangshi.this,SheZhiActivity.class));
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
        Toast.makeText(MainActivitykuangshi.this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                startActivity(new Intent(MainActivitykuangshi.this, SheZhiActivity.class));
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
                startActivity(new Intent(MainActivitykuangshi.this, SheZhiActivity.class));
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

               tsxxChuLi.setData(xgBean, MainActivitykuangshi.this);
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



        Toast tastyToast = TastyToast.makeText(MainActivitykuangshi.this, event, TastyToast.LENGTH_LONG, TastyToast.INFO);
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
                    if (xiaoshiss.split(":")[0].equals("07") && xiaoshiss.split(":")[1].equals("30")) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                               daKaBeanBox.removeAll();
                               baoCunBean.setSize1(0);
                               cishu=0;
                               baoCunBeanDao.put(baoCunBean);

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



                    //xiaoshi.setText(xiaoshiss);
                    xingqi.setText(DateUtils.getWeek(System.currentTimeMillis()));
                    shijian.setText(DateUtils.timeMinute(System.currentTimeMillis() + ""));
                    riqi.setText(DateUtils.timesTwodian(System.currentTimeMillis() + ""));
                    //  xingqi2.setText(DateUtils.getWeek(System.currentTimeMillis() + 86400000L));
                    //  xingqi3.setText(DateUtils.getWeek(System.currentTimeMillis() + 86400000L + 86400000L))
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
//                                Toast tastyToast = TastyToast.makeText(MainActivitykuangshi.this, "获取天气失败,没有设置当前城市", TastyToast.LENGTH_LONG, TastyToast.INFO);
//                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
//                                tastyToast.show();
                                return;
                            }
                            //  Log.d("TimeChangeReceiver", baoCunBean.getDangqianChengShi());


                            xingqi.setText(DateUtils.getWeek(System.currentTimeMillis()));
                            riqi.setText(DateUtils.timesTwodian(System.currentTimeMillis() + ""));



                        } catch (Exception e) {
                            e.printStackTrace();
//                            Toast tastyToast = TastyToast.makeText(MainActivitykuangshi.this, "获取天气失败,没有设置当前城市", TastyToast.LENGTH_LONG, TastyToast.INFO);
//                            tastyToast.setGravity(Gravity.CENTER, 0, 0);
//                            tastyToast.show();
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


    //添加下部头像
    private void setBootonView(ShiBieBean.PersonBeanSB bean2) {

        if (quChongList.size() > 4) {
            quChongList.remove(0);
        }
        if (quChongList.contains(bean2.getId() + "")) {
            if (bottomLl != null) {
                TextView timetv = bottomLl.findViewWithTag(bean2.getId() + "").findViewById(R.id.tv_time);
                timetv.setText(time);
            }
            return;
        }
        quChongList.add(bean2.getId() + "");

        view_bottom = View.inflate(MainActivitykuangshi.this, R.layout.item_face, null);
        view_bottom.setTag(bean2.getId() + "");
        ImageView touxiang = view_bottom.findViewById(R.id.himg_face);
        ImageView imgbg = view_bottom.findViewById(R.id.imgbg);
        ImageView textbg = view_bottom.findViewById(R.id.textbg);
        TextView name = view_bottom.findViewById(R.id.tv_name);
        TextView bumen = view_bottom.findViewById(R.id.tv_bumen);
        TextView timetv = view_bottom.findViewById(R.id.tv_time);
        RelativeLayout itemrl = view_bottom.findViewById(R.id.itemrl);

        name.setText(bean2.getName());
        bumen.setText(bean2.getDepartment());
        timetv.setText(time);

        name.setTypeface(typeFace);
        bumen.setTypeface(typeFace2);
        timetv.setTypeface(typeFace2);

        try {
            if (bean2.getAvatar() != null) {
                Glide.with(MainActivitykuangshi.this)
                        .load(baoCunBean.getTouxiangzhuji()+bean2.getAvatar())
                        .apply(myOptions)
                        .into(touxiang);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        //竖屏
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) touxiang.getLayoutParams();
        layoutParams.width = (int) (dw * 0.22f);
        layoutParams.height = (int) (dw * 0.22f);
        layoutParams.topMargin = (int) (dw * 0.039f);
        layoutParams.rightMargin = (int) (dw * 0.023f);
        touxiang.setLayoutParams(layoutParams);
        touxiang.invalidate();

        RelativeLayout.LayoutParams naeee = (RelativeLayout.LayoutParams) name.getLayoutParams();
        naeee.width = (int) (dw * 0.275f);
        naeee.leftMargin = (int) (dw * 0.2f);
        naeee.topMargin = (int) (dh * 0.062f);
        name.setLayoutParams(naeee);
        name.invalidate();

        RelativeLayout.LayoutParams bumenParams = (RelativeLayout.LayoutParams) bumen.getLayoutParams();
        bumenParams.width = (int) (dw * 0.66f);
        bumenParams.leftMargin = (int) (dw * 0.01f);
        bumenParams.topMargin = (int) (dh * 0.018f);
        bumen.setLayoutParams(bumenParams);
        bumen.invalidate();

        RelativeLayout.LayoutParams timeParams = (RelativeLayout.LayoutParams) timetv.getLayoutParams();
        timeParams.topMargin = (int) (dh * 0.028f);
        timeParams.leftMargin = (int) (dw * 0.027f);
        timetv.setLayoutParams(timeParams);
        timetv.invalidate();

        RelativeLayout.LayoutParams imgbgParams1 = (RelativeLayout.LayoutParams) imgbg.getLayoutParams();
        imgbgParams1.width = (int) (dw * 0.275f);
        imgbgParams1.height = (int) (dw * 0.26f);
        imgbgParams1.topMargin = (int) (dw * 0.02f);
        imgbg.setLayoutParams(imgbgParams1);
        imgbg.invalidate();

        RelativeLayout.LayoutParams textbgParams1 = (RelativeLayout.LayoutParams) textbg.getLayoutParams();
        textbgParams1.height = (int) (dh * 0.13f);
        textbgParams1.width = (int) (dw * 0.83f);
        textbgParams1.topMargin = (int) (dw * 0.036f);
        textbg.setLayoutParams(textbgParams1);
        textbg.invalidate();

        bottomLl.addView(view_bottom, 0);


        if (bottomLl.getChildCount() > 4) {
            bottomLl.removeViewAt(bottomLl.getChildCount() - 1);
        }
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
                                if (!MainActivitykuangshi.this.isFinishing())
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

                            if (daKaBeanBox.get(dataBean.getPerson().getId())==null){

                                DaKaBean bean= new DaKaBean();
                                bean.setId(dataBean.getPerson().getId());
                                daKaBeanBox.put(bean);
                                cishu+=1;
                                baoCunBean.setSize1(cishu);
                                baoCunBeanDao.put(baoCunBean);
                                Log.d("WebsocketPushMsg", "cishu:" + cishu);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvSon.setText(cishu+"");
                                    }
                                });
                            }



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
                                if (!MainActivitykuangshi.this.isFinishing()) {
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
                                if (!MainActivitykuangshi.this.isFinishing()) {
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
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    boolean aa = true;
//
//                    while (aa){
//                        if (TbsVideo.canUseTbsPlayer(MainActivitykuangshi.this)){
//                            aa=false;
//                            Log.d("MainActivitykuangshi", "准备好了");
//                            if (baoCunBean.getShipingIP() != null) {
//                                Log.d("dddddd", baoCunBean.getShipingIP() + "gggg");
//                                TbsVideo.openVideo(MainActivitykuangshi.this,baoCunBean.getShipingIP());
//                            }
//
//                        }
//                        SystemClock.sleep(500);
//                        Log.d("MainActivitykuangshi", "adsdasd");
//                    }
//
//
//                }
//            }).start();



        if (mediaPlayer!=null){
            mediaPlayer.stop();
        }
        if (vlcVout!=null){
            vlcVout.removeCallback(callback);
            vlcVout.detachViews();
        }


        ArrayList<String> options = new ArrayList<>();
        options.add(":file-caching=300");
        options.add(":network-caching=300");


        libvlc = new LibVLC(MainActivitykuangshi.this,options);
        mediaPlayer = new MediaPlayer(libvlc);
        vlcVout = mediaPlayer.getVLCVout();

        callback = new IVLCVout.Callback() {

            @Override
            public void onNewLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {

            }

            @Override
            public void onSurfacesCreated(IVLCVout ivlcVout) {
                try {
                    Log.d("MainActivitykuangshi", "surfaceView.getWidth():" + surfaceView.getWidth());
                    Log.d("MainActivitykuangshi", "surfaceView.getHeight():" + surfaceView.getHeight());
                    vlcVout.setWindowSize(surfaceView.getWidth(),surfaceView.getHeight());
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


    }

    class UpBgBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            bgPath = intent.getStringExtra("bgPath");
            logoPath = intent.getStringExtra("logoPath");

            SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    newRL.setBackground(resource);
                }
            };
            Glide.with(context).load(bgPath).into(simpleTarget);
            Glide.with(context).load(logoPath).into(imgLogo);

        }
    }

    public void addView(LinearLayout relativeLayout1, LinearLayout relativeLayout2, LinearLayout relativeLayout3) {
        if (relativeLayout1.getChildCount() != 0) {
            addView = relativeLayout1.getChildAt(relativeLayout1.getChildCount() - 1);
            relativeLayout1.removeAllViews();
            if (relativeLayout2.getChildCount() == 0) {
                addLeftAnimator(addView);
                relativeLayout2.addView(addView, 0);
            } else {
                if (relativeLayout3.getChildCount() == 0 && relativeLayout2.getChildCount() != 0) {
                    addRightAnimator(addView);
                    relativeLayout3.addView(addView);
                } else {
                    View addView2 = relativeLayout2.getChildAt(relativeLayout2.getChildCount() - 1);
                    relativeLayout2.removeAllViews();
                    addLeftAnimator(addView);
                    relativeLayout2.addView(addView);
                    relativeLayout3.removeAllViews();
                    addRightAnimator(addView2);
                    relativeLayout3.addView(addView2);
                }
            }
        }
    }

    public void addLeftAnimator(View view) {
        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.72f);
        scaleX.setDuration(200);//设置持续时间
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.72f);
        scaleY.setDuration(200);//设置持续时间
        ObjectAnimator alpha = ObjectAnimator.ofFloat(viewRemove, "alpha", 1.0f, 0.6f);
        alpha.setDuration(400);//设置持续时间
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", dw * -0.18f, -dw * 0.25f);
        translationX.setDuration(400);//设置持续时间
        translationX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //底部列表的

            }
        });
        translationX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isDH = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isDH = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        animators.add(scaleX);
        animators.add(scaleY);
        animators.add(translationX);
        animators.add(alpha);
        AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
        btnSexAnimatorSet.playTogether(animators);//设置一起播放
        btnSexAnimatorSet.start();//开始播放

    }

    public void addRightAnimator(View view) {
        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.7f);
        scaleX.setDuration(200);//设置持续时间
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.7f);
        scaleY.setDuration(200);//设置持续时间
        ObjectAnimator alpha = ObjectAnimator.ofFloat(viewRemove, "alpha", 1.0f, 0.6f);
        alpha.setDuration(400);//设置持续时间
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", -dw * 0.05f, dw * 0.0005f);
        translationX.setDuration(400);//设置持续时间
        translationX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //底部列表的

            }
        });
        translationX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isDH = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isDH = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        animators.add(scaleX);
        animators.add(scaleY);
        animators.add(translationX);
        animators.add(alpha);
        AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
        btnSexAnimatorSet.playTogether(animators);//设置一起播放
        btnSexAnimatorSet.start();//开始播放
    }


}
