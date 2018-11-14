package com.ruitong.huiyi3.dialog;

import java.util.List;

/**
 * Created by Administrator on 2018/5/9.
 */

public class GaiNiMaBi {


    /**
     * code : 0
     * data : [{"avatar":"","come_from":"","company_id":1,"department":"","description":"","email":"dyg@megvii.com","end_time":0,"gender":0,"id":4,"interviewee":"","name":"dfds","password_reseted":false,"phone":"","photo_ids":[4],"photos":[{"company_id":1,"id":4,"subject_id":4,"url":"/static/upload/photo/2015-10-13/3ee5d084439065548440749c334957e3fdaa0132.jpg"}],"purpose":0,"start_time":0,"subject_type":0,"title":""}]
     * page : {"count":757,"current":1,"size":10,"total":76}
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }
}
