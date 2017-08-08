package com.lgmember.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lgmember.activity.R;
import com.lgmember.model.Card;
import com.lgmember.model.HistoryScores;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/2/14.
 */

public class CardListAdapter extends BaseAdapter implements View.OnClickListener{

    private List<Card> cardList;
    private Context context;
    private int flag;
    private LayoutInflater layoutInflater;
    private Callback mCallback;



    /*
    *
    * 自定义接口，用于回调按钮点击事件到Activity*/

    public interface Callback{
        public void click(View v);
    }

    public CardListAdapter(Context context, List<Card> cardList,int flag,Callback callback){
        this.context = context;
        this.cardList = cardList;
        this.flag = flag;
        this.layoutInflater = LayoutInflater.from(context);
        this.mCallback = callback;
    }

    @Override
    public int getCount() {
        return cardList.size();
    }

    //获得某一位置的数据
    @Override
    public Object getItem(int position) {
        return cardList.get(position) ;
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
            view = layoutInflater.inflate(R.layout.activity_card_list_item, null);

            TextView name = (TextView) view.findViewById(R.id.tv_name);
            TextView des = (TextView) view.findViewById(R.id.tv_des);
            TextView end_time = (TextView) view.findViewById(R.id.tv_end_time);
            TextView type_name = (TextView)view.findViewById(R.id.tv_type_name) ;
           // Button card_state = (Button)view.findViewById(R.id.btn_card_state);

            //打包
            vh = new ViewHolder();
            vh.name = name;
            vh.des = des;
            vh.end_time = end_time;
            vh.type_name = type_name;
          //  vh.card_state = card_state;

            //上身
            view.setTag(vh);
        } else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }
        Card card = cardList.get(position);
        vh.name.setText("" + card.getName());
        vh.des.setText("" + card.getDescription());
        vh.end_time.setText("" + card.getEnd_time());
        vh.type_name.setText(""+card.getType_name());
        /*if (flag == 0){
            vh.card_state.setText("领取");
        }else if(flag == 1){
            vh.card_state.setText("兑换");
        }else {
            vh.card_state.setText("已核销");
            vh.card_state.setEnabled(false);
        }
        vh.card_state.setOnClickListener(this);
        vh.card_state.setTag(position);*/

        return view;
    }

    @Override
    public void onClick(View v) {
        mCallback.click(v);

    }

    private class ViewHolder {
        public TextView name;
        public TextView des;
        public TextView end_time;
        private TextView type_name;
       // private Button card_state;
}
}

