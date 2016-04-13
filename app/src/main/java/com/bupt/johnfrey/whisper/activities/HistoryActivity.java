package com.bupt.johnfrey.whisper.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bupt.johnfrey.whisper.BaseActivity;
import com.bupt.johnfrey.whisper.R;

import java.util.ArrayList;

import butterknife.Bind;

/**
 *  Created by zhangfengyang on 16/4/13
 */

public class HistoryActivity extends BaseActivity{
    @Bind(R.id.ib_header_back)
    ImageButton headerBack;
    @Bind(R.id.tv_history)
    TextView tvHistory;
    ArrayList<Integer> moods;
    int bad_num;
    int good_num;
    @Override
    public void getArgs(Bundle var1) {
        Bundle args = getIntent().getExtras();
        this.moods = args.getIntegerArrayList("moods");
        Log.d("TEST",""+moods);
    }

    @Override
    public int setView() {
        return R.layout.aty_history;
    }

    @Override
    public void initView() {
        bad_num = 0;
        good_num = 0;
        for(int i = 0;i < moods.size();i++){
            if(moods.get(i) < 0xffdddddd){
                bad_num++;
            }
            if(moods.get(i) > 0xffdddddd){
                good_num++;
            }
        }
        tvHistory.setText("You've recorded "+bad_num+" days of bad mood,and "+good_num+" days of good mood.");
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
