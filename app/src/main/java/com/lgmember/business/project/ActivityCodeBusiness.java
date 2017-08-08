package com.lgmember.business.project;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.SignImpl;
import com.lgmember.util.StringUtil;

import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class ActivityCodeBusiness {
    private String project_id;
    private Context context;
    private SignImpl signImpl;

    public ActivityCodeBusiness(Context context, String project_id) {
        super();
        this.context = context;
        this.project_id = project_id;
    }

    // 先验证参数的可发性，再登陆
    public void getProjectSign() {
        // 判断活动码是否有效
        signImpl = new SignImpl();
        signImpl.getProjectSign(project_id,handler,context);
    }

    // 先验证参数的可发性，再登陆
    public void getClubProjectSign() {
        // 判断活动码是否有效
        signImpl = new SignImpl();
        signImpl.getClubProjectSign(project_id,handler,context);
    }

    private ActivityCodeResulHandler handler;

    public interface ActivityCodeResulHandler extends HttpHandler {
        //当参数为空
        public void onArgumentEmpty(String s);

        public void onActivityCodeSuccess(String string);

    }
    public void setHandler(ActivityCodeResulHandler handler){
        this.handler = handler;
    }

    }