package com.example.dy.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.dy.util.DisplayUtil;
import com.example.livepusher.R;
import com.example.dy.egl.EGLSurfaceView;
import com.example.dy.egl.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class CameraRender implements EGLSurfaceView.GLRender, SurfaceTexture.OnFrameAvailableListener {

    private Context context;

    private FloatBuffer vertexBuffer;

    private FloatBuffer fragmentBuffer;

    private int program;

    private int vPosition;

    private int fPosition;

    private int vboId;

    private int fboId;

    private int fboTextureid;

    private int cameraTextureid;

    private float[] vertexData = {
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f,
    };

    private float[] fragmentData = {
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
    };

    private int umatrix;
    private float[] matrix = new float[16];
    private SurfaceTexture surfaceTexture;
    private OnSurfaceCreateListener onSurfaceCreateListener;
    private CameraFboRender cameraFboRender;

    private int  screenWidth;
    private int  screenHeight;

    private int  width;
    private int  height;

    public CameraRender(Context context) {
        this.context = context;
        screenWidth = DisplayUtil.getScreenWidth(context);
        screenHeight = DisplayUtil.getScreenHeight(context);

        cameraFboRender=new CameraFboRender(context);
        vertexBuffer= ByteBuffer.allocateDirect(vertexData.length*4)
                                .order(ByteOrder.nativeOrder())
                                .asFloatBuffer()
                                .put(vertexData);
        vertexBuffer.position(0);


        fragmentBuffer = ByteBuffer.allocateDirect(fragmentData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(fragmentData);
        fragmentBuffer.position(0);

    }
    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {

    }
    @Override
    public void onSurfaceCreated() {
        cameraFboRender.onCreate();
        String vertexSource = ShaderUtil.getRawResource(context, R.raw.vertex_shader);
        String fragmentSource = ShaderUtil.getRawResource(context, R.raw.fragment_shader);
        program=ShaderUtil.createProgram(vertexSource,fragmentSource);
        vPosition= GLES20.glGetAttribLocation(program,"v_Position");
        fPosition = GLES20.glGetAttribLocation(program, "f_Position");
        umatrix = GLES20.glGetUniformLocation(program, "u_Matrix");
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexData.length * 4 + fragmentData.length * 4, null, GLES20. GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        //fbo
        int[] fbos = new int[1];
        GLES20.glGenBuffers(1, fbos, 0);
        fboId = fbos[0];
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId);
        int []textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        fboTextureid = textureIds[0];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, fboTextureid);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        surfaceTexture = new SurfaceTexture(cameraTextureid);
        surfaceTexture.setOnFrameAvailableListener(this);
        if(onSurfaceCreateListener != null)
        {
            onSurfaceCreateListener.onSurfaceCreate(surfaceTexture, fboTextureid);
        }
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
    }

    public void resetMatrix()
    {
        Matrix.setIdentityM(matrix, 0);
    }

    public void setAngle(float angle, float x, float y, float z)
    {
        Matrix.rotateM(matrix, 0, angle, x, y, z);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void onDrawFrame() {
        surfaceTexture.updateTexImage();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1f,0f, 0f, 1f);

        GLES20.glUseProgram(program);

        GLES20.glViewport(0, 0, screenWidth, screenHeight);
        GLES20.glUniformMatrix4fv(umatrix, 1, false, matrix, 0);

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboId);

        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 8, 0);
        GLES20.glEnableVertexAttribArray(fPosition);
        GLES20.glVertexAttribPointer(fPosition, 2, GLES20.GL_FLOAT, false, 8, vertexData.length * 4);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        cameraFboRender.onChange(width, height);
        cameraFboRender.onDraw(fboTextureid);
    }

    public interface OnSurfaceCreateListener {

        void onSurfaceCreate(SurfaceTexture surfaceTexture, int textureId);
    }

    public void setOnSurfaceCreateListener(OnSurfaceCreateListener onSurfaceCreateListener) {
        this.onSurfaceCreateListener = onSurfaceCreateListener;
    }

    public int getFboTextureid() {
        return fboTextureid;
    }
}
