package com.lgmember.activity.feedback;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.adapter.FeedbackListAdapter;
import com.lgmember.bean.FeedbackListBean;
import com.lgmember.business.feedback.CreateFeedbackBusiness;
import com.lgmember.business.feedback.DeleteFeedbackBusiness;
import com.lgmember.business.feedback.FeedbackListBusiness;
import com.lgmember.model.Feedback;
import com.lgmember.util.StringUtil;
import com.lgmember.view.TopBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yanan_Wu on 2017/1/9.
 */

public class ReportProblemActivity extends BaseActivity implements View.OnClickListener,
        CreateFeedbackBusiness.CreateFeedbackResultHandler,FeedbackListBusiness.FeedbackListResultHandler,
        DeleteFeedbackBusiness.DeleteFeedbackResultHandler,TopBarView.onTitleBarClickListener {
    private EditText edt_feedback_content;
    private Button btn_submit;
    private ExpandableListView ex_lv_feedback;
    private int pageNo = 1;
    private int pageSize = 10;
    private int total;
    private boolean isLoading;

    private TopBarView topBar;

    ArrayList<Feedback> feedBackList;
    private FeedbackListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportproblem);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        feedBackList = new ArrayList<>();
        adapter = new FeedbackListAdapter(context,feedBackList);
        ex_lv_feedback.setAdapter(adapter);
        getData();
        //expandableListView.expandGroup(0);//设置第一组张开
        //expandableListView.collapseGroup(0); 将第group组收起
        ex_lv_feedback.setGroupIndicator(null);//除去自带的箭头，自带的箭头在父列表的最左边，不展开向下，展开向上
        ex_lv_feedback.setDivider(null);//这个是设定每个Group之间的分割线。,默认有分割线，设置null没有分割线

        ex_lv_feedback.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteDialog(position);
                feedBackList.remove(position);
                return true;
            }
        });

        ex_lv_feedback.setOnScrollListener(new AbsListView.OnScrollListener() {
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
    }

    private void DeleteDialog(int position) {
        final int feedback_id = feedBackList.get(position).getId();

        final List ids = new ArrayList();
        ids.add(feedback_id);

        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setMessage("确定删除该条消息");
        build.setTitle("提示");
        build.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteData(ids);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        build.setNegativeButton("取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast("取消");
                dialog.dismiss();
            }

        });

        build.show();
    }



    private void deleteData(List ids) {
        DeleteFeedbackBusiness deleteFeedbackBusiness = new DeleteFeedbackBusiness(context,ids);
        deleteFeedbackBusiness.setHandler(this);
        deleteFeedbackBusiness.deleteFeedback();
    }

    private void getData() {
        FeedbackListBusiness feedbackListBusiness = new FeedbackListBusiness(context,pageNo,pageSize);
        feedbackListBusiness.setHandler(this);
        feedbackListBusiness.feedbackList();
    }

    private void initView() {
        topBar = (TopBarView)findViewById(R.id.topbar) ;
        topBar.setClickListener(this);
        edt_feedback_content = (EditText)findViewById(R.id.edt_feedback_content);
        ex_lv_feedback = (ExpandableListView)findViewById(R.id.ex_lv_feedback);

        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                createFeedback();
        }
    }

    private void createFeedback() {
        String content = getText(edt_feedback_content);
        CreateFeedbackBusiness createFeedbackBusiness = new CreateFeedbackBusiness(context,content);
        createFeedbackBusiness.setHandler(this);
        createFeedbackBusiness.createFeedback();
    }

    @Override
    public void onFeedbackListSuccess(FeedbackListBean feedbackListBean) {

        total = feedbackListBean.getTotal();
        feedBackList.addAll(feedbackListBean.getData());
        for (int i = 0;i<feedbackListBean.getData().size();i++){
             feedBackList.get(i).setResponse(feedbackListBean.getData().get(i).getResponse());
        }
        adapter.notifyDataSetChanged();
        isLoading = false;
    }
    @Override
    public void onCreateFeedbackSuccess() {
        showToast("提交成功");
        edt_feedback_content.setText("");
        //startIntent(ReportProblemActivity.class);
        //refreshData();
        feedBackList.clear();
        pageNo=1;
        getData();
    }


    @Override
    public void onDeleteFeedbackSuccess() {
        showToast("删除成功");
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }

}
