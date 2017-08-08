package com.lgmember.adapter;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgmember.activity.R;
import com.lgmember.model.Message;
import com.lgmember.model.Remind;
import com.lgmember.util.StringUtil;
import com.lgmember.view.BadgeView;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/2/14.
 */

public class MessageListAdapter extends BaseAdapter {

    private List<Message> messageList;
    private Context context;
    private LayoutInflater layoutInflater;
    private BadgeView badgeView;

    public MessageListAdapter(Context context, List<Message> messageList){
        this.context = context;
        this.messageList = messageList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    //获得某一位置的数据
    @Override
    public Object getItem(int position) {
        return messageList.get(position) ;
    }

    //获得唯一标识
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder vh;
        badgeView = new BadgeView(context);

        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_item_message, null);
            TextView tv_message_title = (TextView) view.findViewById(R.id.title);
            TextView tv_message_content = (TextView) view.findViewById(R.id.content);
            badgeView.setTargetView(tv_message_content);
            badgeView.setBadgeCount(0);

            TextView tv_message_date = (TextView) view.findViewById(R.id.date);
            //打包
            vh = new ViewHolder();
            vh.tv_message_title = tv_message_title;
            vh.tv_message_content = tv_message_content;
            vh.tv_message_date = tv_message_date;
            //上身
            view.setTag(vh);
        } else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }
            Message message = messageList.get(position);
            vh.tv_message_title.setText(""+message.getTitle());
            vh.tv_message_content.setText(Html.fromHtml(message.getContent()).toString());
            vh.tv_message_date.setText(""+message.getCreate_time());
            if (message.getState() == 7){
                badgeView.setBadgeCount(1);
            }else {
                badgeView.setBadgeCount(0);
            }


        return view;
    }

    private class ViewHolder {
        public TextView tv_message_title;
        public TextView tv_message_content;
        public TextView tv_message_date;

    }
}

