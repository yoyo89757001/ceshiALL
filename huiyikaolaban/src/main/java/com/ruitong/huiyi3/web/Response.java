package com.ruitong.huiyi3.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private static final int BUFFER_SIZE = 1024;
    Request request;

    public synchronized void setRequest(Request request) {
        this.request = request;
    }

    public synchronized void sendStaticResource(OutputStream output) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            if (request != null) {
                File file = new File(
                        HttpServer.WEB_ROOT, request.getUri());
                if (file.exists() && file.isFile()) {
                    output.write("HTTP/1.1 200 OK\n".getBytes());
                    output.write("Content-Type: text/html; charset=UTF-8\n\n".getBytes());
                    fis = new FileInputStream(file);
                    int readLength;
                    while ((readLength = fis.read(buffer, 0, BUFFER_SIZE)) > 0) {
                        output.write(buffer, 0, readLength);
                    }
                } else if ("/".equals(request.getUri())) {
                    output.write("HTTP/1.1 200 OK\n".getBytes());
                } else {
                    String errMsg = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
                            + "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
                    output.write(errMsg.getBytes());
                }
            } else {
                System.out.println("request is null");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}