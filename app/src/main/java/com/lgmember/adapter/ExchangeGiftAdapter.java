package com.lgmember.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgmember.activity.R;
import com.lgmember.activity.score.ExchangeAllActivity;
import com.lgmember.activity.score.ExchangeGiftInfoActivity;
import com.lgmember.model.Gift;
import com.lgmember.model.Message;
import com.lgmember.util.Common;
import com.lgmember.util.StringUtil;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/2/14.
 */

public class ExchangeGiftAdapter extends BaseAdapter {

    private List<Gift> giftsList;
    private Context context;
    private LayoutInflater layoutInflater;

    public ExchangeGiftAdapter(Context context, List<Gift> giftsList){
        this.context = context;
        this.giftsList = giftsList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return giftsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder vh;
        if (convertView == null){
            view = layoutInflater.inflate(R.layout.grid_item,null);
            final ImageView giftImg = (ImageView)view.findViewById(R.id.iv_gift_img);
            TextView giftName = (TextView)view.findViewById(R.id.tv_gift_name);
            //打包
            vh = new ViewHolder();
            vh.giftImg = giftImg;
            vh.giftName = giftName;
            //上身
            view.setTag(vh);
        }else {
            view = convertView;
            vh = (ViewHolder)view.getTag();
        }
        final Gift gift = giftsList.get(position);
        String imgPath = Common.URL_IMG_BASE+gift.getPicture();
        StringUtil.setNetworkBitmap(context,imgPath,vh.giftImg);

        vh.giftName.setText(gift.getName());
        /*vh.giftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ExchangeGiftInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("gift_id",gift.getId());
                bundle.putString("gift_img",result);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/
        return view;
    }

     private class ViewHolder{
        public ImageView giftImg;
        public TextView giftName;
}
}

