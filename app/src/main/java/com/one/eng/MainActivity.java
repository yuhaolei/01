package com.one.eng;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.one.eng.utils.StatusBarUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RadioGroup rg_main;
    RadioButton rb_main_rule;
    RadioButton rb_main_use;
    RadioButton rb_main_use_buy;
    RadioButton rb_main_person;
    NoScrollViewPager vp_container_no_scroll;

    private int mCurrentIndex = 0;
    private ArrayList<Fragment> mFragmentList;


    /**
     * 沉浸式状态栏
     * @param isBlack 状态栏字体颜色是否为黑色
     */
    protected void setStatusBarImmersive(boolean isBlack){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (isBlack) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//————————————————
//        版权声明：本文为CSDN博主「惜墨儿」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/lovemuchcode/article/details/45480925
        setContentView(R.layout.activity_main);


        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
//        setStatusBarImmersive(true);

        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }

        rg_main=findViewById(R.id.rg_main);
        rb_main_rule=findViewById(R.id.rb_main_rule);
        rb_main_use=findViewById(R.id.rb_main_use);
        rb_main_use_buy=findViewById(R.id.rb_main_use_buy);
        rb_main_person= findViewById(R.id.rb_main_person);
        vp_container_no_scroll=findViewById(R.id.vp_container_no_scroll);
        initViews();
    }

    private void initViews() {
        //初始化底部导航栏
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId) {
                    case R.id.rb_main_rule:
                        setIndexSelected(0);
                        break;
                    case R.id.rb_main_use:
                        setIndexSelected(1);
                        break;
                    case R.id.rb_main_use_buy:
                        setIndexSelected(2);
                        break;
                    case R.id.rb_main_person:
                        setIndexSelected(3);
                        break;
                    default:
                        break;
                }
            }
        });

        //初始化viewPager
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new RuleFragment());
        mFragmentList.add(new UseFragment());
        mFragmentList.add(new ShareFragment());//buy 第三个 语文
        //mFragmentList.add(new ChatFragment());
        mFragmentList.add(new PersonFragment());

        vp_container_no_scroll.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(),mFragmentList));
        vp_container_no_scroll.addOnPageChangeListener(new MainOnPageChangeListener());
        vp_container_no_scroll.setCurrentItem(mCurrentIndex);
        vp_container_no_scroll.setOffscreenPageLimit(3);
    }

    private void setIndexSelected(int index) {
        if (mCurrentIndex == index) {
            return;
        }
        vp_container_no_scroll.setCurrentItem(index, false);
        mCurrentIndex = index;
    }

    public class MainOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    rb_main_rule.setChecked(true);
                    break;
                case 1:
                    rb_main_use.setChecked(true);
                    break;
                case 2:
                    rb_main_use_buy.setChecked(true);
                    break;
                case 3:
                    rb_main_person.setChecked(true);
                    break;
                default:
                    break;

            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}