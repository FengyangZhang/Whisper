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
    @Override
    public void getArgs(Bundle var1) {

    }

    @Override
    public int setView() {
        return R.layout.aty_echo;
    }

    @Override
    public void initView() {
        tvEcho.setText("OK,I heard it,I heard it!");
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
