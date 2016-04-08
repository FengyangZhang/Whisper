package com.bupt.johnfrey.whisper;

/**
 *  Created by zhangfengyang on 16/4/5
 */

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    String filePath = Environment.getExternalStorageDirectory() + "/Whisper/";
    String fileName = "whisper_0.txt";

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
                photoPopup = new WhisperPopupWindow(WhisperActivity.this, itemsOnClick);
                //显示窗口
                photoPopup.showAtLocation(activity.findViewById(R.id.ll_whisper), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        etWhisper.setText(str);
        etWhisper.requestFocus();
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            photoPopup.dismiss();
            switch(v.getId()){
                case R.id.btn_take_photo:
                    break;
                case R.id.btn_pick_photo:
                    break;
                default:
                    break;
            }
        }
    };
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
                if(etWhisper.getText().toString().trim().length() == 0){
                    Toast.makeText(WhisperActivity.this, "Empty input", Toast.LENGTH_SHORT).show();
                    headerRight.setImageResource(R.drawable.whisper_save_unpressed);
                }
                else {
                    scanExistingFiles();
                    saveWhisper(etWhisper.getText().toString().trim(), filePath, fileName);
                    finish();
                }
            }
        });
        llWhisper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    @Override
    public void onFlingView(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
        super.onFlingView(e1,e2,velocityX,velocityY);
        if(e2.getY() - e1.getY() > 100 && Math.abs(velocityY) > 4000 && Math.abs(e1.getX() - e2.getX()) < 150){
            InputMethodManager inputMethodManager =
                    (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(etWhisper.getWindowToken(), 0);
        }
    }
    @Override
    public void onSingleTapConfirmedView(){
        super.onSingleTapConfirmedView();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }



    public void saveWhisper(String strcontent, String filePath, String fileName) {
        // 生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
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
    public void scanExistingFiles(){
        int i = 0;
        File file = new File(filePath + fileName);
        while(file.exists()){
            i++;
            fileName = "whisper_"+i+".txt";
            file = new File(filePath+fileName);
        }
    }
}
