package com.lgmember.activity.message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lgmember.activity.BaseFragment;
import com.lgmember.activity.R;
import com.lgmember.activity.project.ProjectMessageDetailActivity;
import com.lgmember.adapter.RemindListAdapter;
import com.lgmember.bean.RemindListResultBean;
import com.lgmember.business.message.DeleteMsgBusiness;
import com.lgmember.business.message.RemindMessageListBusiness;
import com.lgmember.model.Remind;
import com.lgmember.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;

/**
 * Created by Yanan_Wu on 2016/12/19.
 */

//提醒列表

public class RemindMessageActivity extends BaseFragment implements RemindMessageListBusiness.RemindListResultHandler,DeleteMsgBusiness.DeleteMsgResultHandler{

    private LinearLayout ll_loading;
    private ProgressBar progressBar;
    private TextView loadDesc;
    private ListView lv_sysmeg_list;
    private int pageNo = 1;
    private int pageSize = 5;
    private int total;
    private boolean isLoading;
    //private int index;
    private List<Remind> remindList;
    private RemindListAdapter adapter;

    private int read_state = 3;    // 1 未读 2 已读 3 所有
    private SmartPullableLayout mPullableLayout;
    private static final int ON_REFRESH = 1;
    private static final int ON_LOAD_MORE = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_REFRESH:
                    getInitData();
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

        View view = inflater.inflate(R.layout.activity_systemmsg, container, false);
        init(view);
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getInitData();
    }
    private void getInitData() {
        remindList.clear();
        pageNo = 1;
        ll_loading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        loadDesc.setText("正在拼命加载");
        getData();
        adapter.notifyDataSetChanged();
    }


    private void getData() {
        RemindMessageListBusiness messageBusiness = new
                RemindMessageListBusiness(
                        getActivity(), pageNo, pageSize,read_state);
        messageBusiness.setHandler(this);
        messageBusiness.getRemindListMessage();
    }

    private void init(View view) {
        lv_sysmeg_list=(ListView)view.findViewById(R.id.lv_sysmeg_list);
        ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        loadDesc = (TextView) view.findViewById(R.id.tv_loading_desc);

        remindList = new ArrayList<>();
        adapter = new RemindListAdapter(getActivity(),remindList);
        lv_sysmeg_list.setAdapter(adapter);
        lv_sysmeg_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),RemindDetailActivity.class);
                intent.putExtra("remind",
                        new Gson().toJson(remindList.get(position)));
                startActivity(intent);
            }
        });

        lv_sysmeg_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            //滑动状态改变的时候，回调
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            //在滑动的时候不断的回调
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount ==
                        totalItemCount&&!isLoading) {
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
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(ON_LOAD_MORE);
                    }
                }).start();
            }

        });

    }

    @Override
    public void onSuccess(RemindListResultBean bean) {
        total = bean.getTotal();
        if (total == 0){
            progressBar.setVisibility(View.GONE);
            loadDesc.setText("当前还没有数据");
        }else {
            ll_loading.setVisibility(View.GONE);
            remindList.addAll(bean.getData());
            adapter.notifyDataSetChanged();
            isLoading = false;
        }
    }

    @Override
    public void onDeleteMsgSuccess() {
        adapter.notifyDataSetChanged();

    }

}
