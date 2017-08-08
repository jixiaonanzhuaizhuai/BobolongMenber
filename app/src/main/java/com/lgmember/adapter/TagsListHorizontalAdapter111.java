package com.lgmember.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lgmember.activity.R;
import com.lgmember.model.Tag;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/6/14.
 */

public class TagsListHorizontalAdapter111 extends RecyclerView.Adapter<TagsListHorizontalAdapter111.ViewHolder> {

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

    public TagsListHorizontalAdapter111(List<Tag> tagList){
        mTagsList = tagList;

    }

    @Override
    public TagsListHorizontalAdapter111.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tags_recyclerview_horizontal,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.ll_tag_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Tag tag = mTagsList.get(position);

                Toast.makeText(v.getContext(),tag.getTag(),Toast.LENGTH_LONG).show();
                /*TagProjectMessageListActivity.actionStart(v.getContext(),tag.getId());*/
              /*  Intent intent = new Intent(v.getContext(),TagProjectMessageListActivity.class);
                v.getContext().startActivity(intent);*/

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(TagsListHorizontalAdapter111.ViewHolder holder, int position) {
        Tag tag = mTagsList.get(position);
        holder.tv_tag_name.setText(""+tag.getTag());
    }

    @Override
    public int getItemCount() {
        return mTagsList.size();
    }
}
