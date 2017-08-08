package com.lgmember.activity.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.adapter.ProjectMessageListAdapter;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.business.project.ProjectMessageListBusiness;
import com.lgmember.model.ProjectMessage;
import com.lgmember.model.Tag;
import com.lgmember.util.DataLargeHolder;
import com.lgmember.view.TopBarView;

import java.util.ArrayList;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;

/**
 * Created by Yanan_Wu on 2017/6/14.
 */

public class TagProjectMessageListActivity extends BaseActivity implements TopBarView.onTitleBarClickListener,ProjectMessageListBusiness.ProjectMessageListResultHandler{

    private TopBarView topBar;
    private LinearLayout ll_loading;
    private ProgressBar progressBar;
    private TextView loadDesc;
    private ListView lv_tag0_list;
    private int pageNo = 1;
    private int pageSize = 5;
    private int total;
    private boolean isLoading;
    private List<ProjectMessage> projectMessageList;
    private ProjectMessageListAdapter adapter;
    private String TAG = "-FragmentAlreadyJoin-";

    private SmartPullableLayout mPullableLayout;

    private static final int ON_REFRESH = 1;
    private static final int ON_LOAD_MORE = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_REFRESH:
                    adapter.notifyDataSetChanged();
                    mPullableLayout.stopPullBehavior();
                    break;
                case ON_LOAD_MORE:
                    mPullableLayout.stopPullBehavior();
                    break;
            }
        }
    };

    public static void actionStart(Context context, int tag){
        Intent intent = new Intent(context,TagProjectMessageListActivity.class);
        intent.putExtra("tag",tag);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_projectmessage_list);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNo = 1;
        projectMessageList.clear();
        ll_loading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        loadDesc.setText("正在拼命加载");
        getData();
    }

    private void init() {
        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);
        lv_tag0_list=(ListView) findViewById(R.id.lv_tag_list);
        projectMessageList = new ArrayList<>();
        adapter = new ProjectMessageListAdapter(
                this,projectMessageList);
        lv_tag0_list.setAdapter(adapter);

        lv_tag0_list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        ProjectMessage projectMessage =
                                projectMessageList.get(position);
                        DataLargeHolder.getInstance()
                                .save(projectMessage.getId(),projectMessage);
                        Intent intent = new Intent(TagProjectMessageListActivity.this,ProjectMessageDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",projectMessage.getId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

        lv_tag0_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            //滑动状态改变的时候，回调
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            //在滑动的时候不断的回调
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount==totalItemCount&&!isLoading) {
                    isLoading = true;
                    if (totalItemCount< total){
                        pageNo++;
                        getData();
                    }
                }
            }
        });
        ll_loading = (LinearLayout)findViewById(R.id.ll_loading);
        progressBar = (ProgressBar)findViewById(R.id.progressBar1);
        loadDesc = (TextView)findViewById(R.id.tv_loading_desc);
        mPullableLayout = (SmartPullableLayout)findViewById(R.id.layout_pullable);
        mPullableLayout.setOnPullListener(new SmartPullableLayout.OnPullListener() {
            @Override
            public void onPullDown() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            pageNo = 1;
                            projectMessageList.clear();
                            getData();
                            Thread.sleep(3000);
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
        int tag = getIntent().getIntExtra("tag",0);
        ProjectMessageListBusiness projectMessageListBusiness = new
                ProjectMessageListBusiness(context,pageNo,pageSize,tag);
        projectMessageListBusiness.setHandler(this);
        projectMessageListBusiness.getProjectMessageAllList();
    }


    @Override
    public void onSuccess(final ProjectMessageBean bean) {

        total = bean.getTotal();


        if (bean.getList().size() == 0){
            progressBar.setVisibility(View.GONE);
            loadDesc.setText("还没有数据");
        }else {
            ll_loading.setVisibility(View.GONE);
            projectMessageList.addAll(bean.getList());
            adapter.notifyDataSetChanged();
            isLoading = false;
        }

    }

    @Override
    public void onHotSuccess(ProjectMessageBean bean) {
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}
