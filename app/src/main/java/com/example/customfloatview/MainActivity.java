package com.example.customfloatview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.customfloatview.event.FloatViewShowMessageEvent;
import com.example.customfloatview.util.AsyncUtil;
import com.example.customfloatview.util.SharedPreferencesUtil;
import com.example.customfloatview.util.UIUtil;
import com.example.customfloatview.widget.FloatingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private FloatingView mFloatingView;
    private Button mClickSendDataButton;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch mIsShowButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFloatingView = findViewById(R.id.float_view);
        mClickSendDataButton = findViewById(R.id.click_send_data);
        mIsShowButton = findViewById(R.id.click_show_switch);
        EventBus.getDefault().register(this);

        //进入页面，设置默认switch的状态
        boolean save_ischeck_state = SharedPreferencesUtil.getBoolean("SAVE_ISCHECK_STATE", false);
        mIsShowButton.setChecked(save_ischeck_state);

        if (save_ischeck_state){
            mFloatingView.setVisibility(View.VISIBLE);
        }else {
            mFloatingView.setVisibility(View.GONE);
        }


        mIsShowButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //监听选中状态是否展示浮动view
                    EventBus.getDefault().post("ShowFloatView");
                } else {
                    EventBus.getDefault().post("UNSHOWFloatView");
                }
                //保存状态 用于初始化
                SharedPreferencesUtil.putBoolean("SAVE_ISCHECK_STATE", isChecked);
            }
        });

        //给浮动view添加数据
        mClickSendDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        int num = 0;
                        while (true) {
                            EventBus.getDefault().post(new FloatViewShowMessageEvent(String.valueOf(num), String.valueOf(num)));
                            num++;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            if (num > 30) {
                                break;
                            }
                        }
                    }
                });
            }
        });

        //点击浮动view的X关闭
        mFloatingView.setCloseClickListener(new FloatingView.CloseClickListener() {
            @Override
            public void onClick() {
                //同时通知switch关闭
                EventBus.getDefault().post(Constants.CLOSE_FLOAT_VIEW);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMessage(String message) {
        UIUtil.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (message.equals("ShowFloatView")) {
                    mFloatingView.setVisibility(View.VISIBLE);
                } else if (message.equals("UNSHOWFloatView")) {
                    mFloatingView.setVisibility(View.GONE);
                }

                if (message.equals(Constants.CLOSE_FLOAT_VIEW)) {
                    if (mIsShowButton != null) {
                        mIsShowButton.setChecked(false);
                    }
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMessage(FloatViewShowMessageEvent event) {
        //根据内容展示在view上
        UIUtil.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (mFloatingView.getVisibility() == View.VISIBLE) {
                    mFloatingView.setText(event.title, event.message);
                }

            }
        });
    }

}