package com.example.advanceSuggest;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.fileupload.FileItem;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class MyWebServer extends NanoHTTPD  {

    private final static int PORT = 33445;
    private Context _mainContext;


    /*
    主构造函数，也用来启动http服务
    */
    public MyWebServer(Context context) throws IOException {
        super(PORT);
        _mainContext = context;
        start();
        System.out.println("\nRunning! Point your browsers to [http://0.0.0.0:33445/](http://localhost:33445/)\n");
    }

    /*
    解析的主入口函数，所有请求从这里进，也从这里出
    */
    @Override
    public Response serve(IHTTPSession session) {
        Map<String, String> headers = session.getHeaders();
        Map<String, List<String>> parms = session.getParameters();

        Method method = session.getMethod();
        String uri = session.getUri();
        Map<String, String> files = new HashMap<>();

        if (Method.POST.equals(method) || Method.PUT.equals(method)) {
            try {
                session.parseBody(files);
            } catch (IOException ioe) {
                return newFixedLengthResponse("Internal Error IO Exception: " + ioe.getMessage());
            } catch (ResponseException re) {
                return newFixedLengthResponse(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
            }
        }

        if ("/hggg".equalsIgnoreCase(uri)) {

            String filename = parms.get("fdsf").get(0);

            Log.d("MyWebServer", "filename" + filename);
            String tmpFilePath = files.get("fdsf");
            Log.d("MyWebServer", "tmpFilePath"+tmpFilePath);

            if (null == filename || null == tmpFilePath) {
                return newFixedLengthResponse("文件无效");
            }
            File dst = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
            if (dst.exists()) {
                // Response for confirm to overwrite
            }
            File src = new File(tmpFilePath);

            try {
                InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dst);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (IOException ioe) {
                // Response for failed
            }
            // Response for success
        }

        return  newFixedLengthResponse("ouo") ; // =========》  返回给客户端
    }


}