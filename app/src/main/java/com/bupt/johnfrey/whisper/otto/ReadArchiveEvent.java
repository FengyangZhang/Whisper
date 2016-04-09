package com.bupt.johnfrey.whisper.otto;

/**
 *  Created by zhangfengyang on 16/4/9
 */
public class ReadArchiveEvent {
    int position;
    public ReadArchiveEvent(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }
}
