package com.ruitong.huiyi3.beans;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */

public class HuiYiInFoBean {


    /**
     * createTime : 1527501612994
     * dtoResult : 0
     * modifyTime : 1527501612994
     * objects : [{"capacity":10001,"companyId":10000025,"conferenceAddress":"Wo10001a","conferenceGroupId":"-1","createTime":1527496141000,"create_time":1527496141000,"dtoResult":0,"endTime":1527755220000,"exhibitionId":3,"id":10000034,"introduce":"Wo10001a","machineCode":"c08f41468e76acf","modifyTime":1527496141000,"modify_time":1527496141000,"pageNum":0,"pageSize":0,"screenId":"10100097,","sid":0,"startTime":1527581280000,"status":1,"subConferenceCode":"Wo10001a","subConferenceName":"Wo10001a"},{"capacity":213,"companyId":10000025,"conferenceAddress":"","conferenceGroupId":"-1","createTime":1527499988000,"create_time":1527499988000,"dtoResult":0,"endTime":1527499988000,"exhibitionId":3,"id":10000043,"introduce":"","machineCode":"c08f41468e76acf","modifyTime":1527499988000,"modify_time":1527499988000,"pageNum":0,"pageSize":0,"screenId":"10100097,","sid":0,"startTime":1527499988000,"status":2,"subConferenceCode":"ss","subConferenceName":"ss"}]
     * pageNum : 0
     * pageSize : 0
     * sid : 0
     */

    private long createTime;
    private int dtoResult;
    private long modifyTime;
    private int pageNum;
    private int pageSize;
    private int sid;
    private List<ObjectsBean> objects;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDtoResult() {
        return dtoResult;
    }

    public void setDtoResult(int dtoResult) {
        this.dtoResult = dtoResult;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public List<ObjectsBean> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectsBean> objects) {
        this.objects = objects;
    }

    public static class ObjectsBean {
        /**
         * capacity : 10001
         * companyId : 10000025
         * conferenceAddress : Wo10001a
         * conferenceGroupId : -1
         * createTime : 1527496141000
         * create_time : 1527496141000
         * dtoResult : 0
         * endTime : 1527755220000
         * exhibitionId : 3
         * id : 10000034
         * introduce : Wo10001a
         * machineCode : c08f41468e76acf
         * modifyTime : 1527496141000
         * modify_time : 1527496141000
         * pageNum : 0
         * pageSize : 0
         * screenId : 10100097,
         * sid : 0
         * startTime : 1527581280000
         * status : 1
         * subConferenceCode : Wo10001a
         * subConferenceName : Wo10001a
         */

        private int capacity;
        private int companyId;
        private String conferenceAddress;
        private String conferenceGroupId;
        private long createTime;
        private long create_time;
        private int dtoResult;
        private long endTime;
        private int exhibitionId;
        private int id;
        private String introduce;
        private String machineCode;
        private long modifyTime;
        private long modify_time;
        private int pageNum;
        private int pageSize;
        private String screenId;
        private int sid;
        private long startTime;
        private int status;
        private String subConferenceCode;
        private String subConferenceName;

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getConferenceAddress() {
            return conferenceAddress;
        }

        public void setConferenceAddress(String conferenceAddress) {
            this.conferenceAddress = conferenceAddress;
        }

        public String getConferenceGroupId() {
            return conferenceGroupId;
        }

        public void setConferenceGroupId(String conferenceGroupId) {
            this.conferenceGroupId = conferenceGroupId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public int getDtoResult() {
            return dtoResult;
        }

        public void setDtoResult(int dtoResult) {
            this.dtoResult = dtoResult;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getExhibitionId() {
            return exhibitionId;
        }

        public void setExhibitionId(int exhibitionId) {
            this.exhibitionId = exhibitionId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getMachineCode() {
            return machineCode;
        }

        public void setMachineCode(String machineCode) {
            this.machineCode = machineCode;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }

        public long getModify_time() {
            return modify_time;
        }

        public void setModify_time(long modify_time) {
            this.modify_time = modify_time;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public String getScreenId() {
            return screenId;
        }

        public void setScreenId(String screenId) {
            this.screenId = screenId;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSubConferenceCode() {
            return subConferenceCode;
        }

        public void setSubConferenceCode(String subConferenceCode) {
            this.subConferenceCode = subConferenceCode;
        }

        public String getSubConferenceName() {
            return subConferenceName;
        }

        public void setSubConferenceName(String subConferenceName) {
            this.subConferenceName = subConferenceName;
        }
    }
}
