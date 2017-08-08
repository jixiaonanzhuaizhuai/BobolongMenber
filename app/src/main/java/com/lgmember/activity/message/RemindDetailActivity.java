package com.lgmember.activity.message;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.business.card.GetCardBusiness;
import com.lgmember.business.message.UploadIfReadBusiness;
import com.lgmember.model.Remind;
import com.lgmember.view.TopBarView;

/**
 * Created by Yanan_Wu on 2017/2/14.
 */

public class RemindDetailActivity extends BaseActivity implements TopBarView.onTitleBarClickListener ,GetCardBusiness.GetCardResultHandler,UploadIfReadBusiness.UploadIfReadResultHandler{

    private TextView tv_content,tv_remind_stime;
    private TopBarView topBar;
    private Button btn_get;
    private Remind remind;
    private boolean if_read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_detail);
        init();
    }

    private void init() {
        String RemindMessageJson = getIntent().getStringExtra("remind");
        remind = new Gson().fromJson(RemindMessageJson,
                Remind.class);

        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);
        tv_remind_stime = (TextView)findViewById(R.id.tv_remind_stime);
        tv_content = (TextView)findViewById(R.id.tv_content);

        tv_content.setText(""+remind.getContent());
        tv_remind_stime.setText(""+remind.getRemind_time());
        if_read = remind.getUnread();

        if (if_read){
            //上传消息为已读状态
            uploadIfRead(1);
        }

        btn_get = (Button)findViewById(R.id.btn_get);
        int remind_type = remind.getRemind_type();
        if (remind_type == 1 || remind_type == 3 || remind_type == 5 || remind_type == 6){
            btn_get.setVisibility(View.VISIBLE);
            btn_get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //领取卡券
                    getCard();
                }
            });
        }
    }
    private void uploadIfRead(int unread) {
        UploadIfReadBusiness uploadIfReadBusiness = new UploadIfReadBusiness(context, remind.getId(),unread);
        uploadIfReadBusiness.setHandler(this);
        uploadIfReadBusiness.ifRead();
    }
    private void getCard() {
        GetCardBusiness getCardBusiness = new GetCardBusiness(context, remind.getId());
        getCardBusiness.setHandler(this);
        getCardBusiness.getRemindCard();
    }
    @Override
    public void onBackClick() {
        setResult(99);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(99);
        finish();
    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onGetCardSuccess(int data) {
        if(data == 0){
            showToast("领取成功");
            startIntent(MyMessageActivity.class);
        }
        if(data == 1){
            showToast("已经领取过");
        }
        if(data == 2){
            showToast("已过期");
        }
    }

}
