package com.lgmember.business.person;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.CertificationImpl;
import com.lgmember.impl.SignImpl;
import com.lgmember.model.Certification;
import com.lgmember.util.StringUtil;

import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class CertificationBusiness {
    private Certification certification;
    private Context context;
    private CertificationImpl certificationImpl;

    public CertificationBusiness(Context context, Certification certification) {
        super();
        this.context = context;
        this.certification = certification;
    }

    // 先验证参数的可发性，再登陆
    public void certificationMsg() {
        // 验证参数是否为空
        if (TextUtils.isEmpty(certification.getName())) {
            if (handler != null) {
                handler.onArgumentEmpty("姓名不能为空");
            }
            return;
        }
        if (!StringUtil.userCardCheck(certification.getIdno())) {
            if (handler != null) {
                handler.onArgumentFormatError("身份证号格式不正确");
            }
            return;
        }


        // 判断活动码是否有效
        certificationImpl = new CertificationImpl();
        certificationImpl.certificationMsg(certification,handler,context);
    }

    private CertificationResulHandler handler;

    public interface CertificationResulHandler extends HttpHandler {
        //当参数为空
        public void onArgumentEmpty(String string);

        //当参数不合法时
        public void onArgumentFormatError(String string);

        public void onSuccess();

    }
    public void setHandler(CertificationResulHandler handler){
        this.handler = handler;
    }

    }