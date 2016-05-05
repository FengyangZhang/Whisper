package com.bupt.johnfrey.whisper.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.johnfrey.whisper.BaseActivity;
import com.bupt.johnfrey.whisper.R;

import butterknife.Bind;

/**
 *  Created by zhangfengyang on 16/4/13
 */
public class EchoActivity extends BaseActivity {
    @Bind(R.id.ll_echo)
    LinearLayout llEcho;
    @Bind(R.id.tv_echo)
    TextView tvEcho;
    int mood;

    @Override
    public void getArgs(Bundle var1) {
        Bundle bundle = getIntent().getExtras();
        mood = bundle.getInt("mood");
    }

    @Override
    public int setView() {
        return R.layout.aty_echo;
    }

    @Override
    public void initView() {
        if (mood == 0xffdddddd) {
            tvEcho.setText("A smooth mood is better than anything.");
        } else if (mood > 0xffdddddd) {
            tvEcho.setText("Seems like a good day for you.");
        } else if (mood < 0xffdddddd) {
            tvEcho.setText("Everything will flow,just hold on.");
        }
    }

    @Override
    public void setting() {

    }

    @Override
    public void setListener() {
        llEcho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
