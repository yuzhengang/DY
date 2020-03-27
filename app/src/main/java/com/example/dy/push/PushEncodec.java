package com.example.dy.push;

import android.content.Context;

import com.example.dy.encodec.BaseMediaEncoder;

public class PushEncodec  extends BasePushEncoder {

    private EncodecPushRender   encodecPushRender;

    public  PushEncodec(Context context, int textureId) {
        super(context);
        encodecPushRender = new EncodecPushRender(context, textureId);
        setRender(encodecPushRender);
        setmRenderMode(BaseMediaEncoder.RENDERMODE_CONTINUOUSLY);
    }
}
