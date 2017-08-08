package com.lgmember.activity.score;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.BaseFragment;
import com.lgmember.activity.R;
import com.lgmember.adapter.HistoryScoresListAdapter;
import com.lgmember.bean.HistoryScoresBean;
import com.lgmember.business.score.HistoryScoresBusiness;
import com.lgmember.model.HistoryScores;
import com.lgmember.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;

/**
 * Created by Yanan_Wu on 2016/12/19.
 */

public class HistoryScoresActivity extends BaseFragment implements HistoryScoresBusiness.HistoryScoresResultHandler {

    private LinearLayout ll_loading;
    private ProgressBar progressBar;
    private TextView loadDesc;
    private ListView listView;
    private int pageNo = 1;
    private int pageSize = 5;
    private int total;
    private boolean isLoading;

    private List<HistoryScores> historyScoresList = new ArrayList<HistoryScores>();
    private HistoryScoresListAdapter adapter;

    private SmartPullableLayout mPullableLayout;
    private static final int ON_REFRESH = 1;
    private static final int ON_LOAD_MORE = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_REFRESH:
                    getInitData();
                    adapter.notifyDataSetChanged();
                    mPullableLayout.stopPullBehavior();
                    break;
                case ON_LOAD_MORE:
                    mPullableLayout.stopPullBehavior();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_historyscores, container, false);
        init(view);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        historyScoresList.clear();
        pageNo = 1;
        ll_loading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        loadDesc.setText("正在拼命加载");
        getInitData();
    }

    private void getInitData() {
        getData();
    }

    public void init(View view) {
        listView = (ListView) view.findViewById(R.id.lv_history_scores);
        ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        loadDesc = (TextView) view.findViewById(R.id.tv_loading_desc);
        adapter = new HistoryScoresListAdapter(getActivity(),historyScoresList);
        listView.setAdapter(adapter);

        listView.setOnScrollListener(new OnScrollListener() {
            //滑动状态改变的时候，回调
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            //在滑动的时候不断的回调
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount==totalItemCount&&!isLoading) {
                    isLoading = true;
                    if (totalItemCount< total){
                        pageNo++;
                        getData();
                    }
                }
            }
        });
        mPullableLayout = (SmartPullableLayout)view.findViewById(R.id.layout_pullable);
        mPullableLayout.setOnPullListener(new SmartPullableLayout.OnPullListener() {
            @Override
            public void onPullDown() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(ON_REFRESH);
                    }
                }).start();
            }

            @Override
            public void onPullUp() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(ON_LOAD_MORE);
                    }
                }).start();
            }

        });
    }
    private void getData() {
        HistoryScoresBusiness historyScoresBusiness = new HistoryScoresBusiness(getActivity(), pageNo, pageSize);
        historyScoresBusiness.setHandler(this);
        historyScoresBusiness.getHistoryScores();
    }

    @Override
    public void onHisSuccess(HistoryScoresBean bean) {

        if (bean.getData().size() == 0) {
            progressBar.setVisibility(View.GONE);
            loadDesc.setText("目前还没有数据");
        } else {
            ll_loading.setVisibility(View.GONE);
            total = bean.getTotal();
            historyScoresList.addAll(bean.getData());
            adapter.notifyDataSetChanged();
            isLoading = false;
        }
    }
}
