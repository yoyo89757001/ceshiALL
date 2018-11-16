package com.example.sanjiaoji.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;

import com.example.sanjiaoji.R;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class XiuGaiDiZhiDialog extends Dialog {
   // private TextView title2;
    private Button l1,l2;
    private EditText idid,zhanghao,mima;
    public XiuGaiDiZhiDialog(Context context) {
        super(context, R.style.dialog_style2);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.xiugaidialog_dizhi, null);

        zhanghao= (EditText) mView.findViewById(R.id.zhanghao);
        mima= (EditText) mView.findViewById(R.id.mima);
        idid= (EditText)mView.findViewById(R.id.idid);
       // title2= (TextView) mView.findViewById(R.id.title2);
        l1= (Button)mView. findViewById(R.id.queren);
        l2= (Button) mView.findViewById(R.id.quxiao);

        super.setContentView(mView);
    }

    public void setContents(String ss, String s3,String s4){
       if (ss!=null)
           idid.setText(ss);
       if (s3!=null)
           zhanghao.setText(s3);
       if (s4!=null)
           mima.setText(s4);

    }

    public String getUrl(){
        return idid.getText().toString().trim();
    }

    public String getZhangHao(){
        return zhanghao.getText().toString().trim();
    }
    public String getMiMa(){
        return mima.getText().toString().trim();
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
    public void setOnQueRenListener(View.OnClickListener listener){
        l1.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setQuXiaoListener(View.OnClickListener listener){
        l2.setOnClickListener(listener);
    }


}