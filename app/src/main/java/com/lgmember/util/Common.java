package com.lgmember.util;

/**
 * Created by Yanan_Wu on 2017/1/5.
 */

public class Common {

    //sp的名称
    public static String SP_NAME = "sp_name";

    //sp保存是否推送消息
    public static String SP_IF_TUISONG = "sp_if_tuisong";

    //sp保存是否自动录音
    public static String SP_IF_RECORDER = "sp_if_recorder";

    //版本更新时的时间计算
    public static String SP_VERSION_UPDATE_TIME = "sp_version_update_time";

    public static boolean FLAG = false;
   /*//老服务器基地址
    public static  String URL_BASE = "http://221.212.177.245:8080/front/";*/
    //新服务器基地址
    public static  String URL_BASE = "http://221.212.183.238/";
    //图片基地址
    public static  String URL_IMG_BASE = URL_BASE+"project_img/";
    //版本更新
    public static String URL_VERSION = URL_BASE+ "android-app-version";
    //下载apk
    public static String URL_APK = URL_BASE+ "android.apk";
    //登录网址
    public static String URL_LOGIN = URL_BASE+ "login";
    //注册网址
    public static String URL_REGISTER = URL_BASE+"mobile-reg";
    //游客登录
    public static String URL_GUEST_LOGIN = URL_BASE+ "guest-login";
    //判断当前是否是游客登录
    public static String URL_IF_GUEST_LOGIN = URL_BASE+ "get-member-name";
    //请求手机验证码
    public static String URL_REQUEST_CODE = URL_BASE+"sms-capt";
    //判断手机验证码是否正确
    public static String URL_VALIDATE_SMS_CAPT = URL_BASE+"validate-sms-capt";
    //修改密码
    public static String URL_MODIFY_PWD = URL_BASE+"pwd";
    //获取个人资料
    public static String URL_MEMBER_MESSAGE = URL_BASE+"profile";
    //更新个人资料
    public static String URL_EDIT_MEMBER_MESSAGE = URL_BASE+"profile-update";
    //积分规则
    public static String URL_SCORES_RULE = URL_BASE+"point/rule";
    //历史积分
    public static String URL_HISTORY_SCORES = URL_BASE+"point/history";
    //积分消息
    public static String URL_SCORES_INFORMATION = URL_BASE+"point/information";
    //返回会员姓名
    public static String URL_GET_MEMBER_NAME = URL_BASE+"get-member-name";
    //活动码签到
    public static String URL_PROJECT_SIGN = URL_BASE+"project2/sign-in";
    //俱乐部签到
    public static String URL_CLUB_PROJECT_SIGN = URL_BASE+"project2/sign-in";
    //会员活动报名
    public static String URL_ACTIVITY_JOIN = URL_BASE+"project/check-in2";
    //会员活动列表
    public static String URL_PROJECT = URL_BASE+"project";
    //以往报名
    public static String URL_ALREAD_JOIN = URL_BASE+"project2/history";
    //即将参与
    public static String URL_SOON_JOIN = URL_BASE+"project2/future";
    //热门活动
    public static String URL_HOT = URL_BASE+"project2/popular";
    //新的活动列表
    public static String URL_PROJECT_MESSAGE_ALLLIST = URL_BASE+"project2/all";
    //会员招募信息列表
    public static String URL_PROJECT_MESSAGE = URL_BASE+"project-message";
    //会员招募信息详情
    public static String URL_PROJECT_MESSAGE_DETAIL = URL_BASE+"app-project-message";
    //会员标签列表
    public static String URL_TAGS_LIST = URL_BASE+"project2/get-tag-list";
    //全部礼物列表
    public static String URL_EXCHANGE_ALL_GIGT = URL_BASE+"point/giftlist";
    //筛选后的礼物列表
    public static String URL_EXCHANGE_SELECT_GIGT = URL_BASE+"point/giftexchangelist";
    //点击某行礼物显示该礼物的详细信息
    public static String URL_EXCHANGE_GIFT_INFO = URL_BASE+"gift/information";
    //积分兑换礼品
    public static String URL_EXCHANGE_GIFT = URL_BASE+"point/exchange";
    //消息列表
    public static String URL_MESSAGE_LIST = URL_BASE+"message/list";
    //提醒消息列表
    public static String URL_REMIND_LIST = URL_BASE+"remind/list";
    //获取各个阅读状态数量
    public static String URL_NOREAD_MESSAGE_NUM = URL_BASE+"message/get-count";
    //标记是否为已读消息
    public static String URL_IF_READ = URL_BASE+"remind/read";
    //删除消息
    public static String URL_DELETE_MESSAGE = URL_BASE+"message/delete";
    //更新消息状态
    public static String URL_UPDATE_MESSAGE_STATE = URL_BASE+"message/update";
    //各个状态的卡券列表
    public static String URL_CARD_LIST = URL_BASE+"coupon";
    //领取卡券
    public static String URL_GET_CARD = URL_BASE+"coupon/get";
    //领取提醒里的卡券
    public static String URL_GET_REMIND_CARD = URL_BASE+"get";
    //领取卡券兑换码
    public static String URL_CARD_CODE = URL_BASE+"coupon/code";
    //创建问题反馈
    public static String URL_CREATE_FEEDBACK = URL_BASE+"create-feedback";
    //问题反馈列表
    public static String URL_FEEDBACK_LIST = URL_BASE+"feedback";
    //删除问题反馈
    public static String URL_DELETE_FEEDBACK = URL_BASE+"delete-feedback";
    //申请实名认证
    public static String URL_CERTIFICATION = URL_BASE+"auth";
    //照片上传
    public static String URL_UPLOAD_IMG = URL_BASE+"upload";
    //收藏列表
    public static String URL_COLLECTION_LIST = URL_BASE+"project2/saved";
    //添加收藏
    public static String URL_ADD_COLLECTION = URL_BASE+"favorite/add";
    //删除收藏
    public static String URL_DELETE_COLLECTION = URL_BASE+"favorite/delete";
    //忘记密码
    public static String URL_FORGET_PASSWORD = URL_BASE+"forgotpwd";
    //更新头像
    public static String URL_UPDATE_PHOTO = URL_BASE+"avatar-update";
    //上传录音
    public static String URL_UPLOAD_RECORD = URL_BASE+"radio-record/upload";
    //录音节目识别结果查询
    public static String URL_RECORD_RESULT = URL_BASE+"radio-record/query";
    //录音识别列表
    public static String URL_RECORD_LIST = URL_BASE+"radio-record";
    //图形验证码
    public static String URL_CPT = URL_BASE+"cpt";

}
