package com.one.eng;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.one.eng.adapter.CitysAdapter;
import com.one.eng.adapter.ProvincesAdapter;
import com.one.eng.bean.DefendInfoBean;
import com.one.eng.utils.loglong;
import com.one.okhttputil.JuHeBaseBean;
import com.one.eng.bean.ProvinceBean;
import com.one.okhttputil.OkHttpCallback;
import com.one.okhttputil.OkHttpClientManager;
import com.one.okhttputil.util.CommonUtils;
import com.one.okhttputil.util.GsonUtil;

import org.json.JSONArray;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ShareFragment extends androidx.fragment.app.Fragment {
    private static final String TAG = "ShareFragment";
    View buyView;
    ListView listView;
    List<ProvinceBean> provinceBeanList = new ArrayList<>();
    ProvinceBean provinceBean_selected;

    ListView listView_city;
    List<ProvinceBean.CityBean> cityBeanList = new ArrayList<>();
    private Handler handler = null;
    ProvincesAdapter adapter = null;
    CitysAdapter citysAdapter = null;

    TextView tv_city_from = null;
    boolean bfrom = false;
    ProvinceBean.CityBean cityBean_from;
    TextView tv_city_to = null;
    boolean bto = false;
    ProvinceBean.CityBean cityBean_to;

    TextView tv_content_to;
    TextView tv_content_from;

    DefendInfoBean defendInfoBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        buyView = inflater.inflate(R.layout.fragment_buy, container, false);
        listView = buyView.findViewById(R.id.listview);
        tv_city_from = buyView.findViewById(R.id.tv_city_from);
        tv_city_to = buyView.findViewById(R.id.tv_city_to);
        tv_content_to = buyView.findViewById(R.id.tv_content_to);
        tv_content_from = buyView.findViewById(R.id.tv_content_from);
        adapter = new ProvincesAdapter(getActivity(), R.layout.province_item, provinceBeanList);
        listView.setAdapter(adapter);
        adapter.setClickProvinceItemListener(new ProvincesAdapter.ClickProvinceItemListener() {
            @Override
            public void clickItem(String prov_id) {
                Log.i(TAG, "clicked! item prov_id:" + prov_id);
                for (int i = 0; i < provinceBeanList.size(); i++) {
                    if (provinceBeanList.get(i).province_id == prov_id) {
                        provinceBean_selected = provinceBeanList.get(i);
                        cityBeanList.clear();
                        cityBeanList.addAll(provinceBean_selected.citys);
                        if (citysAdapter != null) {
                            listView.setVisibility(View.GONE);
                            listView_city.setVisibility(View.VISIBLE);
                            citysAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

        tv_city_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bfrom = true;
                queryProvince();

//                queryCitysTravelPolicy(10002, 10017);
            }
        });
        tv_city_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bto = true;
//                queryProvince();
            }
        });

        //citys
        listView_city = buyView.findViewById(R.id.listview_city);
        citysAdapter = new CitysAdapter(getActivity(), R.layout.city_item, cityBeanList);
        listView_city.setAdapter(citysAdapter);
        citysAdapter.setClickProvinceItemListener(new CitysAdapter.ClickProvinceItemListener() {
            @Override
            public void clickItem(ProvinceBean.CityBean cityBean) {
                listView_city.setVisibility(View.GONE);
                queryCitysTravelPolicy(Integer.parseInt(cityBean.city_id), Integer.parseInt(cityBean.city_id));

//                if (bfrom) {
//                    bfrom =false;
//                    cityBean_from = cityBean;
//                    tv_city_from.setText(cityBean_from.city);
//                } else if (bto) {
//                    bto= false;
//                    cityBean_to = cityBean;
//                    tv_city_to.setText(cityBean_to.city);
//                }
//                listView_city.setVisibility(View.GONE);
//
//                if (TextUtils.isEmpty(tv_city_from.getText()) && TextUtils.isEmpty(tv_city_to.getText())) {
//                    queryCitysTravelPolicy(Integer.parseInt(cityBean_from.city_id), Integer.parseInt(cityBean_to.city_id));
//                }
            }
        });


        return buyView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initData();
//        initView(view);

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void queryProvince() {
        provinceBeanList.clear();
        ArrayList<ProvinceBean> list = new ArrayList<ProvinceBean>();
        String url = CommonUtils.JuHeCitysQueryUrl;
        Map<String, String> params = new HashMap();
        params.put("key", CommonUtils.JuHeCitysQueryKey);
        OkHttpClientManager.getInstance().postAsyn(url, params, new OkHttpCallback<JuHeBaseBean<List<ProvinceBean>>>() {
            @Override
            public void onSuccess(JuHeBaseBean<List<ProvinceBean>> response) {
//                Log.i("one.yu", "onSuccess response: " + response.getResult());
//                provinceBeanList.addAll(response.getResult());
                if (response == null || response.getResult() == null || provinceBeanList == null)
                    return;
                loglong.e(TAG, "provinceBeanList: " + GsonUtil.objList2JsonStr(list));
                provinceBeanList.addAll(response.getResult());
                adapter.notifyDataSetChanged();

//                for(int i=0;i<provinceBeanList.size();i++){
//                    Log.i(TAG, ""+provinceBeanList.get(i).province+",");
//                }
            }

            @Override
            public void onFailure(JuHeBaseBean entity, String message) {
                Log.i("one.yu", "onFailure message: " + message);

            }
        }, null);
    }

    private void queryCitysTravelPolicy(int from, int to) {
        String url = CommonUtils.JuHeCitysTravelPolicyUrl;
        Map<String, String> params = new HashMap<>();
        params.put("key", CommonUtils.JuHeCitysQueryKey);
        params.put("from", String.valueOf(from));
        params.put("to", String.valueOf(to));
        OkHttpClientManager.getInstance().postAsyn(url, params, new OkHttpCallback<JuHeBaseBean<DefendInfoBean>>() {
            @Override
            public void onSuccess(JuHeBaseBean<DefendInfoBean> response) {

                loglong.e("policy citys", "onSuccess message: " + GsonUtil.obj2JsonStr(response));
                defendInfoBean = response.getResult();
                tv_content_from.setText(defendInfoBean.getFrom_info().getHigh_in_desc());
                tv_content_to.setText(defendInfoBean.getTo_info().getHigh_in_desc());
            }

            @Override
            public void onFailure(JuHeBaseBean entity, String message) {
                Log.i("policy citys", "onFailure message: " + message);

            }
        }, null);


    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            Log.i(TAG, "setUserVisibleHint");


        }
    }
}
