package com.bupt.johnfrey.whisper;

/**
 *  Created by zhangfengyang on 16/4/5
 */

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import butterknife.Bind;

public class WhisperActivity extends BaseActivity {
    @Bind(R.id.et_whisper)
    EditText etWhisper;
    @Bind(R.id.ll_whisper)
    LinearLayout llWhisper;
    @Bind(R.id.ib_header_back)
    ImageButton headerBack;
    @Bind(R.id.ib_header_right)
    ImageButton headerRight;

    WhisperPopupWindow photoPopup;

    public void getArgs(Bundle var1){}

    public int setView(){return R.layout.aty_whisper;}

    public void initView(){
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.float_menu);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        // repeat many times:
        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageResource(R.drawable.float_button1);
        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();

        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageResource(R.drawable.float_button2);
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();

        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageResource(R.drawable.float_button3);
        SubActionButton btn_camera = itemBuilder.setContentView(itemIcon3).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(btn_camera)
                .attachTo(actionButton)
                .build();

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                photoPopup = new WhisperPopupWindow(WhisperActivity.this, itemsOnClick);
//                //显示窗口
//                menuWindow.showAtLocation(MainActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });

    }

    public void setting(){}

    public void setListener(){
        headerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerBack.setImageResource(R.drawable.arrow_back_pressed);
                finish();
            }
        });
        headerRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerRight.setImageResource(R.drawable.whisper_save_pressed);
                finish();
            }
        });
        etWhisper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    @Override
    public void onFlingView(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
        if(e2.getY() - e1.getY() > 100 && Math.abs(velocityY) > 4000 && Math.abs(e1.getX() - e2.getX()) < 150){
            InputMethodManager inputMethodManager =
                    (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(etWhisper.getWindowToken(), 0);
        }
    }
    @Override
    public void onSingleTapConfirmedView(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
