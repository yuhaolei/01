package com.one.eng;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.one.eng.customview.DrawView;


public class DrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);



        initView();
    }

    private void initView() {

        DrawView drawView = (DrawView) findViewById(R.id.drawView1);

        Button bt_clear = findViewById(R.id.bt_clear);
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawView.clearScreen();
            }
        });
    }

    // 处理菜单事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawView drawView = (DrawView) findViewById(R.id.drawView1);
        switch (item.getItemId()) {
            // 设置id为menu_exit的菜单子项所要执行的方法。
            case R.id.menu1_sub1:
                drawView.paint.setStrokeWidth(1);
                break;
            case R.id.menu1_sub2:
                drawView.paint.setStrokeWidth(5);
                break;
            case R.id.menu1_sub3:
                drawView.paint.setStrokeWidth(10);
                break;
            case R.id.menu1_sub4:
                drawView.paint.setStrokeWidth(50);
                break;
            case R.id.menu2:
                drawView.paint.setColor(Color.BLACK);
                break;
            case R.id.menu3:
                drawView.paint.setColor(Color.WHITE);
                drawView.paint.setStrokeWidth(100);
                break;
            case R.id.menu4:
                try {
                    drawView.saveBitmap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.menu5:
                System.exit(0);// 结束程序
                break;
            case R.id.clear:
                drawView.clearScreen();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}