package com.bupt.johnfrey.whisper.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bupt.johnfrey.whisper.BaseActivity;
import com.bupt.johnfrey.whisper.R;

import butterknife.Bind;

/**
 *  Created by zhangfengyang on 16/4/9
 */
public class ReadArchiveActivity extends BaseActivity {
    @Bind(R.id.ib_header_back)
    ImageButton headerBack;
    @Bind(R.id.tv_archive_text)
    TextView tvArchiveText;

    String content;
    @Override
    public void getArgs(Bundle var1) {
        Bundle args = getIntent().getExtras();
        content = args.getString("content");
    }

    @Override
    public int setView() {
        return R.layout.aty_read_archive;
    }

    @Override
    public void initView() {
        tvArchiveText.setText(content);
    }

    @Override
    public void setting() {

    }

    @Override
    public void setListener() {
        headerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerBack.setImageResource(R.drawable.arrow_back_pressed);
                finish();
            }
        });
    }
}
