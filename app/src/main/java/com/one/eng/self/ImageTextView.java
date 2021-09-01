package com.one.eng.self;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

public class ImageTextView extends androidx.appcompat.widget.AppCompatTextView {

    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context,
                         AttributeSet attrs) {
        super(context, attrs);
    }

    public void insertDrawable(int id) {
        final SpannableString ss =
                new SpannableString("ea sy");
        //得到drawable对象，即所要插入的图片
        Drawable d =
                getResources().getDrawable(id);
        d.setBounds(0, 0, d.getIntrinsicWidth()
                , d.getIntrinsicHeight());

        d.setBounds(0, 0, (int)this.getTextSize()
                , (int)this.getTextSize());
        //用这个drawable对象代替字符串easy
        ImageSpan span = new ImageSpan(d,
                ImageSpan.ALIGN_BOTTOM);
        //包括0但是不包括"easy".length()即：4。[0,4)
        // 。值得注意的是当我们复制这个图片的时候，实际是复制了"easy"这个字符串。
        ss.setSpan(span, 2, 3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        append(ss);
    }

    public void insertDrawableUrl(int id,
                                  String url) {
        final SpannableString ss =
                new SpannableString("easy");
        //得到drawable对象，即所要插入的图片
        Drawable d =
                getResources().getDrawable(id);
        d.setBounds(0, 0, d.getIntrinsicWidth()
                , d.getIntrinsicHeight());
        //用这个drawable对象代替字符串easy
        ImageSpan span = new ImageSpan(d,
                ImageSpan.ALIGN_BASELINE);
        //包括0但是不包括"easy".length()即：4。[0,4)
        // 。值得注意的是当我们复制这个图片的时候，实际是复制了"easy"这个字符串。
        ss.setSpan(span, 0, "easy".length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        append(ss);
    }

}
