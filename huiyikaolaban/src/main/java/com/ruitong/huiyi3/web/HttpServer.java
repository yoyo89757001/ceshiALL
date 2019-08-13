package com.ruitong.huiyi3.web;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private static final int SERVER_PORT = 9000;
    public static final String WEB_ROOT = System.getProperty("user.dir")
            + File.separator + "webroot";

    public void await() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("server started...");
        Request request = new Request();
        Response response = new Response();
        response.setRequest(request);
        Socket socket;
        InputStream input;
        OutputStream output;
        while (true) {
            try {

                socket = serverSocket.accept();
                socket.setSoTimeout(1000);
                input = socket.getInputStream();


                request.parse(input);
                output = socket.getOutputStream();
                response.sendStaticResource(output);


                socket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

