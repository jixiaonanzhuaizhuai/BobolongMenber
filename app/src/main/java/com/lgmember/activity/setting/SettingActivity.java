package com.lgmember.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.igexin.sdk.PushManager;
import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.LoginActivity;
import com.lgmember.activity.R;
import com.lgmember.activity.person.ModifyPwdActivity;
import com.lgmember.activity.person.PersonalAllActivity;
import com.lgmember.app.PersistentCookieStore;
import com.lgmember.service.DemoIntentService;
import com.lgmember.service.DemoPushService;
import com.lgmember.util.Common;
import com.lgmember.util.GlideCacheUtil;
import com.lgmember.view.TopBarView;


/**
 * Created by Yanan_Wu on 2016/12/19.
 */

public class SettingActivity extends BaseActivity implements TopBarView.onTitleBarClickListener,CompoundButton.OnCheckedChangeListener,View.OnClickListener {


    private TopBarView topBar;
    private ToggleButton t_btn_tuisong, t_btn_sign;
    private TextView tv_cache_size;
    private String cacheSize;
    private SharedPreferences sp,sharedPreferences;
    private RelativeLayout rl_clear_cache,rl_about_us,rl_modifyPwd,rl_exit_login;
    private boolean if_tuisong,if_auto_sign;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting1);
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
        init();
    }

    private void init() {
        sp = getSharedPreferences(Common.SP_NAME, MODE_PRIVATE);
        if_tuisong = sp.getBoolean(Common.SP_IF_TUISONG,true);
        if_auto_sign = sp.getBoolean(Common.SP_IF_RECORDER,false);

        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);

        rl_modifyPwd = (RelativeLayout)findViewById(R.id.rl_modifyPwd);
        rl_exit_login = (RelativeLayout)findViewById(R.id.rl_exit_login);
        rl_exit_login.setOnClickListener(this);
        rl_modifyPwd.setOnClickListener(this);

        rl_clear_cache = (RelativeLayout)findViewById(R.id.rl_clear_cache);
        rl_clear_cache.setOnClickListener(this);

        rl_about_us = (RelativeLayout)findViewById(R.id.rl_about_us);
        rl_about_us.setOnClickListener(this);

        topBar = (TopBarView) findViewById(R.id.topbar);
        topBar.setClickListener(this);
        t_btn_tuisong = (ToggleButton) findViewById(R.id.t_btn_tuisong);
        t_btn_sign = (ToggleButton) findViewById(R.id.t_btn_sign);
        t_btn_tuisong.setOnCheckedChangeListener(this);
        t_btn_sign.setOnCheckedChangeListener(this);
        t_btn_tuisong.setChecked(if_tuisong);
        t_btn_sign.setChecked(if_auto_sign);

        tv_cache_size = (TextView)findViewById(R.id.tv_cache_size);
        cacheSize = GlideCacheUtil.getInstance().getCacheSize(getApplicationContext());
        tv_cache_size.setText(cacheSize);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.t_btn_tuisong:
                sp.edit().putBoolean(Common.SP_IF_TUISONG,isChecked).commit();
                break;
            case R.id.t_btn_sign:
                sp.edit().putBoolean(Common.SP_IF_RECORDER,isChecked).commit();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_clear_cache:
                if (cacheSize.equals("0.0Byte")){
                    showToast("当前缓存为0Byte,无需再清除");
                }else{
                    GlideCacheUtil.getInstance()
                            .clearImageAllCache(getApplicationContext());
                    showToast("正在清除图片缓存,请稍等");
                    tv_cache_size.setText(""+GlideCacheUtil.getInstance().getCacheSize(getApplicationContext()));
                }
                break;
            case R.id.rl_about_us:
                startIntent(AboutUsActivity.class);
                break;
            case R.id.rl_modifyPwd:
                startIntent(ModifyPwdActivity.class);
                break;
            case R.id.rl_exit_login:
                //清除cookie
                PersistentCookieStore cookieStore = new
                        PersistentCookieStore(getApplicationContext());
                cookieStore.removeAll();
                Common.FLAG = false;
                //设置自动登录为false
                sharedPreferences =
                        this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("AUTO_ISCHECK",false);
                editor.commit();
                //关闭所有的窗口，跳转到登录界面
                Intent intent = new
                        Intent(SettingActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}

