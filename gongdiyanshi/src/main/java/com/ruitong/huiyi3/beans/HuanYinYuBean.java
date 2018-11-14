package com.ruitong.huiyi3.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/4/23.
 */
@Entity
public class HuanYinYuBean {
    @Id
    @NotNull
    private Long id;
    private String name;
    private String huanyinci;
    private int bianhao;
    @Generated(hash = 201735880)
    public HuanYinYuBean(@NotNull Long id, String name, String huanyinci,
            int bianhao) {
        this.id = id;
        this.name = name;
        this.huanyinci = huanyinci;
        this.bianhao = bianhao;
    }
    @Generated(hash = 1694812871)
    public HuanYinYuBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHuanyinci() {
        return this.huanyinci;
    }
    public void setHuanyinci(String huanyinci) {
        this.huanyinci = huanyinci;
    }
    public int getBianhao() {
        return this.bianhao;
    }
    public void setBianhao(int bianhao) {
        this.bianhao = bianhao;
    }
    


}
