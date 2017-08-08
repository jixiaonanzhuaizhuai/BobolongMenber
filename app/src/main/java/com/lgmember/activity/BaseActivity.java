package com.lgmember.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lgmember.activity.score.ExchangeGiftDetailActivity;
import com.lgmember.util.ActivityCollector;
import com.lgmember.util.StringUtil;

public class BaseActivity extends AppCompatActivity {

	protected Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		Log.d("-----BaseActivity---",getClass().getSimpleName());
		ActivityCollector.addActivity(this);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null){
			actionBar.hide();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}

	public void showToast(String string ) {
		Toast.makeText(BaseActivity.this, string, Toast.LENGTH_SHORT).show();
	}

	public  void startIntent(Class clazz) {
		Intent intent = new Intent(BaseActivity.this,clazz);
		startActivity(intent);
	}
	public String getText(TextView v) {
		return v.getText().toString().trim();
	}


	public void onArgumentEmpty(String string) {
		showToast(string);

	}

	public void onArgumentFormatError(String string) {
		showToast(string);
	}

	public void onError(int code) {
		if (code == 1){
			showLoginDialog();
		}
	}

	public void onNetworkDisconnect() {
		showToast(context.getString(R.string.http_network_disconnect));
	}

	public void onFailed(int code, String msg) {

		//showToast(code + msg+context.getString(R.string.server_error));
	}

	private void showLoginDialog(){
		final AlertDialog.Builder normalDialog =
				new AlertDialog.Builder(BaseActivity.this);
		normalDialog.setTitle("提示");
		normalDialog.setMessage("需要登录!");
		normalDialog.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//...To-do
						startIntent(LoginActivity.class);
					}
				});
		normalDialog.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//...To-do
					}
				});
		// 显示
		normalDialog.show();
	}




}
