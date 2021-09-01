package com.one.eng.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.one.eng.R;
import com.one.eng.bean.EngBean;
import com.one.eng.bean.ProvinceBean;
import com.one.eng.utils.LogUtils;

import java.util.List;

public class ProvincesAdapter extends ArrayAdapter<ProvinceBean> {
    private int resourceId;

    // 适配器的构造函数，把要适配的数据传入这里
    public ProvincesAdapter(Context context,
                            int textViewResourceId,
                            List<ProvinceBean> objects) {
        super(context, textViewResourceId,
                objects);
        resourceId = textViewResourceId;
    }

    // convertView 参数用于将之前加载好的布局进行缓存
    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {
        ProvinceBean provinceBean = getItem(position);
        //获取当前项的Fruit实例
        LogUtils.e("province position:" + position);
        // 加个判断，以免ListView每次滚动时都要重新加载布局，以提高运行效率
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {

            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder = new ViewHolder();
            viewHolder.provinceImage =
                    view.findViewById(R.id.iv_province);
            viewHolder.provinceName =
                    view.findViewById(R.id.tv_province);

            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder =
                    (ViewHolder) view.getTag();
        }

        // 获取控件实例，并调用set...方法使其显示出来
        viewHolder.provinceImage.setImageResource(R.drawable.poster);
        viewHolder.provinceName.setText(provinceBean.province);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.clickItem(provinceBean.province_id);
                }

            }
        });
        return view;
    }

    // 定义一个内部类，用于对控件的实例进行缓存
    class ViewHolder {
        ImageView provinceImage;
        TextView provinceName;
    }

    public interface ClickProvinceItemListener {
        public void clickItem(String item_id);
    }

    ClickProvinceItemListener listener;

    public void setClickProvinceItemListener(ClickProvinceItemListener listener) {
        this.listener = listener;
    }
}
