package com.bupt.johnfrey.whisper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.johnfrey.whisper.BaseActivity;
import com.bupt.johnfrey.whisper.R;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.File;

import butterknife.Bind;


public class LoadingActivity extends BaseActivity {
    @Bind(R.id.tv_new_whisper)
    TextView tvNewWhisper;
    @Bind(R.id.ll_loading)
    LinearLayout llLoading;
    @Bind(R.id.btn_pay)
    Button btnPay;
    Animation animation;

    public void getArgs(Bundle var1) {
    }

    public int setView() {
        return R.layout.aty_loading;
    }

    public void initView() {
        makeRootDirectory(Environment.getExternalStorageDirectory() + "/Whisper/");
    }

    public void setting() {
    }

    public void setListener() {
        tvNewWhisper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(
                        LoadingActivity.this, R.anim.scale);
                tvNewWhisper.setAnimation(animation);
                tvNewWhisper.startAnimation(animation);
                Intent intent = new Intent(activity, WhisperActivity.class);
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
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IWXAPI api = WXAPIFactory.createWXAPI(context, "wxb4ba3c02aa476ea1");
                PayReq req = new PayReq();
                req.appId = "wxf5ee3c151e269342";
                req.partnerId = "1285435401";
                req.prepayId = "wx2016042916105508fa9663500721589190";
                req.nonceStr = "1610558059";
                req.timeStamp = "1461917459";
                req.packageValue = "Sign=WXPay";
                req.sign = "CAF2C389E3DF413643BF9178A7E80D17";
                api.sendReq(req);
            }
        });
    }

    @Override
    public void onFlingView(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > 30 && Math.abs(velocityX) > 2000 && Math.abs(e1.getY() - e2.getY()) < 150) {
            Intent intent = new Intent(activity, ArchiveActivity.class);
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
