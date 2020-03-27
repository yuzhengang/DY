package com.example.dy.encodec;

import android.content.Context;

public class AVMediaEncodec extends BaseMediaEncoder {

    private AVEncodecRender encodecRender;

    public AVMediaEncodec(Context context, int textureId){
      super(context);
      encodecRender=new AVEncodecRender(context,textureId);
      setRender(encodecRender);
      setmRenderMode(BaseMediaEncoder.RENDERMODE_CONTINUOUSLY);
    }
}
