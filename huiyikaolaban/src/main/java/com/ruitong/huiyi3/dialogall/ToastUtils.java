package com.ruitong.huiyi3.dialogall;

/**
 * Created by 于德海 on 2016/9/8.
 *
 * @version ${VERSION}
 * @decpter
 */
public class ToastUtils {

    private static ToastUtils instances;
    private static CommonDialogListener mListener;

    private ToastUtils(){

    }

    public void setListener(CommonDialogListener listener){
        mListener = listener;
      //  Log.d("ToastUtils2", "mListener:" + mListener);
    }

    public static  ToastUtils getInstances(){
        if (instances == null)
        {
            synchronized (ToastUtils.class)
            {
                if (instances == null)
                {
                    instances = new ToastUtils();
                }
            }
        }
        return instances;
    }

    public void setDate(String a,float p, String t){
        mListener.setDate(a, (int) p,t);
    }


    public void showDialog(String a, String t, int p){
       // Log.d("CommonDialogService", "ddddddddddddd");
        if(mListener!=null){
         //   Log.d("CommonDialogService", "eeeeeeeee");
            mListener.show(a,t,p);
        }
    }

    public void cancel(){
        if(mListener!=null){
            mListener.cancel();
        }
    }
}
