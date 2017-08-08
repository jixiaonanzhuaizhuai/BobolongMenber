package com.lgmember.business.person;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.LoginImpl;
import com.lgmember.impl.ModifyPwdImpl;
import com.lgmember.util.StringUtil;

import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class ModifyPwdBusiness {
    private String oldPwdTxt,newPwdTxt,confirmNewTxt;
    private Context context;
    private ModifyPwdImpl modifyPwdImpl;

    public ModifyPwdBusiness(Context context, String oldPwdTxt, String newPwdTxt,String confirmNewTxt) {
        super();
        this.context = context;
        this.oldPwdTxt = oldPwdTxt;
        this.newPwdTxt = newPwdTxt;
        this.confirmNewTxt = confirmNewTxt;
    }

    // 先验证参数的可发性，再登陆
    public void modifyPwd() {
        // 验证参数是否为空
        if (TextUtils.isEmpty(oldPwdTxt) || TextUtils.isEmpty(newPwdTxt)||TextUtils.isEmpty(confirmNewTxt)) {
            if (handler != null) {
                handler.onArgumentEmpty("参数不能为空");
            }
            return;
        }
        // 如果是密码是不是8-20位
        if (!StringUtil.isPassword(newPwdTxt)) {
            if (handler != null) {
                handler.onArgumentFormatError("输入的新密码不是8-20位");
            }
            return;
        }
        //判断两次密码输入是否一致
        if (!StringUtil.isNewPwdEquallyConfirmPwd(newPwdTxt,confirmNewTxt)){
            if (handler != null){
                handler.onPwdNoEqual("输入的两次新密码不一致");
            }
        }else {
            modifyPwdImpl = new ModifyPwdImpl();
            modifyPwdImpl.modifyPwd(oldPwdTxt,newPwdTxt,handler,context);
        }
    }

    private ModifyResultHandler handler;

    public interface ModifyResultHandler extends HttpHandler {
        //当参数为空
        public void onArgumentEmpty(String string);

        //当参数不合法时
        public void onArgumentFormatError(String string);

        //两次密码不一致
        public void onPwdNoEqual(String string);
        public void onSuccess(String s);
    }
    public void setHandler(ModifyResultHandler handler){
        this.handler = handler;
    }

    }