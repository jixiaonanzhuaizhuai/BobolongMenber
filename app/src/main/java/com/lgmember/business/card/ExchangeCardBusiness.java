package com.lgmember.business.card;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.CardImpl;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class ExchangeCardBusiness {

    private int card_id;
    private Context context;
    private CardImpl cardImpl;

    public ExchangeCardBusiness(Context context, int card_id) {
        super();
        this.context = context;
        this.card_id = card_id;
    }

    // 先验证参数的可发性，再登陆
    public void exchangeCard() {
        // TODO 可能还要验证密码
        // 登陆
        cardImpl = new CardImpl();
        cardImpl.exchangeCard(card_id,handler,context);
    }

    private ExchangeCardResultHandler handler;

    public interface ExchangeCardResultHandler extends HttpHandler {
        //当参数为空
        public void onExchangeCardSuccess(String data);

    }
    public void setHandler(ExchangeCardResultHandler handler){
        this.handler = handler;
    }
}
