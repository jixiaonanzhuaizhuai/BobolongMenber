package com.lgmember.activity.sign;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lgmember.activity.AudioFileFunc;
import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.business.UploadImgBusiness;
import com.lgmember.business.sign.UploadRecordBusiness;
import com.lgmember.util.ErrorCode;

import java.io.File;

public class ProgramSignActivity extends BaseActivity implements UploadRecordBusiness.UploadRecordResulHandler {
    private final static int FLAG_WAV = 0;
    private final static int FLAG_AMR = 1;
    private int mState = -1;    //-1:没再录制，0：录制wav，1：录制amr
    private Button btn_record_wav;
    private TextView txt;
    private UIHandler uiHandler;
    private UIThread uiThread;
    private String session_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programsign);
        init();
    }

    private void init(){
        btn_record_wav = (Button)this.findViewById(R.id.btn_record_wav);
        txt = (TextView)this.findViewById(R.id.text);
        btn_record_wav.setOnClickListener(btn_record_wav_clickListener);
        uiHandler = new UIHandler();
    }
    private Button.OnClickListener btn_record_wav_clickListener = new Button.OnClickListener(){
        public void onClick(View v){
            record(FLAG_WAV);
        }
    };
    /**
     * 开始录音
     * @param mFlag，0：录制wav格式，1：录音amr格式
     */
    private void record(int mFlag){
        if(mState != -1){
            Message msg = new Message();
            Bundle b = new Bundle();// 存放数据
            b.putInt("cmd",CMD_RECORDFAIL);
            b.putInt("msg", ErrorCode.E_STATE_RECODING);
            msg.setData(b);

            uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
            return;
        }
        int mResult = -1;
        switch(mFlag){
            case FLAG_WAV:
                MediaRecordFunc mRecord = MediaRecordFunc.getInstance();
                mResult = mRecord.startRecordAndFile();
                break;
        }
        if(mResult == ErrorCode.SUCCESS){
            uiThread = new UIThread();
            new Thread(uiThread).start();
            mState = mFlag;
        }else{
            Message msg = new Message();
            Bundle b = new Bundle();// 存放数据
            b.putInt("cmd",CMD_RECORDFAIL);
            b.putInt("msg", mResult);
            msg.setData(b);

            uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
        }
    }
    /**
     * 停止录音
     */
    private void stop(){
        if(mState != -1){
            switch(mState){
                case FLAG_WAV:
                    MediaRecordFunc mRecord = MediaRecordFunc.getInstance();
                    mRecord.stopRecordAndFile();
                    break;
            }
            if(uiThread != null){
                uiThread.stopThread();
            }
            if(uiHandler != null)
                uiHandler.removeCallbacks(uiThread);
            Message msg = new Message();
            Bundle b = new Bundle();// 存放数据
            b.putInt("cmd",CMD_STOP);
            b.putInt("msg", mState);
            msg.setData(b);
            uiHandler.sendMessageDelayed(msg,1000); // 向Handler发送消息,更新UI
            mState = -1;
        }
    }
    private final static int CMD_RECORDING_TIME = 2000;
    private final static int CMD_RECORDFAIL = 2001;
    private final static int CMD_STOP = 2002;

    @Override
    public void onUploadRecordSuccess(String s) {
        btn_record_wav.setText("上传成功");
    }


    class UIHandler extends Handler{
        public UIHandler() {
        }
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d("MyHandler", "handleMessage......");
            super.handleMessage(msg);
            Bundle b = msg.getData();
            int vCmd = b.getInt("cmd");
            switch(vCmd)
            {
                case CMD_RECORDING_TIME:
                    int vTime = b.getInt("msg");
                    btn_record_wav.setText("正在录音");
                    if (vTime == 15){
                        stop();
                    }
                    break;
                case CMD_RECORDFAIL:
                    int vErrorCode = b.getInt("msg");
                    String vMsg = ErrorCode.getErrorInfo(ProgramSignActivity.this, vErrorCode);
                    btn_record_wav.setText("录音失败");
                    break;
                case CMD_STOP:
                    int vFileType = b.getInt("msg");
                    switch(vFileType){
                        case FLAG_WAV:
                            MediaRecordFunc mRecord_2 = MediaRecordFunc.getInstance();
                            long mSize = mRecord_2.getRecordFileSize();
                            String path = AudioFileFunc.getWavFilePath();
                            uploadRecord(path);
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void uploadRecord(String path) {

        session_id = java.util.UUID.randomUUID().toString();
        UploadRecordBusiness uploadRecordBusiness =
                new UploadRecordBusiness(context,
                        session_id,path,MediaRecordFunc.timestamp);
        uploadRecordBusiness.setHandler(this);
        uploadRecordBusiness.uploadRecord();
    }

    class UIThread implements Runnable {
        int mTimeMill = 0;
        boolean vRun = true;
        public void stopThread(){
            vRun = false;
        }
        public void run() {
            while(vRun){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mTimeMill ++;
                Log.d("thread", "mThread........"+mTimeMill);
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putInt("cmd",CMD_RECORDING_TIME);
                b.putInt("msg", mTimeMill);
                msg.setData(b);
                ProgramSignActivity.this.uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
            }

        }
    }

}