package com.lgmember.activity.person;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.LoginActivity;
import com.lgmember.activity.MainActivity;
import com.lgmember.activity.R;
import com.lgmember.app.PersistentCookieStore;
import com.lgmember.business.ShowNetworkImgBusiness;
import com.lgmember.business.UpdatePhotoBusiness;
import com.lgmember.business.message.MemberMessageBusiness;
import com.lgmember.model.Member;
import com.lgmember.util.Common;
import com.lgmember.view.TopBarView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

import java.io.File;
import java.util.List;

/**
 * Created by Yanan_Wu on 2017/4/5.
 */

public class PersonalAllActivity extends BaseActivity implements TopBarView.onTitleBarClickListener,View.OnClickListener ,UpdatePhotoBusiness.UpdatePhotoResulHandler,MemberMessageBusiness.MemberMessageResulHandler,ShowNetworkImgBusiness.ShowNetworkImgResulHandler{

    private TopBarView topBar;
    private RelativeLayout rl_photo,rl_personal,rl_certification;
    private ImageView iv_photo;
    private String phone;

    private int flagAuthorized ;

    private static final int REQUEST_CODE_CAMERA = 100;
    private static final int REQUEST_CODE_SETTING = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_all);
        init();
        getData();
    }

    private void init() {
        iv_photo = (ImageView)findViewById(R.id.iv_photo);
        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);

        rl_photo = (RelativeLayout)findViewById(R.id.rl_photo);
        rl_personal = (RelativeLayout)findViewById(R.id.rl_personal);
        rl_certification = (RelativeLayout)findViewById(R.id.rl_certification);

        rl_photo.setOnClickListener(this);
        rl_personal.setOnClickListener(this);
        rl_certification.setOnClickListener(this);
    }

    private void getData() {
        MemberMessageBusiness memberMessage = new MemberMessageBusiness(context);
        memberMessage.setHandler(this);
        memberMessage.getMemberMessage();
    }

    @Override
    public void onBackClick() {
        startIntent(MainActivity.class);
    }

    @Override
    public void onRightClick() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_photo:
                //更新头像
                cameraPermission();
                //uploadImg();
                break;
            case R.id.rl_personal:
                //更新会员信息
                startIntent(PersonalActivity.class);
                break;
            case R.id.rl_certification:
                if (flagAuthorized == 1){
                    showToast("您已通过实名认证");
                }else if (flagAuthorized == 2){
                    showToast("您已提交实名认证,正在审核中");
                }else {
                    Intent intent = new
                            Intent(PersonalAllActivity.this,
                            CertificationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("phone",phone);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
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
                                PersonalAllActivity.this, rationale).
                                show();
                    }
                })
                .start();
    }

    //上传图片
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };

    private int REQUEST_CODE = 0;
    public void uploadImg(){
        ImgSelConfig config = new ImgSelConfig.Builder(this, loader)
                // 是否多选
                .multiSelect(false)
                .btnText("Confirm")
                // 确定按钮背景色
                //.btnBgColor(Color.parseColor(""))
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .backResId(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material)
                .title("Images")
                .titleColor(Color.WHITE)
                .titleBgColor(Color.parseColor("#3F51B5"))
                .allImagesText("All Images")
                .cropSize(1, 1, 200, 200)
                .needCrop(true)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(9)
                .build();

        ImgSelActivity.startActivity(this, config, REQUEST_CODE);
    }

    //上传图片后的处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            //startPhotoZoom(data.getData());
            for (String path : pathList) {

                File file = new File(path);
                if (file.exists()) {
                    UpdatePhoto(file);
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    iv_photo.setBackground(new BitmapDrawable(context.getResources(),bm));
                }
            }
        }
        if(requestCode == REQUEST_CODE_SETTING) {
                uploadImg();
        }
    }




    private void UpdatePhoto(File file) {
        UpdatePhotoBusiness updatePhotoBusiness = new UpdatePhotoBusiness(context,file);
        updatePhotoBusiness.setHandler(this);
        updatePhotoBusiness.updatePhoto();
    }

    private void showNetworkImg(String photoName) {

        ShowNetworkImgBusiness showNetworkImgBusiness = new ShowNetworkImgBusiness(context,photoName);
        showNetworkImgBusiness.setHandler(this);
        showNetworkImgBusiness.showNetworkImg();

    }

    //更新头像
    @Override
    public void onSuccess() {
        showToast("上传成功");

    }

    @Override
    public void onShowImgSuccess() {

        String path = Environment.getExternalStorageDirectory() + "/network.jpg";
        Bitmap bm = BitmapFactory.decodeFile(path);
        iv_photo.setBackground(new BitmapDrawable(context.getResources(),bm));
    }

    @Override
    public void onSuccess(Member member) {

        //后台传过来的图片为空，设置为默认的，否则，就用后台传过来的
        if (member.getAvatar() == null ||
                member.getAvatar().isEmpty()){
            iv_photo.setImageResource(R.drawable.touxiang);
        }else {
            showNetworkImg(member.getAvatar());
        }

        phone = member.getMobile();

       flagAuthorized =  member.getAuthorized();
    }

    @Override
    public void onShowImgFailed(String s) {

        iv_photo.setBackground(getResources().getDrawable(R.drawable.touxiang));

    }

    /**
     * 权限的回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA: {
                    uploadImg();
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA: {
                    Toast.makeText(PersonalAllActivity.this, "请求权限失败了", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(PersonalAllActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                /*AndPermission.defaultSettingDialog(MainActivity.this, REQUEST_CODE_SETTING).show();*/

                // 第二种：用自定义的提示语。
                AndPermission.defaultSettingDialog(PersonalAllActivity.this, REQUEST_CODE_SETTING)
                        .setTitle("权限申请失败")
                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                        .setPositiveButton("好，去设置")
                        .show();
            }
        }
    };


}
