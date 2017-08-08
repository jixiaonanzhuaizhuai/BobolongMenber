package com.lgmember.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lgmember.activity.person.CertificationActivity;
import com.lgmember.activity.person.MessageCodeActivity;
import com.lgmember.business.login.IfNeedCaptBusiness;
import com.lgmember.business.login.ImgCptBusiness;
import com.lgmember.business.login.LoginBusiness;
import com.lgmember.util.Common;


public class LoginActivity extends BaseActivity implements OnClickListener, LoginBusiness.LoginResultHandler, ImgCptBusiness.ImgCptResultHandler, IfNeedCaptBusiness.IfNeedCaptResultHandler {

    private Button loginBtn;
    private TextView userEdt, pwdEdt, registerTxt, forgetPassTxt;
    private String loginName;
    private String loginPass;
    private String cpt = "";
    private EditText captEdt;
    private ImageView iv_captImg;
    private LinearLayout ll_capt;
    private CheckBox autoLogin;
    private SharedPreferences sharedPreferences;

    private boolean need_capt_def = false;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        Common.FLAG = true;
        setContentView(R.layout.activity_login);
        initView();
        ifNeedCapt();
    }

    //初始化控件
    private void initView() {

        autoLogin = (CheckBox) findViewById(R.id.cb_autoLogin);
        autoLogin.setChecked(sharedPreferences.getBoolean("AUTO_ISCHECK",false));

        ll_capt = (LinearLayout) findViewById(R.id.ll_capt);
        captEdt = (EditText) findViewById(R.id.edt_capt);
        iv_captImg = (ImageView) findViewById(R.id.iv_captImg);
        iv_captImg.setOnClickListener(this);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        userEdt = (TextView) findViewById(R.id.userEdt);
        pwdEdt = (TextView) findViewById(R.id.pwdEdt);
        registerTxt = (TextView) findViewById(R.id.registerTxt);
        forgetPassTxt = (TextView) findViewById(R.id.forgetPassTxt);
        loginBtn.setOnClickListener(this);
        registerTxt.setOnClickListener(this);
        forgetPassTxt.setOnClickListener(this);

        //默认记住用户名
        userEdt.setText(sharedPreferences.getString("userName", ""));
        if (sharedPreferences.getBoolean("AUTO_ISCHECK", false)){
            pwdEdt.setText(sharedPreferences.getString("password",null));
    }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                login();
                break;
            case R.id.registerTxt:
                startIntent(RegisterActivity.class);
                break;
            case R.id.forgetPassTxt:
                startIntent(MessageCodeActivity.class);
                break;
            case R.id.iv_captImg:
                getImgCpt();
                break;
            default:
                break;
        }
    }
    // Activity只用来1.获取参数、2.处理结果，中间具体业务放到business中去
    //activity只用来1.获取参数2.处理结果，中间业务放到business中去

    //是否需要验证码
    private void ifNeedCapt() {

        //先判断是否需要验证码
        IfNeedCaptBusiness ifNeedCaptBusiness = new IfNeedCaptBusiness(context);
        ifNeedCaptBusiness.setHandler(this);
        ifNeedCaptBusiness.getNeedCapt();
    }

    // 如果嫌方法太多，可以使用适配器模式，减少方法的覆写

    //获取图形验证码
    private void getImgCpt() {

        ImgCptBusiness imgCptBusiness = new ImgCptBusiness(context);
        //处理结果
        imgCptBusiness.setHandler(this);
        imgCptBusiness.getImgCpt();
    }

    //登录
    private void login() {

        loginName = getText(userEdt);
        loginPass = getText(pwdEdt);
        cpt = getText(captEdt);

        LoginBusiness loginBusiness = new
                LoginBusiness(context, loginName, loginPass, cpt, need_capt_def);
        //处理结果
        loginBusiness.setHandler(this);
        loginBusiness.Login();
    }

    /*
    * 登录请求结果的处理
    * */
    @Override
    public void onSuccess(int state, boolean need_capt) {
        if (state == 0) {
            rememberPwd();
            startIntent(MainActivity.class);
            finish();
        } else if (state == 1) {
            if (need_capt) {
                ll_capt.setVisibility(View.VISIBLE);
                getImgCpt();
                need_capt_def = need_capt;
            }
            showToast("用户名或密码错误！");
        } else if (state == 2) {
            if (need_capt) {
                ll_capt.setVisibility(View.VISIBLE);
                getImgCpt();
                need_capt_def = need_capt;
            }
            showToast("验证码错误");
        }else if(state == 3) {
            showToast("您的登录密码过于简单，请及时修改");
            startIntent(MainActivity.class);
        }else if (state == 4){
            rememberPwd();
            showDialog();
        }
    }

    private void rememberPwd() {
        editor = sharedPreferences.edit();
        editor.putString("userName", loginName);
        if (autoLogin.isChecked()) {//自动登录
            editor.putString("password", loginPass);
            editor.putBoolean("AUTO_ISCHECK", true);
        }else {
            editor.clear();
            editor.putBoolean("AUTO_ISCHECK",false);
        }
        editor.commit();
    }

    //弹出对话框
    public void showDialog(){
        //注册成功后的业务逻辑
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("自助实名认证");
        builder.setMessage("您已成功登录龙广会员，通过‘实名认证’即可享受龙广会员俱乐部的贴心服务。");
        builder.setPositiveButton("实名认证", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //完成业务逻辑
                startIntent(CertificationActivity.class);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                startIntent(MainActivity.class);
                finish();
            }
        });
        builder.show();

    }

    /*
    是否需要验证码请求结果的处理
    * */
    @Override
    public void onNeedCaptSuccess(boolean need_capt) {
        if (need_capt) {
            ll_capt.setVisibility(View.VISIBLE);
            getImgCpt();
            need_capt_def = need_capt;
        }
    }

    /*
    * 图像验证码请求结果的处理
    * */
    @Override
    public void onImgCptSuccess() {
        String path = Environment.getExternalStorageDirectory() + "/cpt.jpg";
        Bitmap bm = BitmapFactory.decodeFile(path);
        iv_captImg.setBackground(new BitmapDrawable(context.getResources(),bm));

    }

    @Override
    public void onImgCptFailed(String s) {
        showToast("加载网络图片失败");
    }

}
