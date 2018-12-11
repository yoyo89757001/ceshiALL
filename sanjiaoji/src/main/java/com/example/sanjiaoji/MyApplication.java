package com.example.sanjiaoji;



import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.example.sanjiaoji.model.BaoCunBean;
import com.example.sanjiaoji.model.MyObjectBox;
import com.tencent.bugly.Bugly;

import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.io.File;


import io.objectbox.Box;
import io.objectbox.BoxStore;
import megvii.facepass.FacePassHandler;
import okhttp3.MediaType;



/**
 * Created by tangjun on 14-8-24.
 */
public class MyApplication extends MultiDexApplication {
	public FacePassHandler facePassHandler=null;
	public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
	private final static String TAG = "CookiesManager";
	public static MyApplication myApplication;
	private static BoxStore mBoxStore;

	// 超时时间
	public static final int TIMEOUT = 1000 * 60;

	static
	{
		System.loadLibrary("humansensor_jni");
	}


	@Override
	public void onCreate() {
		super.onCreate();

		try {
			ScreenAdapterTools.init(this);
			mBoxStore = MyObjectBox.builder().androidContext(this).build();

		Bugly.init(getApplicationContext(), "4536442096", false);

		} catch (Exception e) {
			Log.d(TAG, e.getMessage()+"主程序");
		}
			myApplication = this;

		String path= Environment.getExternalStorageDirectory()+ File.separator+"sanjiaoji";
		File destDir = new File(path);

		if (!destDir.exists()) {
			destDir.mkdirs();
		}

		Box<BaoCunBean> baoCunBeanBox=mBoxStore.boxFor(BaoCunBean.class);
		BaoCunBean baoCunBean=baoCunBeanBox.get(123456L);
		if (baoCunBean==null){
			BaoCunBean bao=new BaoCunBean();
			bao.setId(123456L);
			bao.setHoutaiDiZhi("http://192.168.2.78");
			bao.setZhanghuId("test@ruitong.com");
			bao.setGuanggaojiMing("123456");
			baoCunBeanBox.put(bao);
			Log.d(TAG, "新增保存");
		}

		if (baoCunBean!=null)
		Log.d(TAG, baoCunBean.toString());
	}

	public BoxStore getBoxStore(){
		return mBoxStore;
	}
	public FacePassHandler getFacePassHandler() {

		return facePassHandler;
	}

	public void setFacePassHandler(FacePassHandler facePassHandler1){
		facePassHandler=facePassHandler1;
	}

	//旋转适配,如果应用屏幕固定了某个方向不旋转的话(比如qq和微信),下面可不写.
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		ScreenAdapterTools.getInstance().reset(this);
	}

	/**
	 * @param path
	 * @return
	 */
	public static Bitmap decodeImage(String path) {
		Bitmap res;
		try {
			ExifInterface exif = new ExifInterface(path);
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

			BitmapFactory.Options op = new BitmapFactory.Options();
			op.inSampleSize = 1;
			op.inJustDecodeBounds = false;
			//op.inMutable = true;
			res = BitmapFactory.decodeFile(path, op);
			//rotate and scale.
			Matrix matrix = new Matrix();

			if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
				matrix.postRotate(90);
			} else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
				matrix.postRotate(180);
			} else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
				matrix.postRotate(270);
			}

			Bitmap temp = Bitmap.createBitmap(res, 0, 0, res.getWidth(), res.getHeight(), matrix, true);
			Log.d("com.arcsoft", "check target Image:" + temp.getWidth() + "X" + temp.getHeight());

			if (!temp.equals(res)) {
				res.recycle();
			}
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}




	public static MyApplication getAppContext() {
		return myApplication;
	}



	public File getDiskCacheDir(String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = MyApplication.getAppContext().getExternalCacheDir().getPath();
		} else {
			cachePath = MyApplication.getAppContext().getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}

	public int getAppVersion() {
		try {
			PackageInfo info = MyApplication.getAppContext().getPackageManager().getPackageInfo(MyApplication.getAppContext().getPackageName(), 0);
			return info.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}




}
