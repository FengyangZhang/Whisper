package com.bupt.johnfrey.whisper;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

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
    @Bind(R.id.lv_archive)
    ListView lvArchive;
    ArrayList<HashMap<String,Object>> archiveItem;
    ArchiveAdapter archiveAdapter;
    String filePath = Environment.getExternalStorageDirectory() + "/Whisper/";
    String fileName = "whisper_0.txt";

    @Override
    public void getArgs(Bundle var1) {

    }

    @Override
    public int setView() {
        return R.layout.aty_archive;
    }

    @Override
    public void initView() {
        archiveItem = new ArrayList<>();
        File file = new File(filePath+fileName);
        int i = 0;
        String content;
        String time;
        Boolean isFirstLine;
        while(file.exists()){
            isFirstLine = true;
            time="";
            content = "";
            try {
                FileInputStream in = new FileInputStream(file);
                if (in != null) {
                    InputStreamReader inputreader = new InputStreamReader(in);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while ((line = buffreader.readLine()) != null) {
                        if(isFirstLine){
                            time = line;
                            isFirstLine = false;
                        }
                        else {
                            content += line + "\n";
                        }
                    }
                    in.close();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("ItemTime",time);
                    map.put("ItemText", content);
                    archiveItem.add(map);
                    i++;
                    fileName = "whisper_"+i+".txt";
                    file = new File(filePath+fileName);
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
        lvArchive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
