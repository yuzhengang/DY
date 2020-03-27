package com.example.dy.encodec;

import android.content.Context;

public class MediaEncodec extends BaseMediaEncoder {

    private EncodecRender  encodecRender;

    public  MediaEncodec(Context context, int textureId){
      super(context);
      encodecRender=new EncodecRender(context,textureId);
      setRender(encodecRender);
      setmRenderMode(BaseMediaEncoder.RENDERMODE_CONTINUOUSLY);
    }
}
