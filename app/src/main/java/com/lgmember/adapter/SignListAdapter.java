package com.lgmember.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lgmember.activity.R;
import com.lgmember.model.Message;
import com.lgmember.model.SignResult;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/2/14.
 */

public class SignListAdapter extends BaseAdapter {

    private List<SignResult> signResultsList;
    private Context context;
    private LayoutInflater layoutInflater;

    public SignListAdapter(Context context, List<SignResult> signResultsList){
        this.context = context;
        this.signResultsList = signResultsList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return signResultsList.size();
    }

    //获得某一位置的数据
    @Override
    public Object getItem(int position) {
        return signResultsList.get(position) ;
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
            view = layoutInflater.inflate(R.layout.list_item_sign, null);

            TextView dep_name = (TextView) view.findViewById(R.id.txt_dep_name);
            TextView program_name = (TextView) view.findViewById(R.id.txt_program_name);
            TextView create_time = (TextView) view.findViewById(R.id.txt_create_time);
            TextView result_time = (TextView) view.findViewById(R.id.txt_result_time);
            TextView state = (TextView) view.findViewById(R.id.txt_state);



            //打包
            vh = new ViewHolder();
            vh.dep_name = dep_name;
            vh.program_name = program_name;
            vh.create_time = create_time;
            vh.result_time = result_time;
            vh.state = state;
            //上身
            view.setTag(vh);
        } else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }
        SignResult signResult = signResultsList.get(position);
        vh.dep_name.setText(signResult.getDep_name());
        vh.program_name.setText(signResult.getProgram_name());
        vh.create_time.setText(signResult.getCreate_time());
        vh.result_time.setText(signResult.getResult_time());

        String stateStr = "";
        int stateInt = signResult.getState();

        if (stateInt == 0){
            stateStr = "等待识别";
        }else if (stateInt == 1){
            stateStr = "识别中";
        }else if (stateInt == 2){
            stateStr = "签到成功";
        }

        vh.state.setText(stateStr);
        return view;
    }

    private class ViewHolder {
        public TextView dep_name;
        public TextView program_name;
        public TextView create_time;
        public TextView result_time;
        public TextView state;



    }
}

