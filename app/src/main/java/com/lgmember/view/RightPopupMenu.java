package com.lgmember.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lgmember.activity.R;

public class RightPopupMenu extends PopupWindow implements OnClickListener {

	private Activity activity;
	private View popView;
	private Boolean is_collection;

	private View v_item1;
	private View v_item2;
	private View v_item3;
	private TextView collectionTxt;

	private OnItemClickListener onItemClickListener;

	/**
	 * 
	 * @author ywl5320 枚举，用于区分选择了哪个选项
	 */
	public enum MENUITEM {
		ITEM1, ITEM2, ITEM3
	}

	public RightPopupMenu(Activity activity,int layout,boolean is_collection) {
		super(activity);
		this.activity = activity;
		this.is_collection = is_collection;
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		popView = inflater.inflate(layout, null);// 加载菜单布局文件
		this.setContentView(popView);// 把布局文件添加到popupwindow中
		this.setWidth(dip2px(activity, 120));// 设置菜单的宽度（需要和菜单于右边距的距离搭配，可以自己调到合适的位置）
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);// 获取焦点
		this.setTouchable(true); // 设置PopupWindow可触摸
		this.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
		ColorDrawable dw = new ColorDrawable(0x00000000);
		this.setBackgroundDrawable(dw);

		// 获取选项卡
		v_item1 = popView.findViewById(R.id.ly_item1);
		v_item2 = popView.findViewById(R.id.ly_item2);

		if (layout == R.layout.popup_menu_sign){
			v_item3 = popView.findViewById(R.id.ly_item3);
			v_item3.setOnClickListener(this);
		}
		if (layout == R.layout.popup_menu_projecr_message_detail_more){
			collectionTxt = (TextView)popView.findViewById(R.id.txt_collection);
			if (is_collection){
				collectionTxt.setText("取消收藏");
			}else {
				collectionTxt.setText("点击收藏");
			}
		}

		// 添加监听
		v_item1.setOnClickListener(this);
		v_item2.setOnClickListener(this);


	}


	/**
	 * 设置显示的位置
	 * 
	 * @param resourId
	 *            这里的x,y值自己调整可以
	 */
	public void showLocation(int resourId) {
		showAsDropDown(activity.findViewById(resourId), dip2px(activity, 0),
				dip2px(activity, -8));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		MENUITEM menuitem = null;
		if (v == v_item1) {
			menuitem = MENUITEM.ITEM1;
		} else if (v == v_item2) {
			menuitem = MENUITEM.ITEM2;
		} else if (v == v_item3) {
			menuitem = MENUITEM.ITEM3;
		}
		if (onItemClickListener != null) {
			onItemClickListener.onClick(menuitem);
		}
		dismiss();
	}

	// dip转换为px
	public int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	// 点击监听接口
	public interface OnItemClickListener {
		public void onClick(MENUITEM item);
	}

	// 设置监听
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

}
