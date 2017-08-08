package com.lgmember.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgmember.activity.R;
import com.lgmember.model.Feedback;
import com.lgmember.model.Response;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Yanan_Wu on 2017/2/14.
 */

public class FeedbackListAdapter extends BaseExpandableListAdapter {

    List<Feedback> feedbackList;

    Context context;
    int selectParentItem = -1;
    int selectChildItem = -1;

    public FeedbackListAdapter(Context context, List<Feedback> feedbackList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.feedbackList = feedbackList;

    }

    public void setChildSelection(int groupPosition, int childPosition) {
        selectParentItem = groupPosition;
        selectChildItem = childPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return feedbackList.get(groupPosition).getResponse().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            //获取LayoutInflater
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //获取对应的布局
            view = layoutInflater.inflate(R.layout.list_item_feedback_response, null);
        }
        TextView tv_user_name = (TextView) view.findViewById(R.id.tv_userName);
        TextView tv_create_time = (TextView)view.findViewById(R.id.tv_create_time);
        TextView tv_response_content = (TextView)view.findViewById(R.id.tv_response_content);
        tv_user_name.setText(feedbackList.get(groupPosition).getResponse().get(childPosition).getUser_name());
        tv_create_time.setText(feedbackList.get(groupPosition).getResponse().get(childPosition).getCreate_time());
        tv_response_content.setText(feedbackList.get(groupPosition).getResponse().get(childPosition).getContent());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (feedbackList.get(groupPosition).getResponse() ==  null){
            return 0;
        }else {
            return feedbackList.get(groupPosition).getResponse().size();
        }

    }

    @Override
    public Object getGroup(int groupPosition) {
        return feedbackList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return feedbackList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_feedback, null);
        }
        TextView tv_feedback_content = (TextView) view.findViewById(R.id.tv_feedback_content);
        TextView tv_create_time = (TextView)view.findViewById(R.id.tv_create_time);
        tv_feedback_content.setText(feedbackList.get(groupPosition).getContent());
        tv_create_time.setText(feedbackList.get(groupPosition).getCreate_time());
        ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        if (isExpanded) {
            iv_icon.setBackgroundResource(R.mipmap.jiantoublow);
        } else {
            iv_icon.setBackgroundResource(R.mipmap.jiantouright);
        }
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}




