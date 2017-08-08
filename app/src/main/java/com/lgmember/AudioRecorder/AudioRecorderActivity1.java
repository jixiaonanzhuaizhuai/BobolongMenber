package com.lgmember.AudioRecorder;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.audiovisualization.GLAudioVisualizationView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.lgmember.AudioRecorder.model.AudioChannel;
import com.lgmember.AudioRecorder.model.AudioSampleRate;
import com.lgmember.AudioRecorder.model.AudioSource;
import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.MainActivity;
import com.lgmember.activity.R;
import com.lgmember.business.project.ActivityCodeBusiness;
import com.lgmember.business.sign.UploadRecordBusiness;
import com.lgmember.util.StringUtil;
import com.lgmember.view.TopBarView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yzq.zxinglibrary.Consants;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import omrecorder.AudioChunk;
import omrecorder.OmRecorder;
import omrecorder.PullTransport;
import omrecorder.Recorder;

public class AudioRecorderActivity1 extends BaseActivity
        implements PullTransport.OnAudioChunkPulledListener, MediaPlayer.OnCompletionListener ,View.OnClickListener,ActivityCodeBusiness.ActivityCodeResulHandler ,TopBarView.onTitleBarClickListener,UploadRecordBusiness.UploadRecordResulHandler{

    private String filePath;
    private AudioSource source;
    private AudioChannel channel;
    private AudioSampleRate sampleRate;
    private int color;
    private boolean autoStart;

    private Recorder recorder;
    private VisualizerHandler visualizerHandler;
    private Timer timer;
    private int recorderSecondsElapsed;
    private boolean isRecording;
    private RelativeLayout contentLayout;
    private GLAudioVisualizationView visualizerView;
    //private Button btn_code_sign;
    private Button btn_scan;
    private ImageButton recordView;
    //录音开始时间
    private String startRecordTime;

    private GoogleApiClient client;
    private AlertDialog dialog;
    private TopBarView topBar;
    private TextView tv_record_state;

    private static final int REQUEST_CODE_CAMERA = 100;
    private static final int REQUEST_CODE_SETTING = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aar_activity_audio_recorder1);
        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);

        if (savedInstanceState != null) {
            filePath = savedInstanceState.getString(AndroidAudioRecorder.EXTRA_FILE_PATH);
            source = (AudioSource) savedInstanceState.getSerializable(AndroidAudioRecorder.EXTRA_SOURCE);
            channel = (AudioChannel) savedInstanceState.getSerializable(AndroidAudioRecorder.EXTRA_CHANNEL);
            sampleRate = (AudioSampleRate) savedInstanceState.getSerializable(AndroidAudioRecorder.EXTRA_SAMPLE_RATE);
            color = savedInstanceState.getInt(AndroidAudioRecorder.EXTRA_COLOR);
            autoStart = savedInstanceState.getBoolean(AndroidAudioRecorder.EXTRA_AUTO_START);
        } else {
            filePath = getIntent().getStringExtra(AndroidAudioRecorder.EXTRA_FILE_PATH);
            source = (AudioSource) getIntent().getSerializableExtra(AndroidAudioRecorder.EXTRA_SOURCE);
            channel = (AudioChannel) getIntent().getSerializableExtra(AndroidAudioRecorder.EXTRA_CHANNEL);
            sampleRate = (AudioSampleRate) getIntent().getSerializableExtra(AndroidAudioRecorder.EXTRA_SAMPLE_RATE);
            color = getIntent().getIntExtra(AndroidAudioRecorder.EXTRA_COLOR, Color.BLACK);
            autoStart = getIntent().getBooleanExtra(AndroidAudioRecorder.EXTRA_AUTO_START, false);
        }
        visualizerView = new GLAudioVisualizationView.Builder(this)
                .setLayersCount(1)
                .setWavesCount(6)
                .setWavesHeight(R.dimen.aar_wave_height)
                .setWavesFooterHeight(R.dimen.aar_footer_height)
                .setBubblesPerLayer(20)
                .setBubblesSize(R.dimen.aar_bubble_size)
                .setBubblesRandomizeSize(true)
                .setBackgroundColor(getResources().getColor(R.color.white))
                .setLayerColors(new int[]{color})
                .build();

        contentLayout = (RelativeLayout) findViewById(R.id.content);
        recordView = (ImageButton) findViewById(R.id.record);

        //btn_code_sign = (Button) findViewById(R.id.btn_code_sign);
        btn_scan = (Button)findViewById(R.id.btn_scan);
        //btn_code_sign.setOnClickListener(this);
        btn_scan.setOnClickListener(this);
        tv_record_state = (TextView)findViewById(R.id.tv_record_state);

        contentLayout.setBackgroundColor(Util.getDarkerColor(color));
        contentLayout.addView(visualizerView, 0);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*case R.id.btn_code_sign:
                codeSign();
                break;*/
            case R.id.btn_scan:
                cameraPermission();
                break;
        }

    }

    private void cameraPermission() {
        AndPermission.with(this)
                .requestCode(REQUEST_CODE_CAMERA)
                .permission(Manifest.permission.CAMERA)
                .callback(permissionListener)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        AndPermission.rationaleDialog(
                                AudioRecorderActivity1.this, rationale).
                                show();
                    }
                })
                .start();
    }

    public void codeSign() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        dialog = adb.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_avtivitycode, null);
        final EditText codeEt = (EditText)view.findViewById(R.id.activityCodeEt);
        Button btnOk = (Button)view.findViewById(R.id.okBtn);
        Button btnCancle = (Button)view.findViewById(R.id.cancleBtn);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String activityCode = codeEt.getText().toString();
                //活动码符合要求的话
                getCodeSign(activityCode);
                dialog.dismiss();
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view,0,0,0,0);
        dialog.show();
    }

    private void getCodeSign(String result) {

        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map = gson.fromJson(result,map.getClass());
        String sign_type = (String)map.get("sign_type");
        int project_id = (new Double((double)map.get("id"))).intValue();
        ActivityCodeBusiness activityCodeBusiness = new ActivityCodeBusiness(context,String.valueOf(project_id));
        activityCodeBusiness.setHandler(this);

        if (sign_type.equals("project")){
            //活动签到
            activityCodeBusiness.getProjectSign();
        }else {
            //俱乐部签到
            activityCodeBusiness.getClubProjectSign();
        }
    }

    //二维码扫描处理结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (data != null) {
                String content = data.getStringExtra(Consants.CODED_CONTENT);
                getCodeSign(content);
            }

        }
        if(requestCode == REQUEST_CODE_SETTING) {

            Intent intent = new Intent(AudioRecorderActivity1.this,
                    com.yzq.zxinglibrary.android.CaptureActivity.class);
            startActivityForResult(intent, 0);

            /*startActivityForResult(new Intent(this, CaptureActivity.class) , 0);*/

        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (autoStart && !isRecording) {
            toggleRecording(null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            visualizerView.onResume();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onPause() {
        try {
            visualizerView.onPause();
        } catch (Exception e) {
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //setResult(RESULT_CANCELED);
        try {
            visualizerView.release();
        } catch (Exception e) {
        }
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(AndroidAudioRecorder.EXTRA_FILE_PATH, filePath);
        outState.putInt(AndroidAudioRecorder.EXTRA_COLOR, color);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onAudioChunkPulled(AudioChunk audioChunk) {
        float amplitude = isRecording ? (float) audioChunk.maxAmplitude() : 0f;
        visualizerHandler.onDataReceived(amplitude);
    }

    private void selectAudio() {
       // setResult(RESULT_OK);
    }

    public void toggleRecording(View v) {
        Util.wait(100, new Runnable() {
            @Override
            public void run() {
                if (isRecording) {
                    stopRecording();
                } else {
                    resumeRecording();
                }
            }
        });
    }

    private void resumeRecording() {
        isRecording = true;
        visualizerHandler = new VisualizerHandler();
        visualizerView.linkTo(visualizerHandler);

        if (recorder == null) {
            startRecordTime = String.valueOf(System.currentTimeMillis());
            recorder = OmRecorder.wav(
                    new PullTransport.Default(Util.getMic(source, channel, sampleRate), AudioRecorderActivity1.this),
                    new File(filePath));
        }
        recorder.resumeRecording();

        startTimer();
    }

    private void stopRecording(){
        visualizerView.release();
        if(visualizerHandler != null) {
            visualizerHandler.stop();
        }

        recorderSecondsElapsed = 0;
        if (recorder != null) {
            recorder.stopRecording();
            recorder = null;
        }

        stopTimer();
    }
    private void startTimer() {
        stopTimer();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTimer();
            }
        }, 0, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    final TimeCount timeCount = new TimeCount(10000,1000);
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        public void onFinish() {
                stopRecording();
                //selectAudio();
                tv_record_state.setText("签到成功");
                recordView.setBackground(getResources()
                        .getDrawable(R.mipmap.record_success));
                String path = StringUtil.getRecordFilePath();
                uploadRecord(path);

        }
        public void onTick(long millisUntilFinished) {
            recordView.setClickable(false);
            tv_record_state.setText("正在录音，还剩"+millisUntilFinished / 1000 + "秒");
        }
    }

    private void updateTimer() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isRecording) {
                    recordView.setBackground(getResources().getDrawable(R.mipmap.record_press));

                    if (recordView.isClickable()) {
                        timeCount.start();
                    }
                }
            }
        });
    }

    private void uploadRecord(String path) {

        String session_id = java.util.UUID.randomUUID().toString();
        UploadRecordBusiness uploadRecordBusiness =
                new UploadRecordBusiness(this,
                        session_id,path, startRecordTime);
        uploadRecordBusiness.setHandler(this);
        uploadRecordBusiness.uploadRecord();
    }

    @Override
    public void onUploadRecordSuccess(String s) {
       // showToast(s);
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AudioRecorder Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onActivityCodeSuccess(String string) {

        btn_scan.setText(string+"");
        btn_scan.setEnabled(false);
        //#d4237a
    }

    @Override
    public void onBackClick() {
        startIntent(MainActivity.class);
    }

    @Override
    public void onRightClick() {
    }

    /**
     * 权限的回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA: {
                    Intent intent = new Intent(AudioRecorderActivity1.this,
                            com.yzq.zxinglibrary.android.CaptureActivity.class);
                    startActivityForResult(intent, 0);
                    /*startActivityForResult(new Intent(AudioRecorderActivity1.this, CaptureActivity.class) , 0);*/
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA: {
                    Toast.makeText(AudioRecorderActivity1.this, "请求权限失败了", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(AudioRecorderActivity1.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                /*AndPermission.defaultSettingDialog(MainActivity.this, REQUEST_CODE_SETTING).show();*/

                // 第二种：用自定义的提示语。
                AndPermission.defaultSettingDialog(AudioRecorderActivity1.this, REQUEST_CODE_SETTING)
                        .setTitle("权限申请失败")
                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                        .setPositiveButton("好，去设置")
                        .show();
            }
        }
    };
}