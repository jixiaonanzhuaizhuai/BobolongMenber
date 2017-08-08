package com.lgmember.activity.setting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.business.VersionBusiness;
import com.lgmember.util.Common;
import com.lgmember.util.StringUtil;
import com.lgmember.view.TopBarView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yanan_Wu on 2017/5/27.
 */

public class AboutUsActivity extends BaseActivity implements TopBarView.onTitleBarClickListener,View.OnClickListener,VersionBusiness.VersionResulHandler{

    private TopBarView topbar;
    private RelativeLayout rl_version_update;
    private TextView tv_version_des;

    private String currVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        init();
    }

    private void init() {
        topbar = (TopBarView)findViewById(R.id.topbar) ;
        topbar.setClickListener(this);
        rl_version_update = (RelativeLayout)findViewById(R.id.rl_version_update);
        tv_version_des = (TextView)findViewById(R.id.tv_version_des);
        rl_version_update.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getServiceVersion();
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_version_update:
                showUpdateDialog();
                break;
            default:
                break;
        }
    }

    private void getServiceVersion() {
        VersionBusiness versionBusiness = new VersionBusiness(context);
        versionBusiness.setHandler(this);
        versionBusiness.getVersion();
    }

    @Override
    public void onSuccess(String s) {
        //获取手机版本号
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pInfo = pm.getPackageInfo(getPackageName(),0);
            currVersion = String.valueOf(pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (!s.equals(currVersion)){
            tv_version_des.setText("版本更新");
            rl_version_update.setEnabled(true);
        }else {
            tv_version_des.setText("已经是最新版本了");
            rl_version_update.setEnabled(false);
        }
    }

    public void showUpdateDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("软件更新");
        builder.setMessage("有新版本,建议更新!");
        // 更新
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(Common.URL_APK);
                intent.setData(content_url);
                startActivity(intent);
                dialog.dismiss();
            }


        });

        // 稍后更新
        builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }
}
