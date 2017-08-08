package com.lgmember.business.score;

import android.content.Context;
import android.text.TextUtils;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.ExchangeImpl;
import com.lgmember.impl.LoginImpl;
import com.lgmember.util.StringUtil;

import org.json.JSONObject;

/* * @param gift_id  礼物id
 * @param name  收货人名字
 * @param addr  收货地址
 * @param mobile   电话
 * @param capt  验证码
 * @param sms_capt_token  验证码token*/


public class ExchangeGiftBusiness {
    private int gift_id;
    private String name,addr,mobile,capt,sms_capt_token;
    private Context context;
    private ExchangeImpl exchangeImpl;

    public ExchangeGiftBusiness(Context context, int gift_id,String name,String addr, String mobile,
                                String capt,String sms_capt_token) {
        super();
        this.context = context;
        this.gift_id = gift_id;
        this.name = name;
        this.addr = addr;
        this.mobile = mobile;
        this.capt = capt;
        this.sms_capt_token = sms_capt_token;
    }

    // 先验证参数的可发性，再登陆
    public void exchangeGift() {
        // 验证参数是否为空
        if (TextUtils.isEmpty(capt)) {
            if (handler != null) {
                handler.onArgumentSmsCodeEmpty();
            }
            return;
        }
        // 如果是手机号
        if (!StringUtil.isPhone(mobile)) {
            if (handler != null) {
                handler.onArgumentMobileFormatError();
            }
            return;
        }
        // TODO 可能还要验证密码
        // 登陆
        exchangeImpl = new ExchangeImpl();
        exchangeImpl.exchangeGift(gift_id,name,addr,mobile,capt,sms_capt_token,handler,context);
    }

    private ExchangeGiftHandler handler;

    public interface ExchangeGiftHandler extends HttpHandler {
        //当参数为空
        public void onArgumentSmsCodeEmpty();

        //当参数不合法时
        public void onArgumentMobileFormatError();
        //成功
        public void onExchangeGiftSuccess(JSONObject jsonObject);
    }
    public void setHandler(ExchangeGiftHandler handler){
        this.handler = handler;
    }

    }