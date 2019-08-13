package com.example.advanceSuggest;

import java.util.List;

public class test {


    /**
     * city_max_avg : 石家庄市
     * city_avg_data : [4777,3692,23511,3982,3316,15169,3723,3253,4866]
     * citys : ["南昌市","沧州市","石家庄市","嘉兴市","沈阳市","唐山市","潍坊市","绍兴市","乌鲁木齐市"]
     * haoshi : 程序运行时间：289ms
     * city_min_avg : 绍兴市
     */

    private String city_max_avg;
    private String haoshi;
    private String city_min_avg;
    private List<Integer> city_avg_data;
    private List<String> citys;

    public String getCity_max_avg() {
        return city_max_avg;
    }

    public void setCity_max_avg(String city_max_avg) {
        this.city_max_avg = city_max_avg;
    }

    public String getHaoshi() {
        return haoshi;
    }

    public void setHaoshi(String haoshi) {
        this.haoshi = haoshi;
    }

    public String getCity_min_avg() {
        return city_min_avg;
    }

    public void setCity_min_avg(String city_min_avg) {
        this.city_min_avg = city_min_avg;
    }

    public List<Integer> getCity_avg_data() {
        return city_avg_data;
    }

    public void setCity_avg_data(List<Integer> city_avg_data) {
        this.city_avg_data = city_avg_data;
    }

    public List<String> getCitys() {
        return citys;
    }

    public void setCitys(List<String> citys) {
        this.citys = citys;
    }
}
