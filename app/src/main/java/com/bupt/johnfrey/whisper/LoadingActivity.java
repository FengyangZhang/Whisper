package com.bupt.johnfrey.whisper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
                Log.d("Test","onClick");
                animation = AnimationUtils.loadAnimation(
                        LoadingActivity.this, R.anim.scale);
                newWhisper.setAnimation(animation);
                newWhisper.startAnimation(animation);
                Intent intent = new Intent(activity,WhisperActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("Test","onCreated");
    }
    @Override
    protected void onStop() {
        Log.d("Test","onStop");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.d("Test","onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("Test","onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("Test","onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.d("Test","onRestart");
        super.onRestart();
    }
    @Override
    protected void onDestroy(){
        Log.d("Test","onDestroy");
        super.onDestroy();
    }
}
