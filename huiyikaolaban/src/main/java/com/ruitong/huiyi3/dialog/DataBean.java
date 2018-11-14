package com.ruitong.huiyi3.dialog;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/5/9.
 */

public class DataBean {

    String job_number;
    int id;
    private List<PhotosBean> photos;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob_number() {
        return job_number;
    }

    public void setJob_number(String job_number) {
        this.job_number = job_number;
    }

    public List<PhotosBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosBean> photos) {
        this.photos = photos;
    }

    public static class PhotosBean {
        /**
         * company_id : 1
         * id : 562
         * quality : 0.988897
         * subject_id : 4415
         * url : /static/upload/photo/2018-06-14/v2_43ce7ae6e9ab41c0c837a0d27bd20d4a3ecc88f5.jpg
         * version : 7
         */

        private int company_id;
        @SerializedName("id")
        private int idX;
        private double quality;
        private int subject_id;
        private String url;
        private int version;

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public int getIdX() {
            return idX;
        }

        public void setIdX(int idX) {
            this.idX = idX;
        }

        public double getQuality() {
            return quality;
        }

        public void setQuality(double quality) {
            this.quality = quality;
        }

        public int getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(int subject_id) {
            this.subject_id = subject_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }
}
