package com.example.dy.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;

import com.example.dy.egl.EGLSurfaceView;

public class   CameraView  extends EGLSurfaceView {


    private CameraRender cameraRender;
    private CameraUtil cameraUtil;

    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    private int textureId = -1;

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cameraRender = new CameraRender(context);
        cameraUtil = new CameraUtil(context);
        setRender(cameraRender);
        previewAngle(context);
        cameraRender.setOnSurfaceCreateListener(new CameraRender.OnSurfaceCreateListener() {
            @Override
            public void onSurfaceCreate(SurfaceTexture surfaceTexture, int tid) {
                cameraUtil.initCamera(surfaceTexture, cameraId);
                textureId = tid;
            }
        });
    }

    public void onDestory()
    {
        if(cameraUtil != null)
        {
            cameraUtil.stopPreview();
        }
    }

    public void previewAngle(Context context)
    {
        int angle = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        cameraRender.resetMatrix();
        switch (angle)
        {
            case Surface.ROTATION_0:
                if(cameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
                {
                    cameraRender.setAngle(90, 0, 0, 1);
                    cameraRender.setAngle(180, 1, 0, 0);
                }
                else
                {
                    cameraRender.setAngle(90f, 0f, 0f, 1f);
                }

                break;
            case Surface.ROTATION_90:
                if(cameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
                {
                    cameraRender.setAngle(180, 0, 0, 1);
                    cameraRender.setAngle(180, 0, 1, 0);
                }
                else
                {
                    cameraRender.setAngle(90f, 0f, 0f, 1f);
                }
                break;
            case Surface.ROTATION_180:
                if(cameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
                {
                    cameraRender.setAngle(90f, 0.0f, 0f, 1f);
                    cameraRender.setAngle(180f, 0.0f, 1f, 0f);
                }
                else
                {
                    cameraRender.setAngle(-90, 0f, 0f, 1f);
                }
                break;
            case Surface.ROTATION_270:
                if(cameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
                {
                    cameraRender.setAngle(180f, 0.0f, 1f, 0f);
                }
                else
                {
                    cameraRender.setAngle(0f, 0f, 0f, 1f);
                }
                break;
        }
    }

    public int getTextureId()
    {
        return textureId;
    }
}
