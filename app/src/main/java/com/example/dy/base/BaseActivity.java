package com.example.dy.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BaseActivity";

    protected P mPresenter;

    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (onCreatePresenter() != null) {
            mPresenter = onCreatePresenter();
        }
        mContext = this;
        createDialog();
        onCreateView(savedInstanceState);
        initModule();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        System.gc();
        if (mPresenter != null) {
            mPresenter = null;
        }
        super.onDestroy();
    }

    public void createDialog() {

    }

    protected abstract void onCreateView(Bundle savedInstanceState);

    protected abstract P onCreatePresenter();

    protected abstract void initModule();

    protected abstract void initData();



    public void startActivity(Class<? extends AppCompatActivity> target, Bundle bundle, boolean finish) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bundle != null)
            intent.putExtra(getPackageName(), bundle);
        startActivity(intent);
        if (finish)
            finish();
    }

    public Bundle getBundle() {
        if (getIntent() != null && getIntent().hasExtra(getPackageName()))
            return getIntent().getBundleExtra(getPackageName());
        else
            return null;
    }
}
