package com.ruitong.huiyi3.beans;

import java.util.List;

public class IPfanhuibean {


    /**
     * code : 0
     * data : [{"allow_all_subjects":true,"allow_visitor":true,"allowed_subject_ids":[],"box_address":null,"box_heartbeat":null,"box_status":"1","box_token":null,"camera_address":null,"camera_name":null,"camera_position":"门禁Pad","camera_status":null,"description":null,"id":63,"is_select":0,"network_switcher":null,"network_switcher_drive":1,"network_switcher_status":null,"network_switcher_token":null,"screen_token":"6a13d87e886a6d7a","server_time":1.541747428313062E9,"type":2},{"allow_all_subjects":true,"allow_visitor":true,"allowed_subject_ids":[],"box_address":null,"box_heartbeat":null,"box_status":"1","box_token":null,"camera_address":null,"camera_name":null,"camera_position":"门禁Pad","camera_status":null,"description":null,"id":64,"is_select":0,"network_switcher":null,"network_switcher_drive":1,"network_switcher_status":null,"network_switcher_token":null,"screen_token":"3ee07eb6b1ded3f","server_time":1.541747428313062E9,"type":2},{"allow_all_subjects":true,"allow_visitor":true,"allowed_subject_ids":[260,258,259],"box_address":"192.168.2.78","box_heartbeat":1541747397,"box_status":"0","box_token":"6a0fb93d-ed83-404b-84b1-a9cc674ff82a","camera_address":"rtsp://192.168.2.46:554/user=admin_password=tlJwpbo6_channel=1_stream=0.sdp?real_stream","camera_name":null,"camera_position":"瑞瞳专业展示","camera_status":"0","description":null,"id":4,"is_select":1,"network_switcher":"","network_switcher_drive":0,"network_switcher_status":null,"network_switcher_token":null,"screen_token":"e5ce91c1-322f-4320-a913-d62578670723","server_time":1.541747428313062E9,"type":1},{"allow_all_subjects":true,"allow_visitor":true,"allowed_subject_ids":[],"box_address":"192.168.2.78","box_heartbeat":1541747397,"box_status":"0","box_token":"6a0fb93d-ed83-404b-84b1-a9cc674ff82a","camera_address":"rtsp://admin:admin888@192.168.2.41:554/h264/ch0/main/av_stream","camera_name":"","camera_position":"入口海康","camera_status":"0","description":null,"id":5,"is_select":1,"network_switcher":"","network_switcher_drive":0,"network_switcher_status":null,"network_switcher_token":null,"screen_token":"61c986d3-157e-4453-a919-bf9e75a14f60","server_time":1.541747428313062E9,"type":1}]
     * page : {"count":4,"current":1,"size":500,"total":1}
     */

    private int code;
    private PageBean page;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageBean {
        /**
         * count : 4
         * current : 1
         * size : 500
         * total : 1
         */

        private int count;
        private int current;
        private int size;
        private int total;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class DataBean {
        /**
         * allow_all_subjects : true
         * allow_visitor : true
         * allowed_subject_ids : []
         * box_address : null
         * box_heartbeat : null
         * box_status : 1
         * box_token : null
         * camera_address : null
         * camera_name : null
         * camera_position : 门禁Pad
         * camera_status : null
         * description : null
         * id : 63
         * is_select : 0
         * network_switcher : null
         * network_switcher_drive : 1
         * network_switcher_status : null
         * network_switcher_token : null
         * screen_token : 6a13d87e886a6d7a
         * server_time : 1.541747428313062E9
         * type : 2
         */

        private boolean allow_all_subjects;
        private boolean allow_visitor;
        private Object box_address;
        private Object box_heartbeat;
        private String box_status;
        private Object box_token;
        private String camera_address;
        private Object camera_name;
        private String camera_position;
        private Object camera_status;
        private Object description;
        private int id;
        private int is_select;
        private Object network_switcher;
        private int network_switcher_drive;
        private Object network_switcher_status;
        private Object network_switcher_token;
        private String screen_token;
        private double server_time;
        private int type;
        private List<?> allowed_subject_ids;

        public boolean isAllow_all_subjects() {
            return allow_all_subjects;
        }

        public void setAllow_all_subjects(boolean allow_all_subjects) {
            this.allow_all_subjects = allow_all_subjects;
        }

        public boolean isAllow_visitor() {
            return allow_visitor;
        }

        public void setAllow_visitor(boolean allow_visitor) {
            this.allow_visitor = allow_visitor;
        }

        public Object getBox_address() {
            return box_address;
        }

        public void setBox_address(Object box_address) {
            this.box_address = box_address;
        }

        public Object getBox_heartbeat() {
            return box_heartbeat;
        }

        public void setBox_heartbeat(Object box_heartbeat) {
            this.box_heartbeat = box_heartbeat;
        }

        public String getBox_status() {
            return box_status;
        }

        public void setBox_status(String box_status) {
            this.box_status = box_status;
        }

        public Object getBox_token() {
            return box_token;
        }

        public void setBox_token(Object box_token) {
            this.box_token = box_token;
        }

        public String getCamera_address() {
            return camera_address;
        }

        public void setCamera_address(String camera_address) {
            this.camera_address = camera_address;
        }

        public Object getCamera_name() {
            return camera_name;
        }

        public void setCamera_name(Object camera_name) {
            this.camera_name = camera_name;
        }

        public String getCamera_position() {
            return camera_position;
        }

        public void setCamera_position(String camera_position) {
            this.camera_position = camera_position;
        }

        public Object getCamera_status() {
            return camera_status;
        }

        public void setCamera_status(Object camera_status) {
            this.camera_status = camera_status;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_select() {
            return is_select;
        }

        public void setIs_select(int is_select) {
            this.is_select = is_select;
        }

        public Object getNetwork_switcher() {
            return network_switcher;
        }

        public void setNetwork_switcher(Object network_switcher) {
            this.network_switcher = network_switcher;
        }

        public int getNetwork_switcher_drive() {
            return network_switcher_drive;
        }

        public void setNetwork_switcher_drive(int network_switcher_drive) {
            this.network_switcher_drive = network_switcher_drive;
        }

        public Object getNetwork_switcher_status() {
            return network_switcher_status;
        }

        public void setNetwork_switcher_status(Object network_switcher_status) {
            this.network_switcher_status = network_switcher_status;
        }

        public Object getNetwork_switcher_token() {
            return network_switcher_token;
        }

        public void setNetwork_switcher_token(Object network_switcher_token) {
            this.network_switcher_token = network_switcher_token;
        }

        public String getScreen_token() {
            return screen_token;
        }

        public void setScreen_token(String screen_token) {
            this.screen_token = screen_token;
        }

        public double getServer_time() {
            return server_time;
        }

        public void setServer_time(double server_time) {
            this.server_time = server_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<?> getAllowed_subject_ids() {
            return allowed_subject_ids;
        }

        public void setAllowed_subject_ids(List<?> allowed_subject_ids) {
            this.allowed_subject_ids = allowed_subject_ids;
        }
    }
}
