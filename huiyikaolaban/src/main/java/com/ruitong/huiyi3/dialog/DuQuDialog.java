package com.ruitong.huiyi3.dialog;
import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ruitong.huiyi3.R;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class DuQuDialog extends Dialog {
    private TextView a1,tishi;
    private ProgressBar progressBar;
    private Button guanbi;

    public DuQuDialog(Context context) {
        super(context, R.style.dialog_style);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.queren_ll112, null);
        progressBar= (ProgressBar) mView.findViewById(R.id.progressBar);
        a1= (TextView) mView.findViewById(R.id.a1);
        guanbi= (Button) mView.findViewById(R.id.guanbi);
        tishi= (TextView) mView.findViewById(R.id.tishi);
        tishi.setMovementMethod(ScrollingMovementMethod.getInstance());
        //获得当前窗体
        Window window = DuQuDialog.this.getWindow();
        //重新设置
        WindowManager.LayoutParams lp = DuQuDialog.this.getWindow().getAttributes();
        window .setGravity(Gravity.CENTER );
        // lp.x = 100; // 新位置X坐标
//        lp.y = 100; // 新位置Y坐标
        lp.width = 450; // 宽度
        lp.height = 400; // 高度
        //   lp.alpha = 0.7f; // 透明度
        // dialog.onWindowAttributesChanged(lp);
        //(当Window的Attributes改变时系统会调用此函数)
        window .setAttributes(lp);
        progressBar.setMax(100);
        super.setContentView(mView);


    }

    public void setProgressBar(float p){
        progressBar.setProgress((int) p);
        tishi.setVisibility(View.GONE);
        a1.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        a1.setText("入库中..."+ p +"%");
    }


    public void setTiShi(String s){

        tishi.setVisibility(View.VISIBLE);
        a1.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        tishi.setText(s);
    }

    public void setClose(){

     guanbi.setVisibility(View.VISIBLE);

    }

    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener){
        guanbi.setOnClickListener(listener);
    }
//    /**
//     * 取消键监听器
//     * @param listener
//     */
//    public void setOnQuXiaoListener(View.OnClickListener listener){
//        quxiao.setOnClickListener(listener);
//    }
}
