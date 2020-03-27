package com.example.dy.push;

import android.content.Context;

import com.example.dy.encodec.BaseMediaEncoder;

public class AVPushEncodec extends BasePushEncoder {

    private AVEncodecPushRender encodecPushRender;

    public AVPushEncodec(Context context, int textureId) {
        super(context);
        encodecPushRender = new AVEncodecPushRender(context, textureId);
        setRender(encodecPushRender);
        setmRenderMode(BaseMediaEncoder.RENDERMODE_CONTINUOUSLY);
    }
}
