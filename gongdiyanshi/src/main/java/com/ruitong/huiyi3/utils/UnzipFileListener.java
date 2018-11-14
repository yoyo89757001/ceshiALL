package com.ruitong.huiyi3.utils;

/**
 * Created by Administrator on 2018/6/2.
 */

public interface UnzipFileListener {

     void onUnzipStart();
    void onUnziping(int p);
    void onUnzipComplet();
    void onUnzipError();
}
