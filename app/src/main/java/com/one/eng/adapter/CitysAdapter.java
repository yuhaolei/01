package com.one.eng.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.one.eng.R;
import com.one.eng.bean.ProvinceBean;
import com.one.eng.utils.LogUtils;

import java.util.List;

public class CitysAdapter extends ArrayAdapter<ProvinceBean.CityBean> {
    private int resourceId;
    LinearLayout ll_peer;

    // 适配器的构造函数，把要适配的数据传入这里
    public CitysAdapter(Context context,
                        int textViewResourceId,
                        List<ProvinceBean.CityBean> objects) {
        super(context, textViewResourceId,
                objects);
        resourceId = textViewResourceId;
    }

    // convertView 参数用于将之前加载好的布局进行缓存
    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {
        ProvinceBean.CityBean cityBean = getItem(position);
        //获取当前项的Fruit实例
        LogUtils.e("city position:" + position);
        // 加个判断，以免ListView每次滚动时都要重新加载布局，以提高运行效率
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {

            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder = new ViewHolder();
            viewHolder.cityImage =
                    view.findViewById(R.id.iv_city);
            viewHolder.cityName =
                    view.findViewById(R.id.tv_city);
            ll_peer = view.findViewById(R.id.ll_peer);


            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder =
                    (ViewHolder) view.getTag();
        }

        // 获取控件实例，并调用set...方法使其显示出来
        viewHolder.cityImage.setImageResource(R.drawable.copy);
        viewHolder.cityName.setText(cityBean.city);
        ll_peer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.clickItem(cityBean);
                }
            }
        });

        return view;
    }

    // 定义一个内部类，用于对控件的实例进行缓存
    class ViewHolder {
        ImageView cityImage;
        TextView cityName;
    }

    public interface ClickProvinceItemListener {
        public void clickItem(ProvinceBean.CityBean item_id);
    }

    ClickProvinceItemListener listener;

    public void setClickProvinceItemListener(ClickProvinceItemListener listener) {
        this.listener = listener;
    }
}
