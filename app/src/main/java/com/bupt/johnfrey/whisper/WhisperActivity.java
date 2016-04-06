package com.bupt.johnfrey.whisper;

/**
 *  Created by zhangfengyang on 16/4/5
 */

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import butterknife.Bind;

public class WhisperActivity extends BaseActivity {
    @Bind(R.id.ib_header_back)
    ImageButton headerBack;
    @Bind(R.id.ib_header_right)
    ImageButton headerRight;

    public void getArgs(Bundle var1){}

    public int setView(){return R.layout.aty_whisper;}

    public void initView(){
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.float_button);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
// repeat many times:
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageResource(R.drawable.float_menu);
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .attachTo(actionButton)
                .build();
    }

    public void setting(){}

    public void setListener(){
        headerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
