package com.lgmember.business.score;

import android.content.Context;
import android.text.TextUtils;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.ExchangeImpl;
import com.lgmember.impl.LoginImpl;
import com.lgmember.model.Gift;
import com.lgmember.util.StringUtil;

import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class ExchangeGiftInfoBusiness {
    private int gift_id;
    private Context context;
    private ExchangeImpl exchangeImpl;

    public ExchangeGiftInfoBusiness(Context context, int gift_id) {
        super();
        this.context = context;
        this.gift_id = gift_id;
    }

    // 先验证参数的可发性，再登陆
    public void getGiftInfo() {

        exchangeImpl = new ExchangeImpl();
        exchangeImpl.getGiftInfo(gift_id,handler,context);
    }

    private ExchangeGiftInfoHandler handler;
    public interface ExchangeGiftInfoHandler extends HttpHandler {
        public void onSuccess(Gift gift);
    }


    public void setHandler(ExchangeGiftInfoHandler handler){
        this.handler = handler;
    }

    }