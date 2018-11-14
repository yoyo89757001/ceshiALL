package com.example.sanjiaoji.model;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Administrator on 2017/9/15.
 */
@Entity
public class BaoCunBean {
    @Id(assignable = true)
    private Long id;
    private String zhanghuId;
    private String touxiangzhuji;
    private String houtaiDiZhi;
    private String guanggaojiMing;
    private int size;
    private int size1;

    public BaoCunBean() {

    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize1() {
        return size1;
    }

    public void setSize1(int size1) {
        this.size1 = size1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZhanghuId() {
        return zhanghuId;
    }

    public void setZhanghuId(String zhanghuId) {
        this.zhanghuId = zhanghuId;
    }

    public String getTouxiangzhuji() {
        return touxiangzhuji;
    }

    public void setTouxiangzhuji(String touxiangzhuji) {
        this.touxiangzhuji = touxiangzhuji;
    }

    public String getHoutaiDiZhi() {
        return houtaiDiZhi;
    }

    public void setHoutaiDiZhi(String houtaiDiZhi) {
        this.houtaiDiZhi = houtaiDiZhi;
    }

    public String getGuanggaojiMing() {
        return guanggaojiMing;
    }

    public void setGuanggaojiMing(String guanggaojiMing) {
        this.guanggaojiMing = guanggaojiMing;
    }

    @Override
    public String toString() {
        return "BaoCunBean{" +
                "id=" + id +
                ", zhanghuId='" + zhanghuId + '\'' +
                ", touxiangzhuji='" + touxiangzhuji + '\'' +
                ", houtaiDiZhi='" + houtaiDiZhi + '\'' +
                ", guanggaojiMing='" + guanggaojiMing + '\'' +
                ", size=" + size +
                ", size1=" + size1 +
                '}';
    }
}
