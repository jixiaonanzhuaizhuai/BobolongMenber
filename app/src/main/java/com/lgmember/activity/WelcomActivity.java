package com.lgmember.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lgmember.app.PersistentCookieStore;
import com.lgmember.business.ApkBusiness;
import com.lgmember.business.VersionBusiness;
import com.lgmember.business.login.GuestLoginBusiness;
import com.lgmember.business.login.IfGuestLoginBusiness;
import com.lgmember.business.person.MemberNameBusiness;
import com.lgmember.util.ActivityCollector;
import com.lgmember.util.Common;

import java.io.File;


public class WelcomActivity extends BaseActivity implements OnClickListener,MemberNameBusiness.MemberNameResulHandler,GuestLoginBusiness.GUestLoginResultHandler,IfGuestLoginBusiness.IfGUestLoginResultHandler {
	
	private Button loginBtn,regBtn,visitorBtn;
	private String LoginName;



	private String name;
	private int stateCode;
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		getMemberName();

	}

	@Override
	protected void onResume() {
		super.onResume();
		//判断cookie是否有效
		//getMemberName();
	}



	private void getMemberName() {
		MemberNameBusiness memberNameBusiness = new MemberNameBusiness(context);
		memberNameBusiness.setHandler(this);
		memberNameBusiness.getMemberName();
	}

	private void initView() {
		loginBtn = (Button) findViewById(R.id.loginBtn);
		regBtn = (Button) findViewById(R.id.regBtn);
		visitorBtn = (Button) findViewById(R.id.visitorBtn);
		loginBtn.setOnClickListener(this);
		regBtn.setOnClickListener(this);
		visitorBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginBtn:
			if (stateCode !=1 && !TextUtils.equals(LoginName,"游客") && sharedPreferences.getBoolean("AUTO_ISCHECK", false)){
				startIntent(MainActivity.class);
			}else {
				startIntent(LoginActivity.class);
			}
			break;
		case R.id.regBtn:
			startIntent(RegisterActivity.class);
			break;
		case R.id.visitorBtn:
			//清除cookie
			PersistentCookieStore cookieStore = new
					PersistentCookieStore(getApplicationContext());
			cookieStore.removeAll();
			Common.FLAG = true;

			guestLogin();
			//设置自动登录为false

			break;
		default:
			break;
		}
	}

	private void guestLogin() {
		GuestLoginBusiness guestLoginBusiness = new
				GuestLoginBusiness(context);
		//处理结果
		guestLoginBusiness.setHandler(this);
		guestLoginBusiness.guestLogin();
	}

	@Override
	public void onMemberNameSuccess(String name) {
		LoginName = name;
		if (sharedPreferences.getBoolean("AUTO_ISCHECK", false)) {
			startIntent(MainActivity.class);
		} else {
			setContentView(R.layout.welcom);
			initView();

			//}

		}
	}
	@Override
	public void onMemberNameError(int code) {
		stateCode = code;
		setContentView(R.layout.welcom);
		initView();
	}



	//重写返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			ActivityCollector.finishAll();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	@Override
	public void onGuestLoginSuccess() {

		//判断当前用户是否游客
		ifGuestLogin();
	}

	private void ifGuestLogin() {
		IfGuestLoginBusiness ifGuestLoginBusiness = new
				IfGuestLoginBusiness(context);
		//处理结果
		ifGuestLoginBusiness.setHandler(this);
		ifGuestLoginBusiness.ifGuestLogin();
	}

	@Override
	public void onIfGuestLoginSuccess(String s) {
		name = s;
		if (name.equals("游客")){
			startIntent(MainGuestActivity.class);
		}
	}
}
