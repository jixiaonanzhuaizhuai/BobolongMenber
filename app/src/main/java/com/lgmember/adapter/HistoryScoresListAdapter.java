package com.lgmember.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lgmember.activity.R;
import com.lgmember.model.HistoryScores;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/2/14.
 */

public class HistoryScoresListAdapter extends BaseAdapter {

    private List<HistoryScores> historyScoresList;
    private Context context;
    private LayoutInflater layoutInflater;

    public HistoryScoresListAdapter(Context context, List<HistoryScores> historyScoresList){
        this.context = context;
        this.historyScoresList = historyScoresList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return historyScoresList.size();
    }

    //获得某一位置的数据
    @Override
    public Object getItem(int position) {
        return historyScoresList.get(position) ;
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
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_item_history_scores, null);
            TextView before_point = (TextView) view.findViewById(R.id.tv_before_point);
            TextView after_point = (TextView) view.findViewById(R.id.tv_after_point);
            TextView create_time = (TextView) view.findViewById(R.id.tv_create_time);
            TextView reason = (TextView) view.findViewById(R.id.tv_reason);
            TextView userName = (TextView) view.findViewById(R.id.tv_userName);
            //打包
            vh = new ViewHolder();
            vh.before_point = before_point;
            vh.after_point = after_point;
            vh.create_time = create_time;
            vh.reason = reason;
            vh.userName = userName;
            //上身
            view.setTag(vh);
        } else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }
        HistoryScores historyScores = historyScoresList.get(position);
        vh.before_point.setText("" + historyScores.getBefore_point());
        vh.after_point.setText("" + historyScores.getAfter_point());
        vh.create_time.setText("" + historyScores.getCreate_time());
        vh.userName.setText("" + historyScores.getUserName());
        vh.reason.setText("" + historyScores.getReason());
        return view;
    }

    private class ViewHolder {
        public TextView before_point;
        public TextView after_point;
        public TextView create_time;
        public TextView reason;
        public TextView userName;
}
}

