package com.lgmember.activity.score;

import android.app.AlertDialog;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.business.score.ExchangeGiftBusiness;
import com.lgmember.business.score.ExchangeGiftInfoBusiness;
import com.lgmember.business.score.ScoresRuleBusiness;
import com.lgmember.business.SmsCodeBusiness;
import com.lgmember.model.Gift;
import com.lgmember.model.ScoresInfo;
import com.lgmember.util.Common;
import com.lgmember.util.StringUtil;
import com.lgmember.view.TopBarView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2016/12/19.
 */

public class ExchangeGiftDetailActivity extends BaseActivity implements ScoresRuleBusiness.ScoresRuleHandler,SmsCodeBusiness.GetCodeResultHandler,ExchangeGiftBusiness.ExchangeGiftHandler,TopBarView.onTitleBarClickListener ,ExchangeGiftInfoBusiness.ExchangeGiftInfoHandler{

    private TextView nameTxt;
    private EditText adressTxt, phoneTxt;
    private ImageView giftImg;
    private int gift_id;
    private String mobile;
    private String sms_capt_tokenTxt = "";
    private String capt = "";
    private TopBarView topBar;
    private Button btn_exchange_gift;

    private AlertDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangegiftdetail);
        Bundle bundle = this.getIntent().getExtras();
        gift_id = bundle.getInt("gift_id");
        init();
        getGiftData();
        fillData();
    }

    private void getGiftData() {
            ExchangeGiftInfoBusiness exchangeGiftInfoBusiness = new ExchangeGiftInfoBusiness(context,gift_id);
            exchangeGiftInfoBusiness.setHandler(this);
            exchangeGiftInfoBusiness.getGiftInfo();
    }

    public void init() {

        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);
        adressTxt = (EditText) findViewById(R.id.adressTxt);
        nameTxt = (TextView) findViewById(R.id.nameTxt);
        phoneTxt = (EditText) findViewById(R.id.phoneTxt);
        giftImg = (ImageView) findViewById(R.id.giftImg);
        btn_exchange_gift = (Button)findViewById(R.id.btn_exchange_gift);
        btn_exchange_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPhoneCode();
            }
        });
    }

    private void fillData() {
        ScoresRuleBusiness scoresRuleBusiness = new ScoresRuleBusiness(context);
        //处理结果
        scoresRuleBusiness.setHandler(this);
        scoresRuleBusiness.getScoresInfo();
    }
    private ScoresInfo scoresInfo = new ScoresInfo();
    @Override
    public void onSuccess(JSONObject jsob) {
        int code = 100;
        try {
            code = jsob.getInt("code");
            if (code == 0){
                JSONObject jobject = jsob.getJSONObject("data");
                scoresInfo.setName(jobject.getString("name"));
                scoresInfo.setAddr(jobject.getString("addr"));
                scoresInfo.setMobile(jobject.getString("mobile"));
                nameTxt.setText(scoresInfo.getName());
                adressTxt.setText(scoresInfo.getAddr());
                mobile = scoresInfo.getMobile();
                phoneTxt.setText(mobile);
            }else {
                showToast(context.getString(StringUtil.codeTomsg(code)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void DialogPhoneCode() {
        AlertDialog.Builder adb = new AlertDialog.Builder(ExchangeGiftDetailActivity.this);
        dialog = adb.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_exchange_gift_phone_code, null);
        TextView txt_phone = (TextView) view.findViewById(R.id.txt_phone);
        txt_phone.setText(getText(phoneTxt));
        final Button btn_request_code = (Button) view.findViewById(R.id.btn_request_code);
        class TimeCount extends CountDownTimer {
            public TimeCount(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }
            public void onFinish() {
                btn_request_code.setText("获取验证码");
                btn_request_code.setClickable(true);
            }
            public void onTick(long millisUntilFinished) {
                btn_request_code.setClickable(false);
                btn_request_code.setText(millisUntilFinished / 1000 + "秒后点击重发");
            }
        }
        final TimeCount timeCount = new TimeCount(60000,1000);
        btn_request_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_request_code.isClickable()) {
                    timeCount.start();
                    getCode();
                }
            }
        });
        final EditText edt_code = (EditText)view.findViewById(R.id.edt_code);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capt = getText(edt_code);
                exchangeGift(capt);
                dialog.dismiss();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
        }




    private void exchangeGift(String smsCode) {
        ExchangeGiftBusiness exchangeGiftBusiness = new ExchangeGiftBusiness(context,gift_id,
                scoresInfo.getName(),scoresInfo.getAddr(),scoresInfo.getMobile(),smsCode,sms_capt_tokenTxt);
        exchangeGiftBusiness.setHandler(this);
        exchangeGiftBusiness.exchangeGift();
    }
    private void getCode(){
        SmsCodeBusiness getCodeBusiness = new SmsCodeBusiness(context,getText(phoneTxt));
        //处理结果
        getCodeBusiness.setHandler(this);
        getCodeBusiness.getCode();
    }
    @Override
    public void onRequestCodeSuccess(String s) {
        sms_capt_tokenTxt = s;
    }
    @Override
    public void onArgumentSmsCodeEmpty() {
        showToast("验证码不能为空");
    }
    @Override
    public void onArgumentMobileFormatError() {
        showToast("手机格式不正确");
    }
    @Override
    public void onExchangeGiftSuccess(JSONObject jsonObject) {
        try {
            int code = jsonObject.getInt("code");
            if (code == 0){
                showToast("兑换成功");
                startIntent(ExchangeScoresActivity.class);
            }else {
                showToast(context.getString(StringUtil.codeTomsg(code)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onRightClick() {
    }

    @Override
    public void onSuccess(Gift gift) {
        String picture = Common.URL_IMG_BASE+gift.getPicture();
        StringUtil.setNetworkBitmap(context,picture,giftImg);
    }
}