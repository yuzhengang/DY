package com.example.dy.record;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.example.dy.base.BaseActivity;
import com.example.dy.base.BasePresenter;
import com.example.dy.camera.CameraView;
import com.example.dy.encodec.BaseMediaEncoder;
import com.example.dy.encodec.AVMediaEncodec;
import com.example.dy.widget.RecordButton;
import com.example.livepusher.R;
import com.ywl5320.libmusic.WlMusic;
import com.ywl5320.listener.OnCompleteListener;
import com.ywl5320.listener.OnPreparedListener;
import com.ywl5320.listener.OnShowPcmDataListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordActivity extends BaseActivity {
    @BindView(R.id.cameraView)
    CameraView cameraView;

    @BindView(R.id.recordDetailBtn)
    RecordButton recordDetailBtn;

    private AVMediaEncodec mediaEncodec;
    private WlMusic wlMusic;
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
         setContentView(R.layout.activity_record);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void initModule() {
        ButterKnife.bind(this);
        wlMusic=WlMusic.getInstance();
        wlMusic.setCallBackPcmData(true);
        wlMusic.setOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete() {
                if(mediaEncodec != null)
                {
                    mediaEncodec.stopRecord();
                    mediaEncodec = null;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }
        });
        wlMusic.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                wlMusic.playCutAudio(39,60);
            }
        });
        wlMusic.setOnShowPcmDataListener(new OnShowPcmDataListener() {
            @Override
            public void onPcmInfo(int samplerate, int bit, int channels) {
                mediaEncodec = new AVMediaEncodec(RecordActivity.this, cameraView.getTextureId());
                mediaEncodec.initEncodec(cameraView.getEglContext( ), Environment.getExternalStorageDirectory().getAbsolutePath() + "/video.mp4",720, 1280, samplerate, channels);
                mediaEncodec.setOnMediaInfoListener(new BaseMediaEncoder.OnMediaInfoListener() {
                    @Override
                    public void onMediaTime(int times) {

                    }
                });
                mediaEncodec.startRecord();
            }
            @Override
            public void onPcmData(byte[] pcmdata, int size, long clock) {
                if(mediaEncodec!=null){
                    mediaEncodec.putPCMData(pcmdata,size);
                }
            }
        });
        recordDetailBtn.setOnRecordStateChangedListener(new RecordButton.OnRecordStateChangedListener() {
            @Override
            public void onRecordStart() {

            }

            @Override
            public void onRecordStop() {

            }

            @Override
            public void onZoom(float percentage) {

            }
        });
    }
    public void record( ) {
        if(mediaEncodec == null)
        {
            wlMusic.setSource("");
            wlMusic.prePared();
        }
        else
        {
            mediaEncodec.stopRecord();
            mediaEncodec = null;
            wlMusic.stop();
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }
}
