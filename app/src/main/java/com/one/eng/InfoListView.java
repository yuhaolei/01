package com.one.eng;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author yhl
 * @date 2021/5/12
 * @des 解决ScrollView嵌套ListView冲突
 */
public class InfoListView extends ListView {
    public InfoListView(Context context) {
        super(context);
    }

    public InfoListView(Context context,
                        AttributeSet attrs) {
        super(context, attrs);
    }

    public InfoListView(Context context,
                        AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public InfoListView(Context context,
                        AttributeSet attrs,
                        int defStyleAttr,
                        int defStyleRes) {
        super(context, attrs, defStyleAttr,
                defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec
                ,
                MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2
                        , MeasureSpec.AT_MOST));
    }
}
