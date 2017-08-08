package com.lgmember.activity.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lgmember.activity.BaseFragment;
import com.lgmember.activity.MainActivity;
import com.lgmember.activity.R;
import com.lgmember.adapter.ProjectMessageListAdapter;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.bean.TagsListResultBean;
import com.lgmember.business.project.ProjectMessageListBusiness;
import com.lgmember.business.project.TagListBusiness;
import com.lgmember.model.ProjectMessage;
import com.lgmember.model.Tag;
import com.lgmember.util.DataLargeHolder;
import com.lgmember.view.TopBarView;

import java.util.ArrayList;
import java.util.List;

import me.hwang.widgets.SmartPullableLayout;

public class FragmentSoonJoin extends BaseFragment implements ProjectMessageListBusiness.ProjectMessageListResultHandler,TopBarView.onTitleBarClickListener,TagListBusiness.TagListResultHandler {

	private LinearLayout ll_loading;
	private ProgressBar progressBar;
	private TextView loadDesc;
	private ListView lv_soon_join_list;
	private int pageNo = 1;
	private int pageSize = 5;
	private int total;
	private int tagNum = 0;
	private boolean isLoading;
	private TopBarView topBar;
	

	private List<ProjectMessage> projectMessagesList ;
	private ProjectMessageListAdapter adapter;
	private String TAG = "-FragmentSoonJoin-";

	private SmartPullableLayout mPullableLayout;

	private RecyclerView rc_tags_list;
	private TagsListHorizontalAdapter tagsListAdapter;
	private List<Tag> tagList;

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


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_soonjoin, container, false);
		init(view);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		pageNo = 1;
		projectMessagesList.clear();
		lv_soon_join_list.setEnabled(false);
		ll_loading.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		loadDesc.setText("正在拼命加载");
		getData();
	}

	private void init(View view) {
		tagList = new ArrayList<>();
		//获取标签列表数据
		rc_tags_list = (RecyclerView)view.findViewById(R.id.rc_tags_list);
		getTagsList();
		LinearLayoutManager layoutManager =new LinearLayoutManager(getContext());
		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		rc_tags_list.setLayoutManager(layoutManager);
		tagsListAdapter = new TagsListHorizontalAdapter(tagList);
		rc_tags_list.setAdapter(tagsListAdapter);

		topBar = (TopBarView)view.findViewById(R.id.topbar);
		topBar.setClickListener(this);
		lv_soon_join_list=(ListView)view.findViewById(R.id.lv_soon_join_activity_list);
		projectMessagesList = new ArrayList<>();
		adapter = new ProjectMessageListAdapter(getActivity(),projectMessagesList);
		lv_soon_join_list.setAdapter(adapter);

		lv_soon_join_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				ProjectMessage projectMessage =
						projectMessagesList.get(position);
				DataLargeHolder.getInstance()
						.save(projectMessage.getId(),projectMessage);
				Intent intent = new
						Intent(getActivity(),
						ProjectMessageDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("id",projectMessage.getId());
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});
		lv_soon_join_list.setOnScrollListener(new AbsListView.OnScrollListener() {
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
		ll_loading = (LinearLayout)view.findViewById(R.id.ll_loading);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
		loadDesc = (TextView)view.findViewById(R.id.tv_loading_desc);
		mPullableLayout = (SmartPullableLayout)view.findViewById(R.id.layout_pullable);
		mPullableLayout.setOnPullListener(new SmartPullableLayout.OnPullListener() {
			@Override
			public void onPullDown() {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							pageNo = 1;
							projectMessagesList.clear();
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

	private void getTagsList() {

		int tab = 0;
		TagListBusiness tagListBusiness = new TagListBusiness(getContext(),tab);
		tagListBusiness.setHandler(this);
		tagListBusiness.getAllTagList();

	}

	private void getData() {
		ProjectMessageListBusiness projectMessageListBusiness = new
				ProjectMessageListBusiness(getActivity(),pageNo,pageSize,tagNum);
		projectMessageListBusiness.setHandler(this);
		projectMessageListBusiness.getSoonJoinList();
	}


	@Override
	public void onSuccess(ProjectMessageBean bean) {

		total = bean.getTotal();
		progressBar.setVisibility(View.GONE);

		if (bean.getList().size() == 0){
			lv_soon_join_list.setEnabled(false);
			progressBar.setVisibility(View.GONE);
			loadDesc.setText("还没有数据");
		}else {
			lv_soon_join_list.setEnabled(true);
			ll_loading.setVisibility(View.GONE);
			projectMessagesList.addAll(bean.getList());
			adapter.notifyDataSetChanged();
			isLoading = false;
		}

	}

	@Override
	public void onHotSuccess(ProjectMessageBean bean) {

	}

	@Override
	public void onBackClick() {
		Intent intent = new Intent(getActivity(),MainActivity.class);
		startActivity(intent);
	}

	@Override
	public void onRightClick() {
	}
	@Override
	public void onTagListSuccess(TagsListResultBean tagsListResultBean) {

		Tag tag = new Tag();
		tag.setId(0);
		tag.setTag("ALL");
		tagList.add(tag);
		tagList.addAll(tagsListResultBean.getData());
		tagsListAdapter.notifyDataSetChanged();
	}


	class TagsListHorizontalAdapter extends RecyclerView.Adapter<TagsListHorizontalAdapter.ViewHolder> {

		private List<Tag> mTagsList ;
		class ViewHolder extends  RecyclerView.ViewHolder{
			LinearLayout ll_tag_item;
			TextView tv_tag_name;
			public ViewHolder(View view){
				super(view);
				ll_tag_item = (LinearLayout)view.findViewById(R.id.ll_tag_item);
				tv_tag_name = (TextView)view.findViewById(R.id.tv_tag_name);
			}
		}

		public TagsListHorizontalAdapter(List<Tag> tagList){
			mTagsList = tagList;

		}

		@Override
		public TagsListHorizontalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tags_recyclerview_horizontal,parent,false);
			final TagsListHorizontalAdapter.ViewHolder holder = new TagsListHorizontalAdapter.ViewHolder(view);
			holder.ll_tag_item.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = holder.getAdapterPosition();
					Tag tag = mTagsList.get(position);
					tagNum = tag.getId();
					projectMessagesList.clear();
					pageNo =  1;
					getData();
					adapter.notifyDataSetChanged();
				}
			});
			return holder;
		}

		@Override
		public void onBindViewHolder(TagsListHorizontalAdapter.ViewHolder holder, int position) {
			Tag tag = mTagsList.get(position);
			holder.tv_tag_name.setText(""+tag.getTag());
		}

		@Override
		public int getItemCount() {
			return mTagsList.size();
		}
	}
}

