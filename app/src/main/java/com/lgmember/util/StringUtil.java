package com.lgmember.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ImageReader;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.lgmember.activity.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yanan_Wu on 2017/1/6.
 */

public class StringUtil {


    /*获取网络图片*/
    static Bitmap bitmap;
    public static void setNetworkBitmap(Context context,final String path, ImageView imageView) {

        Runnable networkImg = new Runnable() {
            @Override
            public void run() {
                try {
                    URL httpUrl = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                    conn.setConnectTimeout(6000);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    InputStream in = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkImg).start();
        while(bitmap == null)
            continue;
        imageView.setBackground(new BitmapDrawable(context.getResources(),bitmap));
    }
    /*
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    /**
     * 图片按比例大小压缩方法
     * @param srcPath （根据路径获取图片并压缩）
     * @return
     */
    public static Bitmap getimage(String srcPath) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 480f;// 这里设置高度为800f
        float ww = 640f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }
    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 保存文件
     * @param bm
     * @throws IOException
     */
    public static void saveFile(Bitmap bm, String outPath) throws IOException {
        File myCaptureFile = new File(outPath);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    /*
    * 时间比较*/
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                //System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                //System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    /**
     * 判断是否有外部存储设备sdcard
     * @return true | false
     */
    public static boolean isSdcardExit(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    public static String getRecordFilePath(){

        String mAudioWavPath = "";
        if(isSdcardExit()){
            String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mAudioWavPath = fileBasePath+"/recorded_audio.wav";
        }
        return mAudioWavPath;
    }

    public static String[] NATIONS = {"汉族", "蒙古族", "回族", "藏族", "维吾尔族", "苗族", "彝族",
            "壮族", "布依族", "朝鲜族", "满族", "侗族", "瑶族", "白族", "土家族", "哈尼族",
            "哈萨克族", "傣族", "黎族", "傈傈族", "佤族", "畲族", "高山族", "拉祜族", "水族",
            "东乡族", "纳西族", "景颇族", "柯尔克孜族", "土族", "达翰尔族", "仫佬族", "羌族",
            "布朗族", "撒拉族", "毛南族", "仡佬族", "锡伯族", "阿昌族", "普米族", "塔吉克族",
            "怒族", "乌孜别克族", "俄罗斯族", "鄂温克族", "德昂族", "保安族", "裕固族", "京族",
            "塔塔尔族", "独龙族", "鄂伦春族", "赫哲族", "门巴族", "珞巴族", "基诺族", "外籍人士"};
    public static String[] EDUCATIONS = {"初中","高中","专科","本科","硕士","博士"};
    public static String[] GENDER = {"男","女"};

    //身份证号验证
    public static boolean userCardCheck(String userCard) {
        if (userCard.matches("^(\\d{14}|\\d{17})(\\d|[xX])$")) {
            return true;
        }
        return false;
    }

    //手机号验证
    public static boolean isPhone(String phone) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    //判断两次密码是否一致
    public static boolean isNewPwdEquallyConfirmPwd(String newPwd,String confirmPwd) {
        if (newPwd.equals(confirmPwd)) {
            return true;
        }
        return false;
    }

    //密码验证,为8-20位
    public static boolean isPassword(String password) {
        if (password.matches("^[0-9A-Za-z]{8,20}$")) {
            return true;
        }
        return false;
    }

    //判断参数是否全是数字
    public static boolean isAllNumber(String s){
        for(int i = s.length();--i >= 0;){
            if (!Character.isDigit(s.charAt(i))){
                return false;
            }
        }
        return true;
    }
    //bitmap 转 base64
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        final String result = base64Data.substring(base64Data.lastIndexOf(","),base64Data.length());
        byte[] bytes = Base64.decode(result, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public static int codeTomsg(int code){
        int codeArray[] = {R.string.code_1,R.string.code_2,R.string.code_3,R.string.code_4,R.string.code_5,R.string.code_6,
                R.string.code_7,R.string.code_8,R.string.code_9,R.string.code_10,R.string.code_11,};
        for (int i=1;i<=codeArray.length;i++){
            if (i == code){
                return codeArray[i-1];
            }
        }
        return 0;
    }

    //根据身份证号，获取年龄

    public static int IDcard2Age(String idno){
        int leh = idno.length();
        String dates = "";
        if (leh == 18){
            dates = idno.substring(6,10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year = df.format(new Date());
            int u = Integer.parseInt(year)-Integer.parseInt(dates);
            return u;
        }else {
            dates = idno.substring(6,8);
            return Integer.parseInt(dates);
        }
    }

    //boolean 转换为是与否
    public static String boolean2String(Boolean b){

        if (b == true){
            return "是";
        }else {
            return "否";
        }
    }

    //实名认证转换
    public static String authorized2String(int i) {
        if (i == 0) {
            return "未实名认证";
        }
        if (i == 1) {
            return "已实名认证";
        }
        if (i == 2) {
            return "已提交实名认证";
        }
        if (i == 3) {
            return "提交没有通过";
        }
    return "";
    }

    //报名状态
    public static String numToJoinState(int num){
        if (num == -1){
            return "报名参与";
        }else if (num == 0){
            return "报名未签到";
        }else if(num == 1){
            return "报名且签到";
        }else if(num == 2){
            return "未报名但签到";
        }else if (num == 3){
            return "后补报名";
        }
        return null;
    }

    //会员状态

    public static String numToState(int num){

        String stateArray[] = {"正常","锁定","删除"};
        for (int i =1; i<=stateArray.length;i++){
            if (i == num){
                return stateArray[i-1];
            }
        }
        return null;
    }

    public static String numToNation(int num){
        String nationArray[] = {"汉族","蒙古族","回族","藏族","维吾尔族","苗族","彝族","壮族","布依族","朝鲜族"
                ,"满族","侗族","瑶族","白族","土家族","哈尼族","哈萨克族","傣族","黎族","傈傈族"
                ,"佤族","畲族","高山族","拉祜族","水族","东乡族","纳西族","景颇族","柯尔克孜族","土族"
                ,"达翰尔族","仫佬族","羌族","布朗族","撒拉族","毛南族","仡佬族","锡伯族","阿昌族","普米族"
                ,"塔吉克族","怒族","乌孜别克族","俄罗斯族","鄂温克族","德昂族","保安族","裕固族","京族","塔塔尔族"
                ,"独龙族","鄂伦春族","赫哲族","门巴族","珞巴族","基诺族"};
        for (int i = 0;i<nationArray.length;i++){
            if (i == num){
                return nationArray[i];
            }else if (num == 90){
                return "外籍人士";
            }
        }
        return null;
    }

    public static String numToEducation(int num){

        String eduArray[] = {"初中","高中","专科","本科","硕士","博士"};
        for (int i =0; i<eduArray.length;i++){
            if (i == num){
                return eduArray[i];
            }
        }
        return null;
    }
    public static String numToSoure(int num){

        String sourceArray[] = {"APP会员中心","微信会员中心","网页会员中心","线下会员中心","其它"};
        for (int i =1; i<=sourceArray.length;i++){
            if (i == num){
                return sourceArray[i-1];
            }
        }
        return null;
    }
    public static String numToLevels(int num){

        String levelsArray[] = {"红卡","银卡","金卡","钻石卡"};
        for (int i =1; i<=levelsArray.length;i++){
            if (i == num){
                return levelsArray[i-1];
            }
        }
        return null;
    }
    public static String numToGender(boolean b){

        if (b){
            return "女";
        }else {
            return "男";
        }
    }



}

