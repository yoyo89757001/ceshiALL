package com.ruitong.huiyi3.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/5/29.
 */
@Entity
public class MBLeiXingBean {
    @Id
    @NotNull
    private Long id;
    private String mb_Name;
    private int type;
    private String shenfen;
    private String huanyinyu;
    private String bg_path;
    @Generated(hash = 564736954)
    public MBLeiXingBean(@NotNull Long id, String mb_Name, int type, String shenfen,
            String huanyinyu, String bg_path) {
        this.id = id;
        this.mb_Name = mb_Name;
        this.type = type;
        this.shenfen = shenfen;
        this.huanyinyu = huanyinyu;
        this.bg_path = bg_path;
    }
    @Generated(hash = 1223025098)
    public MBLeiXingBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMb_Name() {
        return this.mb_Name;
    }
    public void setMb_Name(String mb_Name) {
        this.mb_Name = mb_Name;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getShenfen() {
        return this.shenfen;
    }
    public void setShenfen(String shenfen) {
        this.shenfen = shenfen;
    }
    public String getHuanyinyu() {
        return this.huanyinyu;
    }
    public void setHuanyinyu(String huanyinyu) {
        this.huanyinyu = huanyinyu;
    }
    public String getBg_path() {
        return this.bg_path;
    }
    public void setBg_path(String bg_path) {
        this.bg_path = bg_path;
    }
    



}
