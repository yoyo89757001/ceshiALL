package com.example.sanjiaoji.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.WindowManager;

import com.example.sanjiaoji.ui.CustomerDisplay;

import java.util.Objects;


/**
 * Created by Administrator on 2018/6/8.
 */

public class CustomerEngine {
    // 获取设备上的屏幕
    private DisplayManager mDisplayManager;// 屏幕管理器
    private Display[] displays;// 屏幕数组
    private CustomerDisplay mCustomerDisplay;   //（继承Presentation）

    private static CustomerEngine instance;

    /**
     * 单例模式，创建的时候把界面绑定到第二个屏幕中
     * @param context 这里需要传入getApplicationContext(),就能实现全局双屏异显
     * @return
     */
    public static CustomerEngine getInstance(Context context,Activity activity,int w,int h){
        if(instance == null){
            instance = new CustomerEngine(context,activity,w,h);
        }
        return instance;
    }

    public static void colose(){
        instance = null;
    }

    private CustomerEngine(Context context, Activity activity,int w,int h){
        mDisplayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        displays = mDisplayManager.getDisplays();
        if (null == mCustomerDisplay && displays.length > 1) {
            //显示的副屏
            mCustomerDisplay =  new CustomerDisplay(context, displays[1],activity,w,h);// displays[1]是副屏
            Objects.requireNonNull(mCustomerDisplay.getWindow()).setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            mCustomerDisplay.show();
        }
    }


}