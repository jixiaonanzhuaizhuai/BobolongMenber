package com.lgmember.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lgmember.activity.R;
import com.lgmember.activity.project.TagProjectMessageListActivity;
import com.lgmember.model.Tag;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/6/14.
 */

public class TagsListAdapter extends RecyclerView.Adapter<TagsListAdapter.ViewHolder> {

    private List<Tag> mTagsList ;
    static class ViewHolder extends  RecyclerView.ViewHolder{
        LinearLayout ll_tag_item;
        TextView tv_tag_name;
        public ViewHolder(View view){
            super(view);
            ll_tag_item = (LinearLayout)view.findViewById(R.id.ll_tag_item);
            tv_tag_name = (TextView)view.findViewById(R.id.tv_tag_name);
        }
    }

    public TagsListAdapter(List<Tag> tagList){
        mTagsList = tagList;

    }

    @Override
    public TagsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tags_recyclerview,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.ll_tag_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Tag tag = mTagsList.get(position);
                TagProjectMessageListActivity.actionStart(v.getContext(),tag.getId());

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(TagsListAdapter.ViewHolder holder, int position) {
        Tag tag = mTagsList.get(position);
        holder.tv_tag_name.setText(""+tag.getTag());
    }

    @Override
    public int getItemCount() {
        return mTagsList.size();
    }
}
