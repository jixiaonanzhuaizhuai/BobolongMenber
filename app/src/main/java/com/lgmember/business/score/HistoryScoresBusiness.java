package com.lgmember.business.score;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.HistoryScoresBean;
import com.lgmember.impl.ScoresImpl;
import com.lgmember.model.HistoryScores;

import java.util.List;

/**
 * Created by Yanan_Wu on 2017/3/6.
 */

public class HistoryScoresBusiness {

    private int pageNo;
    private int pageSize;
    private Context context;
    private ScoresImpl scoresImpl;

    public HistoryScoresBusiness(Context context, int pageNo, int pageSize) {
        super();
        this.context = context;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    // 先验证参数的可发性，再登陆
    public void getHistoryScores() {
        // TODO 可能还要验证密码
        // 登陆
        scoresImpl = new ScoresImpl();
        scoresImpl.getHistoryScores(pageNo,pageSize,handler,context);
    }

    private HistoryScoresBusiness.HistoryScoresResultHandler handler;

    public interface HistoryScoresResultHandler extends HttpHandler {
        //当参数为空
        public void onHisSuccess(HistoryScoresBean historyScoresBean);

    }
    public void setHandler(HistoryScoresResultHandler handler){
        this.handler = handler;
    }
}
