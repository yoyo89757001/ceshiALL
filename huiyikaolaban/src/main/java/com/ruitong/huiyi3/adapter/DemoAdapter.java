package com.ruitong.huiyi3.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ruitong.huiyi3.MyApplication;
import com.ruitong.huiyi3.R;
import com.ruitong.huiyi3.beans.BaoCunBean;
import com.ruitong.huiyi3.beans.ShiBieBean;
import com.ruitong.huiyi3.ui.MainActivitykuangshi2;
import com.ruitong.huiyi3.utils.DateUtils;
import com.ruitong.huiyi3.view.GlideCircleTransform;


import java.util.List;

import io.objectbox.Box;


public class DemoAdapter extends RecyclerView.Adapter {
    private BaoCunBean baoCunBean = null;
    private Box<BaoCunBean> baoCunBeanDao = null;
    private Context mContext;
    private List<ShiBieBean.PersonBeanSB> mEntityList;
    private RequestOptions myOptions = new RequestOptions()
            .fitCenter()
            .error(R.drawable.erroy_bg)
            .transform(new GlideCircleTransform(2, Color.parseColor("#ffffff")));


    public DemoAdapter (Context context, List<ShiBieBean.PersonBeanSB> entityList){
        this.mContext = context;
        this.mEntityList = entityList;
        baoCunBeanDao = MyApplication.myApplication.getBaoCunBeanBox();
        baoCunBean = baoCunBeanDao.get(123456L);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itemddd, parent, false);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShiBieBean.PersonBeanSB entity = mEntityList.get(position);
        ((DemoViewHolder)holder).name.setText(entity.getName());
        Glide.with(MyApplication.getAppContext())
                .load(baoCunBean.getTouxiangzhuji()+entity.getAvatar())
                .apply(myOptions)
                .into(((DemoViewHolder)holder).touxiang);
        ((DemoViewHolder)holder).time.setText(DateUtils.timeMinuteSecond(entity.getId()+""));

    }

    @Override
    public int getItemCount() {
        return mEntityList.size();
    }

    private class DemoViewHolder extends RecyclerView.ViewHolder{

        private ImageView touxiang;
        private TextView time;
        private TextView name;

        public DemoViewHolder(View itemView) {
            super(itemView);
            name =  itemView.findViewById(R.id.name);
            time =  itemView.findViewById(R.id.time);
            touxiang =  itemView.findViewById(R.id.touxiang);

        }
    }
}