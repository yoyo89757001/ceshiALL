package com.example.advanceSuggest;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class GLRendel implements GLSurfaceView.Renderer {
    private int one=0x10000;

    private int[] intBuffer=new int[]{0,one,0
    -one,-one,0,
    one,-one,0};


@Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_NICEST);

          gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glClearColor(0,0,0,1);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float rodio=width/height;
        //设置视口(openGL场景大小)
        gl.glViewport(0,0,width,height);
        //透视投影
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //重置投影矩阵
        gl.glLoadIdentity();
        //设置视口大小
        gl.glFrustumf(-rodio,rodio,-1,1,1,10);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //清屏
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);

        //设置模型视图矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        //重置矩阵
        gl.glLoadIdentity();
        //视点变换
        GLU.gluLookAt(gl,0,0,3,0,0,0,0,1,0);
        gl.glLoadIdentity();
        gl.glTranslatef(-3.0f,0.0f,-4.0f);
        gl.glColor4f(1,0,0,1);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FIXED,0,bufferUtil(intBuffer));

        gl.glDrawArrays(GL10.GL_TRIANGLES,0,3);



    //    gl.glTranslatef(1f,0f,-4f);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);






    }


    /*
     * OpenGL 是一个非常底层的画图接口，它所使用的缓冲区存储结构是和我们的 java 程序中不相同的。
     * Java 是大端字节序(BigEdian)，而 OpenGL 所需要的数据是小端字节序(LittleEdian)。
     * 所以，我们在将 Java 的缓冲区转化为 OpenGL 可用的缓冲区时需要作一些工作。建立buff的方法如下
     * */
    public Buffer bufferUtil(int []arr){
        IntBuffer mBuffer ;
        //先初始化buffer,数组的长度*4,因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        //数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());

        mBuffer = qbb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);

        return mBuffer;
    }
}
