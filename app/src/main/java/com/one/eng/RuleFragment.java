package com.one.eng;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.one.eng.adapter.EngAdapter;
import com.one.eng.bean.EngBean;

import java.util.ArrayList;
import java.util.List;

public class RuleFragment extends androidx.fragment.app.Fragment {
    private View ruleView;
    private RelativeLayout rl_chat;
    private RelativeLayout rl_buy;
    private ListView listView_rule;
    private List<EngBean> engRuleList =
            new ArrayList<>();


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

        rl_chat = view.findViewById(R.id.rl_chat);
        rl_chat.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ChatActivity.class);
                startActivity(intent);
            }
        });

        rl_buy = view.findViewById(R.id.rl_buy);
        rl_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DrawActivity.class);
                startActivity(intent);
            }
        });



        // 先拿到数据并放在适配器上
        initEngRuleList(); //初始化英语语法列表数据
        EngAdapter adapter =
                new EngAdapter(getActivity(),
                        R.layout.eng_item,
                        engRuleList);

        // 将适配器上的数据传递给listView
        listView_rule.setAdapter(adapter);

        // 为ListView注册一个监听器，当用户点击了ListView
        // 中的任何一个子项时，就会回调onItemClick()方法
        // 在这个方法中可以通过position参数判断出用户点击的是那一个子项
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
    }

    private void initEngRuleList() {

        for (int i = 0; i < 30; i++) {
            EngBean a = new EngBean("名词",
                    R.drawable.writte, "https" +
                    "://sc.chinaz.com/");
            engRuleList.add(a);
            EngBean b = new EngBean("代词",
                    R.drawable.happy, "https" +
                    "://www.51voa.com/");
            engRuleList.add(b);
            EngBean c = new EngBean("动词",
                    R.drawable.buy, "https" +
                    "://www.cpsenglish" +
                    ".com/question/7994");
            engRuleList.add(c);
            EngBean d = new EngBean("形容词",
                    R.drawable.all, "https" +
                    "://www.cnblogs" +
                    ".com/adamjwh/p/11018268" +
                    ".html");
            engRuleList.add(d);
        }
    }
}
