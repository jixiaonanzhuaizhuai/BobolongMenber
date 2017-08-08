package com.lgmember.business.card;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.impl.CardImpl;
import com.lgmember.model.Card;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class CardDetailBusiness {

    private int card_id;
    private Context context;
    private CardImpl cardImpl;

    public CardDetailBusiness(Context context, int card_id) {
        super();
        this.context = context;
        this.card_id = card_id;
    }

    // 先验证参数的可发性，再登陆
    public void getCardDetail() {
        // TODO 可能还要验证密码
        // 登陆
        cardImpl = new CardImpl();
        cardImpl.getCardDetail(card_id,handler,context);
    }

    private CardDetailResultHandler handler;

    public interface CardDetailResultHandler extends HttpHandler {
        //当参数为空
        public void onCardDetailSuccess(Card card);

    }
    public void setHandler(CardDetailResultHandler handler){
        this.handler = handler;
    }
}
