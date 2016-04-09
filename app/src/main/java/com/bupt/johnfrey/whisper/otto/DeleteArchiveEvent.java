package com.bupt.johnfrey.whisper.otto;

/**
 *  Created by zhangfengyang on 16/4/8
 */
public class DeleteArchiveEvent {
    int position;
    public DeleteArchiveEvent(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }
}
