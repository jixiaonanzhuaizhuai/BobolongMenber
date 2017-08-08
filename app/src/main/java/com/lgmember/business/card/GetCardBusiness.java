package com.lgmember.business.card;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.CardImpl;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class GetCardBusiness {

    private int card_id;
    private Context context;
    private CardImpl cardImpl;

    public GetCardBusiness(Context context, int card_id) {
        super();
        this.context = context;
        this.card_id = card_id;
    }

    // 先验证参数的可发性，再登陆
    public void getCard() {
        // TODO 可能还要验证密码
        // 登陆
        cardImpl = new CardImpl();
        cardImpl.getCard(card_id,handler,context);
    }

    public void getRemindCard() {
        // TODO 可能还要验证密码
        // 登陆
        cardImpl = new CardImpl();
        cardImpl.getRemindCard(card_id,handler,context);
    }

    private GetCardResultHandler handler;

    public interface GetCardResultHandler extends HttpHandler {
        //当参数为空
        public void onGetCardSuccess(int data);
    }
    public void setHandler(GetCardResultHandler handler){
        this.handler = handler;
    }
}
