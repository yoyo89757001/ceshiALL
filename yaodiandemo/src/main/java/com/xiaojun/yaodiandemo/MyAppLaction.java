package com.xiaojun.yaodiandemo;


import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


import com.tencent.bugly.Bugly;
import com.xiaojun.yaodiandemo.beans.BaoCunBean;


import com.xiaojun.yaodiandemo.beans.FaceDB;
import com.xiaojun.yaodiandemo.beans.MyObjectBox;


import java.io.File;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import megvii.facepass.FacePassHandler;


/**
 * Created by Administrator on 2017/7/5.
 */

public class MyAppLaction extends MultiDexApplication {
    private File mCascadeFile;
 //   public static FaceDet mFaceDet;
   // public static String sip=null;
  //  public static LibVLC libvlc;
   // public static JiuDianBean jiuDianBean=null;
   // public static String zhuji=null;
  public FacePassHandler facePassHandler=null;



    public static MyAppLaction myAppLaction;
    public FaceDB mFaceDB;
    public static Long ShenfenzhengId=0L;
    private static BoxStore mBoxStore;
    private Box<BaoCunBean> baoCunBeanDao;
    private BaoCunBean baoCunBean;

    @Override
    public void onCreate() {
        super.onCreate();

        myAppLaction=this;
        mBoxStore = MyObjectBox.builder().androidContext(this).build();


        try {
            Bugly.init(getApplicationContext(), "a464b976b000", false);
            mFaceDB = new FaceDB(this.getExternalCacheDir().getPath());


            baoCunBeanDao = mBoxStore.boxFor(BaoCunBean.class);

            baoCunBean = baoCunBeanDao.get(123456L);
            if (baoCunBean == null) {
                baoCunBean = new BaoCunBean();
                baoCunBean.setId(123456L);
                baoCunBean.setHoutaiDiZhi("http://hy.inteyeligence.com/front");
                baoCunBean.setShibieFaceSize(20);
                baoCunBean.setShibieFaZhi(70);
                baoCunBean.setYudiao(5);
                baoCunBean.setYusu(5);
                baoCunBean.setBoyingren(4);
                baoCunBean.setRuKuFaceSize(38);
                baoCunBean.setRuKuMoHuDu(0.3f);
                baoCunBean.setHuoTiFZ(70);
                baoCunBean.setHuoTi(false);
                baoCunBean.setDangqianShiJian("d");
                baoCunBean.setTianQi(false);
                if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    //竖屏
                    Log.d("SheZhiActivity", "竖屏");
                    baoCunBean.setHengOrShu(false);
                } else {
                    //横屏
                    Log.d("SheZhiActivity", "横屏");
                    baoCunBean.setHengOrShu(true);
                }
                if (baoCunBean.isHengOrShu()){
                    //true就是横
                    baoCunBean.setMoban(101);
                }else {
                    //竖屏
                    baoCunBean.setMoban(201);
                }
                baoCunBeanDao.put(baoCunBean);


            }


        //    mFaceDet = new FaceDet(Constants.getFaceShapeModelPath());

        //    Reservoir.init(this, 900 * 1024); //in bytes 1M

//            //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
//            QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//                @Override
//                public void onViewInitFinished(boolean arg0) {
//
//                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                    Log.d("app", " onViewInitFinished is " + arg0);
//                }
//
//                @Override
//                public void onCoreInitFinished() {
//                    QbSdk.reset(getApplicationContext());
//                }
//            };
//            //x5内核初始化接口
//            QbSdk.initX5Environment(getApplicationContext(), cb);
        } catch (Exception e) {
            Log.d("gggg", e.getMessage());

        }

     //   libvlc = LibVLCUtil.getLibVLC(getApplicationContext());

    }


    public BoxStore getBoxStore(){
        return mBoxStore;
    }
    /**
     * 设置greenDao
     */



//        Log.d("MainActivity", "OpenCVLoader.initDebug():" + OpenCVLoader.initDebug());
        // Example of a call to a native method
//        try {
//            // load cascade file from application resources
//            InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
//            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
//            mCascadeFile = new File(cascadeDir, "haarcascade_frontalface_alt2.xml");
//            FileOutputStream os = new FileOutputStream(mCascadeFile);
//
//            byte[] buffer = new byte[4096];
//            int bytesRead;
//            while ((bytesRead = is.read(buffer)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//            is.close();
//            os.close();
//
//            mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
//            if (mJavaDetector.empty()) {
//
//                mJavaDetector = null;
//            }
//            cascadeDir.delete();
//
//
//        } catch (IOException e) {
//            Log.d("InFoActivity2", e.getMessage());
//        }


    }

