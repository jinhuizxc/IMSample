package com.example.imsample.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imsample.R;
import com.example.imsample.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wapchief on 2017/8/24.
 * 群组会话
 */

public class ChatMsgGroupActivity extends BaseActivity {

    @BindView(R.id.title_bar_back)
    ImageView mTitleBarBack;
    @BindView(R.id.title_bar_title)
    TextView mTitleBarTitle;
    @BindView(R.id.title_options_img)
    ImageView mTitleOptionsImg;
    private String groupID;
    @Override
    protected int setContentView() {
        return R.layout.activity_group_msg;
    }

    @Override
    protected void initView() {
        groupID = getIntent().getStringExtra("GROUPID");
        mTitleOptionsImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_back));
        mTitleOptionsImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_group_mimi));
        mTitleOptionsImg.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.title_bar_back, R.id.title_options_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.title_options_img:
                Intent intent = new Intent(ChatMsgGroupActivity.this, GroupDetailActivity.class);
                intent.putExtra("GROUPID", groupID);
                startActivity(intent);
                break;
        }
    }

}
