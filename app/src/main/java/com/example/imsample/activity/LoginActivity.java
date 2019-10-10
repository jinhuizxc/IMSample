package com.example.imsample.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.imsample.R;
import com.example.imsample.base.BaseActivity;
import com.example.imsample.framework.helper.SharedPrefHelper;
import com.orhanobut.logger.Logger;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by wapchief on 2017/5/8 0008 下午 3:39.
 * 描述：注册登录
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_username)
    EditText loginUsername;
    @BindView(R.id.login_passWord)
    EditText loginPassWord;
    @BindView(R.id.login_code)
    EditText loginCode;
    @BindView(R.id.login_code_bt)
    Button loginCodeBt;
    @BindView(R.id.login_submit)
    Button loginSubmit;
    @BindView(R.id.login_ok)
    Button loginOk;
    @BindView(R.id.title_bar_back)
    ImageView mTitleBarBack;
    @BindView(R.id.title_bar_title)
    TextView mTitleBarTitle;
    @BindView(R.id.title_options_img)
    ImageView mTitleOptionsImg;
    private int time = 60;
    private SharedPrefHelper sharedPrefHelper;
    private UserInfo userInfo;

    Handler handler = new Handler();

    @Override
    protected int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        sharedPrefHelper = SharedPrefHelper.getInstance();
        if (!sharedPrefHelper.getUserId().equals("")) {
            loginUsername.setText(sharedPrefHelper.getUserId());
        }
//        if (!sharedPrefHelper.getUserId().equals("")) {
//            loginPassWord.setText(sharedPrefHelper.getUserPW());
//        }
        mTitleBarBack.setVisibility(View.GONE);
        mTitleOptionsImg.setVisibility(View.GONE);
        mTitleBarTitle.setText("注册登录");
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.login_code_bt, R.id.login_submit, R.id.login_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_code_bt:
//                startCount();
//                SMSSDK.getInstance().getSmsCodeAsyn(loginUsername.getText().toString(), "1", new SmscodeListener() {
//                    @Override
//                    public void getCodeSuccess(String s) {
//                        loginCode.setText(s);
//                    }
//
//                    @Override
//                    public void getCodeFail(int i, String s) {
//                        showToast(LoginActivity.this, s);
//                    }
//                });
                break;
            case R.id.login_submit:
                //注册
                JMessageClient.register(loginUsername.getText().toString(), loginPassWord.getText().toString(), new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        Logger.d("注册返回结果 ->" + i + " , " + s);
                        switch (i) {
                            case 0:
                                ToastUtils.showShort("注册成功");
                                initLogin(loginUsername.getText().toString(), loginPassWord.getText().toString(), 1);
                                break;
                            case 898001:
                                ToastUtils.showShort("用户名已存在");
                                break;
                            case 871301:
                                ToastUtils.showShort("密码格式错误");
                                break;
                            case 871304:
                                ToastUtils.showShort("密码错误");
                                break;
                            default:
                                ToastUtils.showShort("s");
                                break;
                        }
                    }
                });
                break;
            case R.id.login_ok:
                //登录
//                Log.e("info2============",""+JMessageClient.getMyInfo());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initLogin(loginUsername.getText().toString(), loginPassWord.getText().toString(), 0);
                    }
                }, 500);
                break;

        }
    }

    // 验证码按钮
//    public void startCount() {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                time--;
//                if (time <= 0) {
//                    mHandler.sendEmptyMessage(5);
//                } else {
//                    mHandler.sendEmptyMessage(4);
//                    mHandler.postDelayed(this, 1000);
//                }
//            }
//        };
//        new Thread(runnable).start();
//    }

//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    // hidenSoft(mUserPwd);
//                    // if (tomain) {
//                    // Intent i = new Intent(LoginActivity.this,
//                    // MainActivity.class);
//                    // LoginActivity.this.startActivity(i);
//                    // overridePendingTransition(R.anim.anim_enter,
//                    // R.anim.anim_exit);
//                    // }
//                    setResult(RESULT_OK);
//                    LoginActivity.this.finish();
//                    break;
//                case 4:
//                    loginCodeBt.setEnabled(false);
//                    loginCodeBt.setTextColor(Color.rgb(32, 32, 32));
//                    loginCodeBt.setText("已发送(" + String.valueOf(time) + ")");
//                    break;
//                case 5:
//                    loginCodeBt.setText("重新获取验证码");
//                    loginCodeBt.setEnabled(true);
//                    time = 60;
//                    break;
//            }
//
//        }
//    };

    /**
     * @param userName
     * @param passWord
     */
    private void initLogin(String userName, String passWord, final int type) {
        showProgressDialog("正在登录...");
        JMessageClient.login(userName, passWord, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Logger.d("登录返回结果 ->" + i + " , " + s);
                dismissProgressDialog();
                switch (i) {
                    case 801003:
                        ToastUtils.showShort("用户名不存在");
                        break;
                    case 871301:
                        ToastUtils.showShort("密码格式错误");
                        break;
                    case 801004:
                        ToastUtils.showShort("密码错误");
                        handler.sendEmptyMessage(-1);
                        break;
                    case 0:
                        ToastUtils.showShort("登录成功");
                        sharedPrefHelper.setUserId(loginUsername.getText().toString());
                        sharedPrefHelper.setUserPW(loginPassWord.getText().toString());
                        initUserInfo(loginUsername.getText().toString(), type);
                        break;
                    default:
                        break;
                }

            }
        });
    }

    //初始化个人资料
    public void initUserInfo(String id, final int type) {
        showProgressDialog("正在初始化数据");
        JMessageClient.getUserInfo(id, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                dismissProgressDialog();
                if (i == 0) {
//                    Log.e("info-Login", ""+JMessageClient.getMyInfo()+"\n"+JMessageClient.getConversationList()+"\n"+userInfo);
                    Intent intent = new Intent(LoginActivity.this
                            , MainActivity.class);
                    intent.putExtra("LOGINTYPE", type);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            dialog();
//        }
//        return false;
//    }

    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        System.exit(0);
                    }
                });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

//    private static Boolean isExit = false;
//    /*单击回退*/
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exitBy2Click();
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    /*双击退出*/
//    private void exitBy2Click() {
//        Timer tExit = null;
//        if (isExit == false) {
//            isExit = true; // 准备退出
//            showLongToast(this, "再按一次退出程序");
//            tExit = new Timer();
//            tExit.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    isExit = false; // 取消退出
//                }
//            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
//
//        } else {
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(0);
//
//            ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//            manager.killBackgroundProcesses(getPackageName());
//        }
//    }
}
