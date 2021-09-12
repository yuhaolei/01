package com.one.eng.internetspeed;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

public class UseForUnity {

    private NetSpeedTimer mNetSpeedTimer;
    private String mNetSpeed;

    /// 设置一个 Activity 参数
    private static Activity _unityActivity;
    // 通过反射获取 Unity 的 Activity 的上下文
    public static Activity getActivity() {

        if (null == _unityActivity) {
            try {
                Class<?> classtype = Class.forName("com.unity3d.player.UnityPlayer");
                Activity activity = (Activity) classtype.getDeclaredField("currentActivity").get(classtype);
                _unityActivity = activity;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return _unityActivity;
    }

    // 开始轮询网速
    // 1s后启动任务，每2s更新一次
    public void StartNetSpeed() {
        //创建NetSpeedTimer实例
        mNetSpeedTimer = new NetSpeedTimer(getActivity(), new NetSpeed(), mHandler).setDelayTime(1000).setPeriodTime(2000);
        //在想要开始执行的地方调用该段代码
        mNetSpeedTimer.startSpeedTimer();
    }

    // 获得网速
    public String GetNetSpeed() {
        return mNetSpeed;
    }

    // 关闭轮询网速
    public void CloseNetSpeed() {
        if (null != mNetSpeedTimer) {
            mNetSpeedTimer.stopSpeedTimer();
        }
    }

    private Handler mHandler = new Handler() {
        private static final String TAG = "UnitTest";

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == NetSpeedTimer.NET_SPEED_TIMER_DEFAULT) {
                mNetSpeed = (String) msg.obj;
                // Log.i(TAG, "current net speed  = " + mNetSpeed);
            }
        }
    };
}

