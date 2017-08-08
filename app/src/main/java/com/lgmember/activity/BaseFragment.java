package com.lgmember.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lgmember.util.ActivityCollector;

public class BaseFragment extends Fragment {

	protected Context context;

	public void showToast(String string ) {
		Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
	}
	public  void startIntent(Class clazz) {
		Intent intent = new Intent(getActivity(),clazz);
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

		//showToast(code + msg);
	}

	private void showLoginDialog(){
		final AlertDialog.Builder normalDialog =
				new AlertDialog.Builder(getActivity());
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
