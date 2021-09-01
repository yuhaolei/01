package com.one.eng.bean;

import java.util.List;

public class ProvinceBean {

    public String province_id;
    public String province;
    public List<CityBean> citys;


    public class CityBean {
        public String city_id;
        public String city;

    }
}
