package com.bupt.johnfrey.whisper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;


public class LoadingActivity extends BaseActivity {
    @Bind(R.id.new_whisper)
    TextView newWhisper;
    @Bind(R.id.ll_loading)
    LinearLayout llLoading;
    Animation animation;
    public void getArgs(Bundle var1){}

    public int setView(){return R.layout.aty_loading;}

    public void initView(){
    }

    public void setting(){}

    public void setListener(){
        newWhisper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(
                        LoadingActivity.this, R.anim.scale);
                newWhisper.setAnimation(animation);
                newWhisper.startAnimation(animation);
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
}
