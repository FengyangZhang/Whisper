package com.bupt.johnfrey.whisper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import butterknife.Bind;


public class LoadingActivity extends BaseActivity {
    @Bind(R.id.new_whisper)
    TextView newWhisper;
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
    }

}
