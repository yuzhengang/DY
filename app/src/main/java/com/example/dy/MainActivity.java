package com.example.dy;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.example.dy.base.BaseActivity;
import com.example.dy.base.BasePresenter;
import com.example.dy.record.RecordActivity;
import com.example.livepusher.R;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void initModule() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.recordBtn})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recordBtn:
                requestCemera();
                break;
        }
    }

    private void requestCemera() {
        PermissionsUtil.requestPermission(getApplication(), new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                Toast.makeText(MainActivity.this, "用户拒绝了访问摄像头", Toast.LENGTH_LONG).show();
            }
        }, Manifest.permission.CAMERA);
    }
}
