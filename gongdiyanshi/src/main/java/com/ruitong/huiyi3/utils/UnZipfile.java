package com.ruitong.huiyi3.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ruitong.huiyi3.R;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2018/6/2.
 */

public class UnZipfile {

    private final static String TAG = "UnZipfile";
    private boolean isUnZip = false;
    private static UnZipfile instance;
    private int curpercent = 0;
    private UnzipFileListener mUnzipFileListener;
    private static  Thread thread;
    private Context mContext;
    private UnZipfile(Context context) {
        mContext = context;
    }
    public UnzipFileListener getmUnzipFileListener() {
        return mUnzipFileListener;
    }

    public void setmUnzipFileListener(UnzipFileListener mUnzipFileListener) {
        this.mUnzipFileListener = mUnzipFileListener;
    }

    final public static UnZipfile getInstance(Context context) {
        if (instance == null) {
            synchronized (UnZipfile.class) {
                if (instance == null) {
                    instance = new UnZipfile(context);
                }
            }
        }
        return instance;
    }

    public void unZip(String zipFilePath,String dirPath,Handler zipHandler) {

        if (zipFilePath == null || zipFilePath.equals("")) {
            Log.d(TAG, "此解决路径错误：" + zipFilePath);
            Toast.makeText(mContext, "解压路径错误" + zipFilePath,Toast.LENGTH_LONG).show();
            return;
        }

        // 防止重复解压缩一个文件，造成系统异常
        if (!isUnZip()) {
            File zipfile = new File(zipFilePath);
            if (zipfile.exists()) {

                try {
                    // 由于系统的原因，此处不允许删除源文件，否则会造成解压缩不全
//             File file =new File(getDefaultPath(EstickerApp.getAppContext()));
//             if (!file.exists()){
//                file.mkdir();
//             }
                    unZip(zipfile, dirPath, zipHandler,
                            false, false);
                } catch (ZipException e) {
                    e.printStackTrace();
                }

            } else {
                Log.d(TAG, zipFilePath + " 文件不存在");
                if(mContext!=null) {
                    Toast.makeText(mContext, zipFilePath + "文件不存在",Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    /**
     * 封装不同的解压状态
     *
     **/
    public static class CompressStatus {
        public final static int START = 10000;
        public final static int HANDLING = 10001;
        public final static int COMPLETED = 10002;
        public final static int ERROR = 10003;

        public final static String PERCENT = "PERCENT";
        public final static String ERROR_COM = "ERROR";
    }

    public boolean isUnZip() {
        return isUnZip;
    }

    public void setUnZip(boolean isUnZip) {
        this.isUnZip = isUnZip;
    }
    /**
     * 无密码解压
     *
     * @param zipFile
     *            源文件
     * @param filePath
     *            解压文件路径
     * @param handler
     *            进度回调
     * @param isDeleteZip
     *            是否删除源文件
     * @param isEncryption
     *            文件是否加密
     * @throws ZipException
     */
    public static void unZip(final File zipFile, String filePath, final Handler handler, final boolean isDeleteZip,
                             boolean isEncryption) throws ZipException {
        unZip(zipFile, filePath, handler, isDeleteZip, "", isEncryption);
    }
    /**
     * 解压
     *
     * @param zipFile
     *            源文件
     * @param filePath
     *            解压文件路径
     * @param handler
     *            进度回调
     * @param isDeleteZip
     *            是否删除源文件
     * @param password
     *            解压密码
     * @param isEncryption
     *            文件是否加密
     * @throws ZipException
     */
    private static void unZip(final File zipFile, String filePath, final Handler handler, final boolean isDeleteZip,
                              String password, boolean isEncryption) throws ZipException {

        if (isEncryption) {
            final File decodeFile = new File(zipFile.getParentFile(), "test");
            try {
                // 解密
                decodeFile(zipFile, decodeFile, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d(TAG, "开始解压");
            ZipFile zFile = new ZipFile(decodeFile);
            zFile.setFileNameCharset("GBK");

            if (!zFile.isValidZipFile()) {
                throw new ZipException("exception!");
            }
            File destDir = new File(filePath); // 解压目录文件
            if (destDir.isDirectory() && !destDir.exists()) {
                destDir.mkdir();
            }
            if (zFile.isEncrypted()) {
                zFile.setPassword(password); // 设置解压密码
            }

            final ProgressMonitor progressMonitor = zFile.getProgressMonitor();

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Bundle bundle = null;
                    Message msg = null;
                    int percent = 0;
                    try {
                        Log.d(TAG,"try start");
                        int precentDone = 0;
                        if (handler == null) {
                            return;
                        }

                     //   boolean isUzipStart = false;
                        while (true) {
                            // 每隔50ms,发送一个解压进度出去
                            Thread.sleep(500);
//                            int starState = progressMonitor.getState();
//                            Log.d(TAG,"starState = "+starState);
//                            if(starState == ProgressMonitor.STATE_BUSY){
//                             //   Log.d(TAG,"isUzipStart = true");
//                                isUzipStart = true;
//                            }
                            precentDone = progressMonitor.getPercentDone();
                            Log.d(TAG,"precentDone = "+precentDone);
                            if (percent != precentDone) {
                                percent = precentDone;
                                // System.out.println(precentDone);
                                bundle = new Bundle();
                                bundle.putInt(CompressStatus.PERCENT, precentDone);
                                msg = new Message();
                                msg.what = CompressStatus.HANDLING;
                                msg.setData(bundle);
                                handler.sendMessage(msg); // 通过 Handler将进度扔出去
                            }
                           // Log.d(TAG,"isUzipStart"+isUzipStart+"progressMonitor.getState()"+progressMonitor.getState());
                            if (precentDone >=100||( progressMonitor.getState()==ProgressMonitor.STATE_READY)) {

                                break;
                            }
                        }
                        Log.d(TAG,"handler CompressStatus.COMPLETED");
                        handler.sendEmptyMessage(CompressStatus.COMPLETED);
                    } catch (InterruptedException e) {
                        bundle = new Bundle();
                        bundle.putString(CompressStatus.ERROR_COM, e.getMessage());
                        msg = new Message();
                        msg.what = CompressStatus.ERROR;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                        e.printStackTrace();
                    } finally {
                        if (isDeleteZip) {
                            decodeFile.delete();// 将原压缩文件删除
                        }
                    }
                }
            });
            Log.d(TAG,"Thread start");
            thread.start();
            zFile.setRunInThread(true); // true 在子线程中进行解压 , false主线程中解压
            zFile.extractAll(filePath); // 将压缩文件解压到filePath中...
        } else {
            Log.d(TAG, "开始解压");
            ZipFile zFile = new ZipFile(zipFile);
            zFile.setFileNameCharset("GBK");

            if (!zFile.isValidZipFile()) {
                throw new ZipException("exception!");
            }
            File destDir = new File(filePath); // 解压目录文件
            if (destDir.isDirectory() && !destDir.exists()) {
                destDir.mkdir();
            }
            if (zFile.isEncrypted()) {
                zFile.setPassword(password); // 设置解压密码
            }

            final ProgressMonitor progressMonitor = zFile.getProgressMonitor();
            thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    Bundle bundle = null;
                    Message msg = null;
                    int percent = 0;
                    try {
                        Log.d(TAG,"try start");
                        int precentDone = 0;
                        if (handler == null) {
                            return;
                        }
                      //  boolean isUzipStart = false;
                        while (true) {
                            // 每隔50ms,发送一个解压进度出去
                            Thread.sleep(500);
                            precentDone = progressMonitor.getPercentDone();
//                            int starState = progressMonitor.getState();
//                            Log.d(TAG,"starState = "+starState);
//                            if(starState == ProgressMonitor.STATE_BUSY){
//                                isUzipStart = true;
//                                Log.d(TAG,"isUzipStart = true");
//                            }
//                            Log.d(TAG,"precentDone = "+precentDone);
                            // System.out.println(precentDone);
                            if (percent != precentDone) {
                                percent = precentDone;
                                bundle = new Bundle();
                                bundle.putInt(CompressStatus.PERCENT, precentDone);
                                msg = new Message();
                                msg.what = CompressStatus.HANDLING;
                                msg.setData(bundle);
                                handler.sendMessage(msg); // 通过 Handler将进度扔出去
                            }
//                            Log.d(TAG,"progressMonitor.getResult() = "+progressMonitor.getResult());
//                            progressMonitor.getResult();
//                            Log.d(TAG,"progressMonitor.getState() = "+progressMonitor.getState());
//                            Log.d(TAG,"progressMonitor.getWorkCompleted() = "+progressMonitor.getWorkCompleted());
//                            Log.d(TAG,"progressMonitor.getCurrentOperation() "+progressMonitor.getCurrentOperation()) ;
//                            Log.d(TAG,"isUzipStart"+isUzipStart+"progressMonitor.getState()"+progressMonitor.getState());
                            if (precentDone >= 100||( progressMonitor.getState()==ProgressMonitor.STATE_READY)) {
                                break;
                            }
                        }
                       // Log.d(TAG,"handler CompressStatus.COMPLETED");
                        handler.sendEmptyMessage(CompressStatus.COMPLETED);
                    } catch (InterruptedException e) {
                        bundle = new Bundle();
                        bundle.putString(CompressStatus.ERROR_COM, e.getMessage());
                        msg = new Message();
                        msg.what = CompressStatus.ERROR;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                        e.printStackTrace();
                    } finally {
                        if (isDeleteZip) {
                            zipFile.delete();// 将原压缩文件删除
                        }
                    }
                }

            });

            Log.d(TAG,"Thread start");

            Log.d(TAG," handler.sendEmptyMessage(CompressStatus.START);");
            handler.sendEmptyMessage(CompressStatus.START);
            thread.start();
            zFile.setRunInThread(false); // true 在子线程中进行解压 ,
            // false主线程中解压
            zFile.extractAll(filePath); // 将压缩文件解压到filePath中...

        }
    }
    /**
     * 解密文件
     *
     * @param sourceFile
     *            加密文件
     * @param decodeFile
     *            解密文件
     * @param b
     *            是否删除加密文件
     */
    public static void decodeFile(File sourceFile, File decodeFile, boolean b) {
        byte[] bs = new byte[1024];
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(sourceFile));
            out = new BufferedOutputStream(new FileOutputStream(decodeFile, false));
            while ((in.read(bs)) != -1) {
                for (int i = 0; i < bs.length; i++) {
                    out.write(bs[i] - 1);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                in.close();
                out.close();
            } catch (Exception e2) {
                // TODO: handle exception
                e2.printStackTrace();
            }
            if (b) {
                sourceFile.delete();
            }
        }

    }
    public  void stopUnzipFile(){
        if (thread!=null){
            thread.stop();
        }
    }
}