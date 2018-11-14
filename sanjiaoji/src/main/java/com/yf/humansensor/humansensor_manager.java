package com.yf.humansensor;

/**
 * Created by pc on 2018/10/8.
 */
public class humansensor_manager
{
//    static
//    {
//        System.loadLibrary("humansensor_jni");
//    }

    //3.使用
//int fd = humansensor_manager.open();
//红外状态,value判断
//int value = humansensor_manager.get_gpio1_value(fd);
//开灯
// humansensor_manager.set_gpio2_value(fd, 1);
//关灯
 //humansensor_manager.set_gpio2_value(fd, 0);
//开继电器，即闸机开关
 //humansensor_manager.set_gpio3_value(fd, 1);
//关继电器
 //humansensor_manager.set_gpio3_value(fd, 0);

    public static native int get_gpio1_value(int paramInt);

    public static native int get_gpio2_value(int paramInt);

    public static native int get_gpio3_value(int paramInt);

    public static native int get_gpio4_value(int paramInt);

    public static native int get_humansensor_value(int paramInt);

    public static native int get_sensor_value(int paramInt);

    public static native int open();

    public static native void set_gpio1_value(int paramInt1, int paramInt2);

    public static native void set_gpio2_value(int paramInt1, int paramInt2);

    public static native void set_gpio3_value(int paramInt1, int paramInt2);

    public static native void set_gpio4_value(int paramInt1, int paramInt2);

    public static native void set_humansensor_off(int paramInt);

    public static native void set_humansensor_on(int paramInt);

    public static native void set_humansensor_time(int paramInt1, int paramInt2);

    public static native void set_sensor_value(int paramInt1, int paramInt2);
}