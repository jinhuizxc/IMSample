package com.example.imsample.base;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.example.imsample.framework.helper.MySQLiteOpenHelper;
import com.example.imsample.framework.helper.SharedPrefHelper;
import com.example.imsample.dao.DaoMaster;
import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.greenrobot.greendao.query.QueryBuilder;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

import static cn.jpush.im.android.api.JMessageClient.FLAG_NOTIFY_SILENCE;

/**
 * 应用名称IMSample
 * 应用图标
 * AppKey
 * AppKey 释义
 * <p>
 * AppKey 为极光平台应用的唯一标识。
 * 5a5123c7a098eb78a3c41ee5
 *
 * Master Secret
 * 用于服务器端 API 调用时与 AppKey 配合使用达到鉴权的目的，请保管好 Master Secret 防止外泄。
 * 4bab71a421a662f6e1e494b6
 *
 * 极光im
 * https://docs.jiguang.cn/jpush/client/Android/android_guide/
 *
 * # 也许是最良心的开源表情键盘解决方案
 * https://juejin.im/entry/56f56ac16be3ff005b946093
 * https://github.com/w446108264/XhsEmoticonsKeyboard
 * # AndroidEmoji
 * https://github.com/w446108264/AndroidEmoji
 *
 */
public class BaseApplication extends Application {

    public static BaseApplication baseApplication;
    private Context mContext;
    public MySQLiteOpenHelper helper;
    private DaoMaster master;
    private SharedPrefHelper sharedPrefHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        baseApplication = this;
        mContext = BaseApplication.this;

        // 本地持久化
        sharedPrefHelper = SharedPrefHelper.getInstance();
        sharedPrefHelper.setRoaming(true);

        //初始化数据库
        initDaoDatabase();
        //初始化utils
        Utils.init(this);

        // 统一logger日志打印
        initLogger();

        //实例化极光推送
        JPushInterface.init(mContext);
        //开启极光调试
        JPushInterface.setDebugMode(true);
        //实例化极光IM,并自动同步聊天记录
        JMessageClient.init(getApplicationContext(), true);
        //初始化极光sms
//        SMSSDK.getInstance().initSdk(mContext);

        //通知管理,通知栏开启，其他关闭
        JMessageClient.setNotificationFlag(FLAG_NOTIFY_SILENCE);

        //推送状态
        initJPush2();

    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(5)        // (Optional) Skips some method invokes in stack trace. Default 5
//        .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("IMSample")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();
        //  logger open or close
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
    }

    private void initJPush2() {
        sharedPrefHelper.setMusic(false);
        sharedPrefHelper.setVib(false);
        sharedPrefHelper.setAppKey("5a5123c7a098eb78a3c41ee5");
    }

    private void initDaoDatabase() {
        //是否开启调试
        MigrationHelper.DEBUG = true;
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        //数据库升级
        helper = new MySQLiteOpenHelper(mContext, "text");
        master = new DaoMaster(helper.getWritableDb());

    }

}
