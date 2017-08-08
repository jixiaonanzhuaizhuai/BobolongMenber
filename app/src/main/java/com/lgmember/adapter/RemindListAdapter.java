package com.lgmember.adapter;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lgmember.activity.R;
import com.lgmember.model.Remind;
import com.lgmember.view.BadgeView;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/2/14.
 */

public class RemindListAdapter extends BaseAdapter {

    private List<Remind> remindsList;
    private Context context;
    private LayoutInflater layoutInflater;

    public RemindListAdapter(Context context, List<Remind> remindsList){
        this.context = context;
        this.remindsList = remindsList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return remindsList.size();
    }

    //获得某一位置的数据
    @Override
    public Object getItem(int position) {
        return remindsList.get(position) ;
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
        Remind remind = remindsList.get(position);
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_item_remind, null);
             TextView tv_remind_content = (TextView) view
                     .findViewById(R.id.content);
            if (remind.getUnread()){
                BadgeView badgeView = new BadgeView(context);
                badgeView.setTargetView(tv_remind_content);
                badgeView.setBadgeCount(1);
            }
            TextView tv_remind_date = (TextView) view.findViewById(R.id.date);
            //打包
            vh = new ViewHolder();
            vh.tv_remind_content = tv_remind_content;
            vh.tv_remind_date = tv_remind_date;
            //上身
            view.setTag(vh);
        } else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }

        vh.tv_remind_content.setText(Html.fromHtml(remind.getContent()).toString());
        vh.tv_remind_date.setText(""+remind.getRemind_time());

        return view;
    }

    private class ViewHolder {
        public TextView tv_remind_content;
        public TextView tv_remind_date;

    }
}

