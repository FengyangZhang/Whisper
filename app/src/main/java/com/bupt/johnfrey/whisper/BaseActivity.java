package com.bupt.johnfrey.whisper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.bupt.johnfrey.whisper.otto.OttoManager;

import butterknife.ButterKnife;

/**
 *  Created by zhangfengyang on 16/4/1
 */
public abstract class BaseActivity extends Activity {
    public GestureDetector gestureDetector;
    public Context context;
    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TEST", getClass().getSimpleName());
        setStyle();
        super.onCreate(savedInstanceState);
        this.getArgs(this.getIntent().getExtras());
        gestureDetector = new GestureDetector(context, new MyGestureListener());
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

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override //双击
        public boolean onDoubleTap(MotionEvent e) {
            super.onDoubleTap(e);
            onDoubleTapView();
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            super.onSingleTapConfirmed(e);
            onSingleTapConfirmedView();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            super.onFling(e1, e2, velocityX, velocityY);
            onFlingView(e1, e2, velocityX, velocityY);
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    public void onSingleTapConfirmedView() {
    }

    public void onDoubleTapView() {
    }

    public void onFlingView(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        OttoManager.unregister(this);
    }
}
