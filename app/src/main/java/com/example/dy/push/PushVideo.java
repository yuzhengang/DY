package com.example.dy.push;

import android.text.TextUtils;

public class PushVideo {

    private  ConnectListener   connectListener;

    static {
        System.loadLibrary("push");
    }

    public void setWlConnectListenr(ConnectListener   connectListener) {
        this.connectListener = connectListener;
    }


    private void onConnecting()
    {
        if(connectListener != null)
        {
            connectListener.onConnecting();
        }
    }

    private void onConnectSuccess()
    {
        if(connectListener != null)
        {
            connectListener.onConnectSuccess();
        }
    }

    private void onConnectFial(String msg)
    {
        if(connectListener != null)
        {
            connectListener.onConnectFail(msg);
        }
    }

    public void initLivePush(String url)
    {
        if(!TextUtils.isEmpty(url))
        {
            initPush(url);
        }
    }

    public void pushSPSPPS(byte[] sps, byte[] pps)
    {
        if(sps != null && pps != null)
        {
            pushSPSPPS(sps, sps.length, pps, pps.length);
        }
    }

    public void pushVideoData(byte[] data, boolean keyframe)
    {
        if(data != null)
        {
            pushVideoData(data, data.length, keyframe);
        }
    }

    public void pushAudioData(byte[] data)
    {
        if(data != null)
        {
            pushAudioData(data, data.length);
        }
    }

    public void stopPush()
    {
        pushStop();
    }


    private native void initPush(String pushUrl);

    private native void pushSPSPPS(byte[] sps, int sps_len, byte[] pps, int pps_len);

    private native void pushVideoData(byte[] data, int data_len, boolean keyframe);

    private native void pushAudioData(byte[] data, int data_len);

    private native void pushStop();

}
