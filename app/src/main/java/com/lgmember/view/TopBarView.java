package com.lgmember.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgmember.activity.R;


/**
 * 导航栏 , 包括返回键,标题,右侧按钮.其中:
 * </br>返回键已经实现按键监听
 * </br>右侧按钮已实现按键监听
 */
public class TopBarView extends RelativeLayout
        implements  OnClickListener{

    private ImageView backView;
    private TextView titleView,rightView;

    private String titleTextStr,rightTextStr;
    private int titleTextSize ;
    private int  titleTextColor ;

    private Drawable leftImage ;

    public TopBarView(Context context){
        this(context, null);
    }


    public TopBarView(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.AppTheme);


    }

    public TopBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getConfig(context, attrs);
        initView(context);
    }

    /**
     * 从xml中获取配置信息
     */
    private void getConfig(Context context, AttributeSet attrs) {
        //TypedArray是一个数组容器用于存放属性值
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.Topbar);

        int count = ta.getIndexCount();
        for(int i = 0;i<count;i++)
        {
            int attr = ta.getIndex(i);
            switch (attr)
            {
                case R.styleable.Topbar_titleText:
                    titleTextStr = ta.getString(R.styleable.Topbar_titleText);
                    break;
                case R.styleable.Topbar_titleColor:
                    // 默认颜色设置为黑色
                    titleTextColor = ta.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.Topbar_titleSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    titleTextSize = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;

                case R.styleable.Topbar_leftBtn:

                    leftImage = ta.getDrawable(R.styleable.Topbar_leftBtn);
                    break;
                case R.styleable.Topbar_rightText:
                    rightTextStr = ta.getString(R.styleable.Topbar_rightText);
                    break;
            }
        }

        //用完务必回收容器
        ta.recycle();

    }


    private void initView(Context context)
    {
        View layout = LayoutInflater.from(context).inflate(R.layout.commom_topbar,
                this,true);

        backView = (ImageView) layout.findViewById(R.id.back_image);
        titleView = (TextView) layout.findViewById(R.id.text_title);
        rightView = (TextView) layout.findViewById(R.id.right_txt);
        backView.setOnClickListener(this);
        rightView.setOnClickListener(this);

        if(null != leftImage)
            backView.setImageDrawable(leftImage);
        if(null != rightTextStr)
            rightView.setText(rightTextStr);
        if(null != titleTextStr)
        {
            titleView.setText(titleTextStr);
            titleView.setTextSize(titleTextSize);
            titleView.setTextColor(titleTextColor);
        }
    }
    /**
     * 获取返回按钮
     * @return
     */
    public ImageView getBackView() {
        return backView;
    }

    /**
     * 获取标题控件
     * @return
     */
    public TextView getTitleView() {
        return titleView;
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title){
        titleView.setText(title);
    }

    /**
     * 获取右侧按钮,默认不显示
     * @return
     */
    public TextView getRightView() {
        return rightView;
    }

    private onTitleBarClickListener onMyClickListener;

    /**
     * 设置按钮点击监听接口
     */
    public void setClickListener(onTitleBarClickListener listener) {
        this.onMyClickListener = listener;
    }

    /**
     * 导航栏点击监听接口
     */
    public static interface onTitleBarClickListener{
        /**
         * 点击返回按钮回调
         */
        void onBackClick();

        void onRightClick();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id)
        {
            case R.id.back_image:
                if(null != onMyClickListener)
                    onMyClickListener.onBackClick();
                break;
            case R.id.right_txt:
                if(null != onMyClickListener)
                    onMyClickListener.onRightClick();
                break;
        }
    }
}