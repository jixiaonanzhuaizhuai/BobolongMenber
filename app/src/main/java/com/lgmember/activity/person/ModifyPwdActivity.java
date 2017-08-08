package com.lgmember.activity.person;

import android.os.Bundle;
import android.widget.EditText;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.MainActivity;
import com.lgmember.activity.R;
import com.lgmember.business.person.ModifyPwdBusiness;
import com.lgmember.view.TopBarView;

/**
 * Created by Yanan_Wu on 2017/3/1.
 */

public class ModifyPwdActivity extends BaseActivity implements ModifyPwdBusiness.ModifyResultHandler,TopBarView.onTitleBarClickListener {

    private EditText oldPwdEdt,newPwdEdt,confirmNewPwdEdt;
    private String oldPwdTxt,newPwdTxt,confirmNewPwdTxt;
    private TopBarView topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifypwd);
        initView();
    }
    public void initView(){
        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);
        oldPwdEdt = (EditText)findViewById(R.id.oldPwdEdt);
        newPwdEdt =(EditText)findViewById(R.id.newPwdEdt);
        confirmNewPwdEdt = (EditText)findViewById(R.id.confirmNewPwdEdt);


    }
    public void modifyPwd(){

        oldPwdTxt = getText(oldPwdEdt);
        newPwdTxt = getText(newPwdEdt);
        confirmNewPwdTxt = getText(confirmNewPwdEdt);

        ModifyPwdBusiness modifyPwdBusiness = new ModifyPwdBusiness(context,oldPwdTxt,newPwdTxt,confirmNewPwdTxt);
        //处理结果
        modifyPwdBusiness.setHandler(this);
        modifyPwdBusiness.modifyPwd();
    }

    @Override
    public void onSuccess(String s) {
        showToast(s);
        startIntent(MainActivity.class);
    }
    @Override
    public void onPwdNoEqual(String string) {
        showToast(string);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onRightClick() {
        modifyPwd();
    }
}
