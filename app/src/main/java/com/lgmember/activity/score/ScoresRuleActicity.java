package com.lgmember.activity.score;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.BaseFragment;
import com.lgmember.activity.R;
import com.lgmember.business.score.ScoresRuleBusiness;
import com.lgmember.model.ScoresRule;
import com.lgmember.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Yanan_Wu on 2016/12/19.
 */

public class ScoresRuleActicity extends BaseFragment implements ScoresRuleBusiness.ScoresRuleHandler {

    private TextView rule1,rule2,rule3,rule4,rule5,rule6,rule7,rule8,rule9,rule10,rule11,rule12
            ,rule13,rule14,rule15,rule16,rule17,rule18,rule19,rule20,rule21,rule22,rule23,rule24,rule25,rule26
            ,rule27,rule28,rule29,rule30,rule31;
    private ScoresRule scoresRelu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_scoresrule, container, false);
        initView(view);
        getScoresRuleRule();
        return view;
    }

    public void initView(View view){
        rule1 = (TextView)view.findViewById(R.id.rule1);
        rule2 = (TextView)view.findViewById(R.id.rule2);
        rule3 = (TextView)view.findViewById(R.id.rule3);
        rule4 = (TextView)view.findViewById(R.id.rule4);
        rule5 = (TextView)view.findViewById(R.id.rule5);
        rule6 = (TextView)view.findViewById(R.id.rule6);
        rule7 = (TextView)view.findViewById(R.id.rule7);
        rule8 = (TextView)view.findViewById(R.id.rule8);
        rule9 = (TextView)view.findViewById(R.id.rule9);
        rule10 = (TextView)view.findViewById(R.id.rule10);
        rule11 = (TextView)view.findViewById(R.id.rule11);
        rule12 = (TextView)view.findViewById(R.id.rule12);
        rule13 = (TextView)view.findViewById(R.id.rule13);
        rule14 = (TextView)view.findViewById(R.id.rule14);
        rule15 = (TextView)view.findViewById(R.id.rule15);
        rule16 = (TextView)view.findViewById(R.id.rule16);
        rule17 = (TextView)view.findViewById(R.id.rule17);
        rule18 = (TextView)view.findViewById(R.id.rule18);
        rule19 = (TextView)view.findViewById(R.id.rule19);
        rule20 = (TextView)view.findViewById(R.id.rule20);
        rule21 = (TextView)view.findViewById(R.id.rule21);
        rule22 = (TextView)view.findViewById(R.id.rule22);
        rule23 = (TextView)view.findViewById(R.id.rule23);
        rule24 = (TextView)view.findViewById(R.id.rule24);
        rule25 = (TextView)view.findViewById(R.id.rule25);
        rule26 = (TextView)view.findViewById(R.id.rule26);
        rule27 = (TextView)view.findViewById(R.id.rule27);
        rule28 = (TextView)view.findViewById(R.id.rule28);
        rule29 = (TextView)view.findViewById(R.id.rule29);
        rule30 = (TextView)view.findViewById(R.id.rule30);
        rule31 = (TextView)view.findViewById(R.id.rule31);
    }
    public void getScoresRuleRule(){
        ScoresRuleBusiness scoresRuleBusiness = new ScoresRuleBusiness(getActivity());
        //处理结果
        scoresRuleBusiness.setHandler(this);
        scoresRuleBusiness.getScoresRule();
    }
    @Override
    public void onSuccess(JSONObject jsob) {

        scoresRelu = new ScoresRule();
        int code = 99;
        try {
            code = jsob.getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (code == 0){
        try {
            JSONObject jsonObject = jsob.getJSONObject("data");
            scoresRelu.setRedup(jsonObject.getInt("redup"));//红卡升级所需积分
            scoresRelu.setRedcut(jsonObject.getInt("redcut"));//红卡升级后扣除积分
            scoresRelu.setAgup(jsonObject.getInt("agup"));//银卡升级所需积分
            scoresRelu.setAgcut(jsonObject.getInt("agcut"));//银卡升级后扣除积分
            scoresRelu.setAuup(jsonObject.getInt("auup"));//金卡升级所需积分
            scoresRelu.setAucut(jsonObject.getInt("aucut"));//金卡升级后扣除积分
            scoresRelu.setAgdown(jsonObject.getInt("agdown"));//银卡降级积分
            scoresRelu.setAudown(jsonObject.getInt("audown"));//金卡降级积分
            scoresRelu.setSign(jsonObject.getInt("sign"));   //会员报名积分
            scoresRelu.setRegister(jsonObject.getInt("register"));//注册积分
            scoresRelu.setPunish(jsonObject.getInt("punish"));//积分惩罚
            scoresRelu.setComplete(jsonObject.getInt("complete"));//完善资料积分
            scoresRelu.setShare(jsonObject.getInt("share"));//分享活动积分
            scoresRelu.setProject_A(jsonObject.getInt("project_A"));//A级活动基础积分
            scoresRelu.setProject_B(jsonObject.getInt("project_B"));//B级活动基础积分
            scoresRelu.setProject_C(jsonObject.getInt("project_C"));//C级活动基础积分
            scoresRelu.setProject_double(jsonObject.getDouble("project_double"));//活动签到积分翻倍倍数
            scoresRelu.setProgram(jsonObject.getInt("program"));//节目签到积分
            scoresRelu.setProgram_double(jsonObject.getDouble("program_double"));//节目签到积分翻倍倍数
            scoresRelu.setA_red_double(jsonObject.getDouble("A_red_double"));//A级活动红卡会员翻倍倍数
            scoresRelu.setA_ag_double(jsonObject.getDouble("A_ag_double"));//A级活动银卡会员翻倍倍数
            scoresRelu.setA_au_double(jsonObject.getDouble("A_au_double"));//A级活动金卡会员翻倍倍数
            scoresRelu.setA_diamond_double(jsonObject.getDouble("A_diamond_double"));//A级活动钻石卡会员翻倍倍数
            scoresRelu.setB_red_double(jsonObject.getDouble("B_red_double"));//B级活动红卡会员翻倍倍数
            scoresRelu.setB_ag_double(jsonObject.getDouble("B_ag_double"));//B级活动银卡会员翻倍倍数
            scoresRelu.setB_au_double(jsonObject.getDouble("B_au_double"));//B级活动金卡会员翻倍倍数
            scoresRelu.setB_diamond_double(jsonObject.getDouble("B_diamond_double"));//B级活动钻石卡会员翻倍倍数
            scoresRelu.setC_red_double(jsonObject.getDouble("C_red_double"));//C级活动红卡会员翻倍倍数
            scoresRelu.setC_ag_double(jsonObject.getDouble("C_ag_double"));//C级活动银卡会员翻倍倍数
            scoresRelu.setC_au_double(jsonObject.getDouble("C_au_double"));//C级活动金卡会员翻倍倍数
            scoresRelu.setC_diamond_double(jsonObject.getDouble("C_diamond_double"));//C级活动钻石卡会员翻倍倍数
        } catch (JSONException e) {
            e.printStackTrace();
        }

            rule1.setText(""+scoresRelu.getRedup());
            rule2.setText(""+scoresRelu.getRedcut());
            rule3.setText(""+scoresRelu.getAgup());
            rule4.setText(""+scoresRelu.getAgcut());
            rule5.setText(""+scoresRelu.getAuup());
            rule6.setText(""+scoresRelu.getAucut());
            rule7.setText(""+scoresRelu.getAgdown());
            rule8.setText(""+scoresRelu.getAudown());
            rule9.setText(""+scoresRelu.getSign());
            rule10.setText(""+scoresRelu.getRegister());
            rule11.setText(""+scoresRelu.getPunish());
            rule12.setText(""+scoresRelu.getComplete());
            rule13.setText(""+scoresRelu.getShare());
            rule14.setText(""+scoresRelu.getProject_A());
            rule15.setText(""+scoresRelu.getProject_B());
            rule16.setText(""+scoresRelu.getProject_C());
            rule17.setText(""+scoresRelu.getProject_double());
            rule18.setText(""+scoresRelu.getProgram());
            rule19.setText(""+scoresRelu.getProgram_double());
            rule20.setText(""+scoresRelu.getA_red_double());
            rule21.setText(""+scoresRelu.getA_ag_double());
            rule22.setText(""+scoresRelu.getA_au_double());
            rule23.setText(""+scoresRelu.getA_au_double());
            rule24.setText(""+scoresRelu.getB_red_double());
            rule25.setText(""+scoresRelu.getB_ag_double());
            rule26.setText(""+scoresRelu.getB_au_double());
            rule27.setText(""+scoresRelu.getB_diamond_double());
            rule28.setText(""+scoresRelu.getC_red_double());
            rule29.setText(""+scoresRelu.getC_ag_double());
            rule30.setText(""+scoresRelu.getC_au_double());
            rule31.setText(""+scoresRelu.getC_diamond_double());
        }else {
            Toast.makeText(getActivity(),getActivity().getString(StringUtil.codeTomsg(code)),Toast.LENGTH_LONG).show();
        }
    }
}
