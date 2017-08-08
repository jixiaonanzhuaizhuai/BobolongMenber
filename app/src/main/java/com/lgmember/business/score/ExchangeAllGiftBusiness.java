package com.lgmember.business.score;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.ExchangeGiftResultBean;
import com.lgmember.impl.ExchangeImpl;
import com.lgmember.impl.ScoresImpl;

import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class ExchangeAllGiftBusiness {

    private int pageNo;
    private int pageSize;
    private Context context;
    private ExchangeImpl exchangeImpl;

    public ExchangeAllGiftBusiness(Context context, int pageNo, int pageSize) {
        super();
        this.context = context;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    // 先验证参数的可发性，再登陆
    public void getAllGift() {
        // TODO 可能还要验证密码
        // 登陆
        exchangeImpl = new ExchangeImpl();
        exchangeImpl.getAllExchangeGift(pageNo,pageSize,handler,context);
    }
    public void getSelectGift() {
        // TODO 可能还要验证密码
        // 登陆
        exchangeImpl = new ExchangeImpl();
        exchangeImpl.getSelectExchangeGift(pageNo,pageSize,handler,context);
    }

    private ExchangeAllGiftHandler handler;
    public interface ExchangeAllGiftHandler extends HttpHandler {
        public void onSuccess(ExchangeGiftResultBean bean);
    }


    public void setHandler(ExchangeAllGiftHandler handler){
        this.handler = handler;
    }
}
