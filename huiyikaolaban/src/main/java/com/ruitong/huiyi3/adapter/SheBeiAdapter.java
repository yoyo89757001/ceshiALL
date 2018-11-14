package com.ruitong.huiyi3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.beans.IPbean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */


public class SheBeiAdapter extends BaseAdapter {

    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<IPbean> fuWuQiBeanList;


    /*构造函数*/
    public SheBeiAdapter(Context context, List<IPbean> fuWuQiBeanList) {
        this.mInflater = LayoutInflater.from(context);
        this.fuWuQiBeanList=fuWuQiBeanList;
    }

    @Override
    public int getCount() {

        return fuWuQiBeanList.size();//返回数组的长度
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    /*书中详细解释该方法*/
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.shebeiip_list,null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.checkBox =  convertView.findViewById(R.id.checkbox);

            convertView.setTag(holder);//绑定ViewHolder对象
        }
        else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }
            /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        holder.text.setText(fuWuQiBeanList.get(position).getText());
        holder.checkBox.setChecked(fuWuQiBeanList.get(position).isTrue());

        return convertView;
    }
    /*存放控件*/
   private class ViewHolder{
        public CheckBox checkBox;
        public TextView text;

    }
}

