package com.ruitong.huiyi3.beans;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class PiLiangBean {


    /**
     * createTime : 1527561505583
     * dtoResult : 0
     * modifyTime : 1527561505583
     * objects : [{"accountId":10000065,"assemblyId":"Wo10001","channel":0,"come_from":"气管炎","companyName":"10000025","country":"中国","createTime":1527496888000,"department":"领导","dtoResult":0,"gender":0,"id":10403782,"identity":"身份","jobStatus":1,"location":"11","meetingId":0,"modifyTime":1527560211000,"name":"张三","namePinyin":"ZS","num":0,"pageNum":0,"pageSize":0,"phone":"1234","photo_ids":"20180528/1527497456044_657.jpg","province":"广东","remark":"","sid":0,"sourceMeeting":"Wo10001a","sourceQuestionJson":"","status":1,"subject_type":0,"title":"普通员工"},{"accountId":10000065,"assemblyId":"Wo10001","channel":0,"come_from":"气管炎","companyName":"10000025","country":"中国","createTime":1527496888000,"department":"普通员工","dtoResult":0,"gender":0,"id":10403783,"identity":"身份","jobStatus":1,"location":"22","meetingId":0,"modifyTime":1527560211000,"name":"李四","namePinyin":"LS","num":0,"pageNum":0,"pageSize":0,"phone":"12346","photo_ids":"20180528/1527497438266_638.jpg","province":"广西","remark":"","sid":0,"sourceMeeting":"Wo10001a","sourceQuestionJson":"","status":1,"subject_type":0,"title":"领导"}]
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
         * accountId : 10000065
         * assemblyId : Wo10001
         * channel : 0
         * come_from : 气管炎
         * companyName : 10000025
         * country : 中国
         * createTime : 1527496888000
         * department : 领导
         * dtoResult : 0
         * gender : 0
         * id : 10403782
         * identity : 身份
         * jobStatus : 1
         * location : 11
         * meetingId : 0
         * modifyTime : 1527560211000
         * name : 张三
         * namePinyin : ZS
         * num : 0
         * pageNum : 0
         * pageSize : 0
         * phone : 1234
         * photo_ids : 20180528/1527497456044_657.jpg
         * province : 广东
         * remark :
         * sid : 0
         * sourceMeeting : Wo10001a
         * sourceQuestionJson :
         * status : 1
         * subject_type : 0
         * title : 普通员工
         */

        private int accountId;
        private String assemblyId;
        private int channel;
        private String come_from;
        private String companyName;
        private String country;
        private long createTime;
        private String department;
        private int dtoResult;
        private int gender;
        private int id;
        private String identity;
        private int jobStatus;
        private String location;
        private int meetingId;
        private long modifyTime;
        private String name;
        private String namePinyin;
        private int num;
        private int pageNum;
        private int pageSize;
        private String phone;
        private String photo_ids;
        private String province;
        private String remark;
        private int sid;
        private String sourceMeeting;
        private String sourceQuestionJson;
        private int status;
        private int subject_type;
        private String title;

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public String getAssemblyId() {
            return assemblyId;
        }

        public void setAssemblyId(String assemblyId) {
            this.assemblyId = assemblyId;
        }

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public String getCome_from() {
            return come_from;
        }

        public void setCome_from(String come_from) {
            this.come_from = come_from;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getDtoResult() {
            return dtoResult;
        }

        public void setDtoResult(int dtoResult) {
            this.dtoResult = dtoResult;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public int getJobStatus() {
            return jobStatus;
        }

        public void setJobStatus(int jobStatus) {
            this.jobStatus = jobStatus;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getMeetingId() {
            return meetingId;
        }

        public void setMeetingId(int meetingId) {
            this.meetingId = meetingId;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNamePinyin() {
            return namePinyin;
        }

        public void setNamePinyin(String namePinyin) {
            this.namePinyin = namePinyin;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhoto_ids() {
            return photo_ids;
        }

        public void setPhoto_ids(String photo_ids) {
            this.photo_ids = photo_ids;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getSourceMeeting() {
            return sourceMeeting;
        }

        public void setSourceMeeting(String sourceMeeting) {
            this.sourceMeeting = sourceMeeting;
        }

        public String getSourceQuestionJson() {
            return sourceQuestionJson;
        }

        public void setSourceQuestionJson(String sourceQuestionJson) {
            this.sourceQuestionJson = sourceQuestionJson;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSubject_type() {
            return subject_type;
        }

        public void setSubject_type(int subject_type) {
            this.subject_type = subject_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
