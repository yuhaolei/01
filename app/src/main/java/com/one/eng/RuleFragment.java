package com.one.eng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.one.eng.adapter.EngAdapter;
import com.one.eng.bean.EngBean;
import com.one.eng.internetspeed.NetSpeed;
import com.one.eng.internetspeed.NetSpeedTimer;

import java.util.ArrayList;
import java.util.List;

public class RuleFragment extends androidx.fragment.app.Fragment {
    private View ruleView;
    private RelativeLayout rl_chat;
    private RelativeLayout rl_buy;
    private ListView listView_rule;
    private List<EngBean> engRuleList =
            new ArrayList<>();
    private TextView tv_netspeed_per_second;
    private NetSpeedTimer mNetSpeedTimer;
    private static final String TAG = "RuleFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ruleView =
                inflater.inflate(R.layout.fragment_rule, container, false);
        return ruleView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,
                savedInstanceState);
        listView_rule =
                view.findViewById(R.id.listView_rule);
        tv_netspeed_per_second = view.findViewById(R.id.tv_netspeed_per_second);

        rl_chat = view.findViewById(R.id.rl_chat);
        rl_chat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        });

        rl_buy = view.findViewById(R.id.rl_buy);
        rl_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DrawActivity.class);
                startActivity(intent);
            }
        });


        // ????????????????????????????????????
        initEngRuleList(); //?????????????????????????????????
        EngAdapter adapter =
                new EngAdapter(getActivity(),
                        R.layout.eng_item,
                        engRuleList);

        // ?????????????????????????????????listView
        listView_rule.setAdapter(adapter);

        // ???ListView??????????????????????????????????????????ListView
        // ??????????????????????????????????????????onItemClick()??????
        // ??????????????????????????????position????????????????????????????????????????????????
        listView_rule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EngBean engBean =
                        engRuleList.get(position);
                Toast.makeText(getActivity(),
                        engBean.getName(),
                        Toast.LENGTH_SHORT).show();
                Intent intent =
                        new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("target_url",
                        engBean.getLession_url());
                startActivity(intent);
            }
        });

        initNewWork(getActivity());
    }

    private void initEngRuleList() {

        for (int i = 0; i < 30; i++) {
            EngBean a = new EngBean("??????",
                    R.drawable.writte, "https" +
                    "://sc.chinaz.com/");
            engRuleList.add(a);
            EngBean b = new EngBean("??????",
                    R.drawable.happy, "https" +
                    "://www.51voa.com/");
            engRuleList.add(b);
            EngBean c = new EngBean("??????",
                    R.drawable.buy, "https" +
                    "://www.cpsenglish" +
                    ".com/question/7994");
            engRuleList.add(c);
            EngBean d = new EngBean("?????????",
                    R.drawable.all, "https" +
                    "://www.cnblogs" +
                    ".com/adamjwh/p/11018268" +
                    ".html");
            engRuleList.add(d);
        }
    }


    private void initNewWork(Context mContext) {
        //??????NetSpeedTimer??????
        mNetSpeedTimer = new NetSpeedTimer(mContext, new NetSpeed(), mHandler).setDelayTime(1000).setPeriodTime(2000);
        //????????????????????????????????????????????????
        mNetSpeedTimer.startSpeedTimer();
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == NetSpeedTimer.NET_SPEED_TIMER_DEFAULT) {
                String speed = (String) msg.obj;
                //????????????????????????????????????????????????kb/s
                Log.i(TAG, "current net speed  = " + speed);
                tv_netspeed_per_second.setText(speed);
            }
        }
    };
}
