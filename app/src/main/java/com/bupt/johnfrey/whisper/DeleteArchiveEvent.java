package com.bupt.johnfrey.whisper;

/**
 *  Created by zhangfengyang on 16/4/8
 */
public class DeleteArchiveEvent {
    int position;
    DeleteArchiveEvent(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }
}
