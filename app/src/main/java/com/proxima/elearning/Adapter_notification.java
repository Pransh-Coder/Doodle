package com.proxima.elearning;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class Adapter_notification extends BaseAdapter {
    Context context;
    Activity activity;
    ListView listView;
    LayoutInflater layoutInflater;
    ArrayList<notification_data> notificationData;
    public Adapter_notification(Activity activity, Context context, ArrayList<notification_data> notificationData, SwipeMenuListView listView) {
        this.activity=activity;
        this.context=context;
        this.notificationData=notificationData;
        this.listView=listView;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notificationData.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView not_title,not_date;
        convertView=layoutInflater.inflate(R.layout.notification_layout,null);
        not_title=convertView.findViewById(R.id.not_title);
        not_date=convertView.findViewById(R.id.not_date);
        not_title.setText(notificationData.get(position).getTitle());
        not_date.setText(notificationData.get(position).getCreated_at());

        return convertView;

    }
}
