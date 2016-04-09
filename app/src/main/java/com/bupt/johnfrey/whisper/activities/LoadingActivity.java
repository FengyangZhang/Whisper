package com.bupt.johnfrey.whisper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.johnfrey.whisper.BaseActivity;
import com.bupt.johnfrey.whisper.R;

import java.io.File;

import butterknife.Bind;


public class LoadingActivity extends BaseActivity {
    @Bind(R.id.tv_new_whisper)
    TextView tvNewWhisper;
    @Bind(R.id.ll_loading)
    LinearLayout llLoading;
    Animation animation;
    public void getArgs(Bundle var1){}

    public int setView(){return R.layout.aty_loading;}

    public void initView(){
        makeRootDirectory(Environment.getExternalStorageDirectory() + "/Whisper/");
    }

    public void setting(){}

    public void setListener(){
        tvNewWhisper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(
                        LoadingActivity.this, R.anim.scale);
                tvNewWhisper.setAnimation(animation);
                tvNewWhisper.startAnimation(animation);
                Intent intent = new Intent(activity,WhisperActivity.class);
                startActivity(intent);
            }
        });
        llLoading.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    @Override
    public void onFlingView(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
        if(e1.getX() - e2.getX() > 30 && Math.abs(velocityX) > 2000 && Math.abs(e1.getY() - e2.getY()) < 150){
            Intent intent = new Intent(activity,ArchiveActivity.class);
            startActivity(intent);
        }
    }
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
