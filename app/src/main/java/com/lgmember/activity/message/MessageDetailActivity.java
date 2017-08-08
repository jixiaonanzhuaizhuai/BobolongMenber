package com.lgmember.activity.message;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.business.message.UpdateMessageStateBusiness;
import com.lgmember.model.Message;
import com.lgmember.util.Common;
import com.lgmember.util.StringUtil;
import com.lgmember.view.TopBarView;

/**
 * Created by Yanan_Wu on 2017/2/14.
 */

public class MessageDetailActivity extends BaseActivity implements UpdateMessageStateBusiness.UpdateMessageStateResultHandler,TopBarView.onTitleBarClickListener {

    private TextView tv_title,tv_create_time,
            tv_content,tv_hyperlink,tv_author;
    private TopBarView topBar;
    private ImageView iv_img;
    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        init();
    }

    private void init() {

        String memberMessageJson = getIntent().getStringExtra("memberMsg");
        message = new Gson().fromJson(memberMessageJson,
                Message.class);
        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);
        iv_img = (ImageView)findViewById(R.id.iv_img);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_create_time = (TextView)findViewById(R.id.tv_create_time);
        tv_content = (TextView)findViewById(R.id.tv_content);
        tv_hyperlink = (TextView)findViewById(R.id.tv_hyperlink);
        tv_author = (TextView)findViewById(R.id.tv_author);

        String picture = Common.URL_IMG_BASE + message.getPicture();
        StringUtil.setNetworkBitmap(context,picture,iv_img);

        tv_title.setText(message.getTitle()+"");
        tv_create_time.setText(message.getCreate_time()+"");
        tv_content.setText(Html.fromHtml(message.getContent()+""));
        tv_author.setText(message.get_type()+"");
        tv_hyperlink.setText(message.getHyperlink()+"");
        tv_hyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.baidu.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        if (message.getState() == 7){
            //上传消息为已读状态
            updateMessageState(message.getMessage_id(),8);
        }
    }

    private void updateMessageState(int message_id, int state) {
        UpdateMessageStateBusiness messageDetailBusiness = new UpdateMessageStateBusiness(context, message_id,state);
        messageDetailBusiness.setHandler(this);
        messageDetailBusiness.updateMsgState();
    }
    @Override
    public void onBackClick() {
        setResult(100);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(100);
        finish();
    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onUpdateMessageStateSuccess() {

    }
}
