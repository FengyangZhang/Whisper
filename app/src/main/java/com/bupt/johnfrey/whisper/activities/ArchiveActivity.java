package com.bupt.johnfrey.whisper.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bupt.johnfrey.whisper.BaseActivity;
import com.bupt.johnfrey.whisper.R;
import com.bupt.johnfrey.whisper.accessories.ArchiveAdapter;
import com.bupt.johnfrey.whisper.otto.DeleteArchiveEvent;
import com.bupt.johnfrey.whisper.otto.ReadArchiveEvent;
import com.squareup.otto.Subscribe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;

/**
 *  Created by zhangfengyang on 16/4/8
 */
public class ArchiveActivity extends BaseActivity {
    @Bind(R.id.ib_header_back)
    ImageButton headerBack;
    @Bind(R.id.ib_header_right)
    ImageButton headerRight;
    @Bind(R.id.lv_archive)
    ListView lvArchive;
    @Bind(R.id.tv_no_archive)
    TextView tvNoArchive;
    ArrayList<HashMap<String,Object>> archiveItem;
    ArchiveAdapter archiveAdapter;
    String filePath = Environment.getExternalStorageDirectory() + "/Whisper/";
    File menu;
    File[] files;
    ArrayList<Integer> moods;

    @Override
    public void getArgs(Bundle var1) {

    }

    @Override
    public int setView() {
        return R.layout.aty_archive;
    }

    @Override
    public void initView() {
        moods = new ArrayList<>();
        archiveItem = new ArrayList<>();
        menu = new File(filePath);
        files = menu.listFiles();
        String content;
        String time;
        int mood;
        Boolean isFirstLine;
        Boolean isSecondLine;
        if(files.length == 0){tvNoArchive.setVisibility(View.VISIBLE);}
        for(int j = files.length-1;j >= 0; j--){
            isFirstLine = true;
            isSecondLine = false;
            mood = 0xffdddddd;
            time = "";
            content = "";
            try {
                FileInputStream in = new FileInputStream(files[j]);
                if (in != null) {
                    InputStreamReader inputreader = new InputStreamReader(in);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while ((line = buffreader.readLine()) != null) {
                        if(isFirstLine){
                            mood = Integer.parseInt(line);
                            moods.add(j,mood);
                            isFirstLine = false;
                            isSecondLine = true;
                        }
                        else if(isSecondLine){
                            time = line;
                            isSecondLine = false;
                        }
                        else {
                            content += line + "\n";
                        }
                    }
                    in.close();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("ItemMood",mood);
                    map.put("ItemTime",time);
                    map.put("ItemText", content);
                    archiveItem.add(map);
                }
            }
            catch (java.io.FileNotFoundException e)
            {
                Log.d("TestFile", "The File doesn't not exist.");
            }
            catch (IOException e)
            {
                Log.d("TestFile", e.getMessage());
            }
        }
        archiveAdapter = new ArchiveAdapter(this,archiveItem);
        lvArchive.setAdapter(archiveAdapter);
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
        headerRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,HistoryActivity.class);
                intent.putIntegerArrayListExtra("moods",moods);
                startActivity(intent);
            }
        });
    }
    @Subscribe
    public void onDeleteArchiveEvent(DeleteArchiveEvent event){
        int position = event.getPosition();
        files = menu.listFiles();
        files[files.length-position-1].delete();
        archiveItem.remove(position);
        archiveAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onReadArchiveEvent(ReadArchiveEvent event){
        int position = event.getPosition();
        String content = "";
        int mood = 0xffdddddd;
        Boolean isFirstLine = true;
        try {
            FileInputStream in = new FileInputStream(files[files.length-position-1]);
            if (in != null) {
                InputStreamReader inputreader = new InputStreamReader(in);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                //分行读取
                while ((line = buffreader.readLine()) != null) {
                    if (isFirstLine) {
                        mood = Integer.parseInt(line);
                        isFirstLine = false;
                    }
                    else{
                        content += line + "\n";
                    }
                }
                in.close();
            }
        }
        catch (java.io.FileNotFoundException e)
        {
            Log.d("TestFile", "The File doesn't not exist.");
        }
        catch (IOException e)
        {
            Log.d("TestFile", e.getMessage());
        }
        Intent intent = new Intent(activity, ReadArchiveActivity.class);
        intent.putExtra("content",content);
        intent.putExtra("mood",mood);
        startActivity(intent);
    }
}
