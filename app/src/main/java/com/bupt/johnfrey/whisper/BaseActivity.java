package com.bupt.johnfrey.whisper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 *  Created by zhangfengyang on 16/4/1
 */
public abstract class BaseActivity extends Activity{
    public Context context;
    public Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        setStyle();
        super.onCreate(savedInstanceState);
        this.getArgs(this.getIntent().getExtras());
        this.setContentView(this.setView());
        ButterKnife.bind(this);
        this.activity = this;
        this.context = this;
        OttoManager.register(this);
        this.setting();
        this.initView();
        this.setListener();
    }
    private void setStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Translucent navigation bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    public abstract void getArgs(Bundle var1);

    public abstract int setView();

    public abstract void initView();

    public abstract void setting();

    public abstract void setListener();
}
