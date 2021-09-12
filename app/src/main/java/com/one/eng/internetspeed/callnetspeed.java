package com.one.eng.internetspeed;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class callnetspeed {
    private NetSpeedTimer mNetSpeedTimer;
    private static final String TAG = "callnetspeed";

    private void initNewWork(Context mContext) {
        //创建NetSpeedTimer实例
        mNetSpeedTimer = new NetSpeedTimer(mContext, new NetSpeed(), mHandler).setDelayTime(1000).setPeriodTime(2000);
        //在想要开始执行的地方调用该段代码
        mNetSpeedTimer.startSpeedTimer();
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == NetSpeedTimer.NET_SPEED_TIMER_DEFAULT) {
                String speed = (String) msg.obj;
                //打印你所需要的网速值，单位默认为kb/s
                Log.i(TAG, "current net speed  = " + speed);
            }
        }
    };
}
