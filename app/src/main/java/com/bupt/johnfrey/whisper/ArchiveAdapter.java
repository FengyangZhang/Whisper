package com.bupt.johnfrey.whisper;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Created by zhangfengyang on 16/4/8
 */
public class ArchiveAdapter extends BaseAdapter {
    ArrayList<HashMap<String,Object>> archiveItem;
    Context context;
    LayoutInflater mInflater;
    String filePath = Environment.getExternalStorageDirectory() + "/Whisper/";

    public ArchiveAdapter(Context context, ArrayList<HashMap<String,Object>> archiveItem){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.archiveItem = archiveItem;
    }
    @Override
    public int getCount() {
        return archiveItem.size();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_archive, null);
            holder = new ViewHolder();
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.bt = (Button) convertView.findViewById(R.id.button); // to ItemButton
            convertView.setTag(holder); //绑定ViewHolder对象
        }
        else {
            holder = (ViewHolder) convertView.getTag(); //取出ViewHolder对象
        }

        holder.time.setText(archiveItem.get(position).get("ItemTime").toString());
        holder.text.setText(archiveItem.get(position).get("ItemText").toString());
        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    OttoManager.getInstance().post(new DeleteArchiveEvent(position));
            }
        });

        return convertView;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public final class ViewHolder {
        public TextView time;
        public TextView text;
        public Button bt;
    }

}
