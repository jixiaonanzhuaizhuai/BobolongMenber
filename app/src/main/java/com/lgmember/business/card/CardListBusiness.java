package com.lgmember.business.card;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.CardListResultBean;
import com.lgmember.impl.CardImpl;
import com.lgmember.model.Card;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class CardListBusiness {

    private int pageNo;
    private int pageSize;
    private int state;
    private Context context;
    private CardImpl cardImpl;

    public CardListBusiness(Context context, int pageNo, int pageSize,int state) {
        super();
        this.context = context;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.state = state;
    }

    // 先验证参数的可发性，再登陆
    public void getCardList() {
        // TODO 可能还要验证密码
        // 登陆
        cardImpl = new CardImpl();
        cardImpl.getCardList(pageNo,pageSize,state,handler,context);
    }

    private CardListBusiness.CardListResultHandler handler;

    public interface CardListResultHandler extends HttpHandler {
        //当参数为空
        public void onSuccess(CardListResultBean cardListResultBean);

    }
    public void setHandler(CardListResultHandler handler){
        this.handler = handler;
    }
}
