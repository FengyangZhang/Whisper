package com.bupt.johnfrey.whisper.activities;

/**
 *  Created by zhangfengyang on 16/4/5
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bupt.johnfrey.whisper.BaseActivity;
import com.bupt.johnfrey.whisper.NaiveBayesian.TrainData;
import com.bupt.johnfrey.whisper.NaiveBayesian.TrainVector;
import com.bupt.johnfrey.whisper.NaiveBayesian.Vocabulary;
import com.bupt.johnfrey.whisper.R;
import com.bupt.johnfrey.whisper.accessories.WhisperPopupWindow;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Bind(R.id.tv_time)
    TextView tvTime;

    WhisperPopupWindow photoPopup;
    String filePath = Environment.getExternalStorageDirectory() + "/Whisper/";
    String fileName;
    String time;
    int mood;
    TrainData dataManager;
    Vocabulary vocabulary;
    TrainVector vectorManager;

    public void getArgs(Bundle var1) {
    }

    public int setView() {
        return R.layout.aty_whisper;
    }

    public void initView() {
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.float_menu);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        // repeat many times:
        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageResource(R.drawable.float_button1);
        SubActionButton btn_refresh = itemBuilder.setContentView(itemIcon1).build();

        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageResource(R.drawable.float_button2);
        SubActionButton btn_shine = itemBuilder.setContentView(itemIcon2).build();

        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageResource(R.drawable.float_button3);
        SubActionButton btn_camera = itemBuilder.setContentView(itemIcon3).build();

        ImageView itemIcon4 = new ImageView(this);
        itemIcon4.setImageResource(R.drawable.float_button4);
        SubActionButton btn_echo = itemBuilder.setContentView(itemIcon4).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(btn_refresh)
                .addSubActionView(btn_shine)
                .addSubActionView(btn_camera)
                .addSubActionView(btn_echo)
                .attachTo(actionButton)
                .build();
        //set listeners for the floating buttons
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPopup = new WhisperPopupWindow(WhisperActivity.this, camera_listener);
                //显示窗口
                photoPopup.showAtLocation(activity.findViewById(R.id.ll_whisper), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mood = 0xffdddddd;
                changeBackgroundColor(0);
            }
        });
        btn_shine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mood += 0xff101010;
                changeBackgroundColor(1);
            }
        });
        btn_echo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,EchoActivity.class);
                startActivity(intent);
            }
        });
        //show current time
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        time = formatter.format(curDate) + "\r\n";
        tvTime.setText(time);
        fileName = "Whisper_" + time + ".txt";
        etWhisper.requestFocus();
        //show current mood
        mood = 0xffdddddd;
        changeBackgroundColor(0);
        //train
        dataManager = new TrainData();
        vocabulary = new Vocabulary();
        vectorManager = new TrainVector();
        dataManager.init();
        vocabulary.init(dataManager.getData());
        vectorManager.init();
        for(int i = 0;i<dataManager.getData().size();i++){
            vectorManager.data2Vector(vocabulary.get(), dataManager.getData().get(i));
        }
        vectorManager.train(dataManager.getDataClass());
    }

    private void changeBackgroundColor(int extent) {

        etWhisper.setBackgroundColor(mood);
    }


    public void setting() {
    }

    public void setListener() {
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
                if (etWhisper.getText().toString().trim().length() == 0) {
                    Toast.makeText(WhisperActivity.this, "Empty input", Toast.LENGTH_SHORT).show();
                    headerRight.setImageResource(R.drawable.whisper_save_unpressed);
                } else {
                    saveWhisper(etWhisper.getText().toString().trim(), time, mood, filePath, fileName);
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
        etWhisper.addTextChangedListener(echo_listener);
    }
    private View.OnClickListener camera_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            photoPopup.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    break;
                case R.id.btn_pick_photo:
                    break;
                default:
                    break;
            }
        }
    };

    private TextWatcher echo_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if((s.toString().length() >= 1) && ((s.toString().charAt(s.toString().length()-1) == 32)||
                    (s.toString().charAt(s.toString().length()-1) == 33)||
                    (s.toString().charAt(s.toString().length()-1) == 44)||
                    (s.toString().charAt(s.toString().length()-1) == 46)||
                    (s.toString().charAt(s.toString().length()-1) == 63)||
                    (s.toString().charAt(s.toString().length()-1) == 10)||
                    (s.toString().charAt(s.toString().length()-1) == 13))){
                echo(s.toString());
            }
        }
    };
    @Override
    public void onFlingView(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        super.onFlingView(e1, e2, velocityX, velocityY);
        if (e2.getY() - e1.getY() > 100 && Math.abs(velocityY) > 4000 && Math.abs(e1.getX() - e2.getX()) < 150) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(etWhisper.getWindowToken(), 0);
        }
    }

    @Override
    public void onSingleTapConfirmedView() {
        super.onSingleTapConfirmedView();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public void saveWhisper(String strcontent, String time, int mood, String filePath, String fileName) {
        makeFilePath(filePath, fileName);

        String strFilePath = filePath + fileName;

        String strContent = mood+"\r\n"+time + strcontent + "\r\n";
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
    public void echo(String s){
        List<String> test = new ArrayList<>();
        List<Integer> testVec = new ArrayList<>();
        Pattern p = Pattern.compile("[.,\"\\?!:'\r\n]");// 增加对应的标点
        Matcher m = p.matcher(s);
        s = m.replaceAll(" "); // 把英文标点符号替换成空，即去掉英文标点符号
        Log.d("TEST",s);
        String temp[] = s.toLowerCase().split(" ");
        for(int j = 0;j < temp.length;j++){
            test.add(temp[j]);
        }
        testVec = vectorManager.test2Vector(vocabulary.get(), test);
        int result = vectorManager.judge(testVec);
        if(result == 1){
            Toast.makeText(WhisperActivity.this, "positive words!", Toast.LENGTH_SHORT).show();
            mood += 0x00101010;
            if(mood <= 0xffffffff){
                changeBackgroundColor(1);
            }
            else Toast.makeText(WhisperActivity.this, "good mood indeed!", Toast.LENGTH_SHORT).show();
        }
        else if(result == -1){
            Toast.makeText(WhisperActivity.this, "negative words!", Toast.LENGTH_SHORT).show();
            mood -= 0x00101010;
            if(mood >= 0xff000000){
                changeBackgroundColor(-1);
            }
            else Toast.makeText(WhisperActivity.this, "bad mood indeed...", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(WhisperActivity.this, "neutral words!", Toast.LENGTH_SHORT).show();
            changeBackgroundColor(0);

        }
    }
}
