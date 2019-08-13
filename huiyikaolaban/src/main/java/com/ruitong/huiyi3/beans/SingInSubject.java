package com.ruitong.huiyi3.beans;

import android.view.View;

import java.util.Arrays;
import java.util.Comparator;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;

/**
 * Created by Administrator on 2018/5/31.
 */
@Entity
public class SingInSubject implements Comparator<Subject> {
    public SingInSubject() {
    }

    @Id(assignable = true)
    private long id;
    private String sid;
    private String name;// 姓名
    private String companyId; // 公司ID
    private String companyName; // 公司名称
    private String workNumber; // 工号
    private String sex; // 性别
    private String phone;// 手机号
    private String peopleType;// 人员类型
    private String email;// 电子邮箱
    private String position; // 职位
    private int employeeStatus; // 是否在职
    private int quitType; // 离职类型
    private String remark;// 备注
    private String photo;// 照片
    private String storeId;// 门店ID
    private String storeName;// 门店名称
    private String entryTime; // 入职时间
    private String birthday; // 生日
    private String teZhengMa;
    private String departmentName;
    private int daka;
    private String shijian;
    private String displayPhoto;
    private byte[] txBytes;
    private int w;
    private int h;
    public long trackId;
    public float age;         //年龄
    public int gender;        //性别
    public float smile;      //心情值
    public long attentionTime;
    private String meetingId; //会议ID
    private String time;
    private String status;





    public SingInSubject(long id, String sid, String name, String companyId, String companyName, String workNumber, String sex, String phone, String peopleType, String email, String position, int employeeStatus, int quitType, String remark, String photo, String storeId, String storeName, String entryTime, String birthday, String teZhengMa, String departmentName, int daka, String shijian, View view) {
        this.id = id;
        this.sid = sid;
        this.name = name;
        this.companyId = companyId;
        this.companyName = companyName;
        this.workNumber = workNumber;
        this.sex = sex;
        this.phone = phone;
        this.peopleType = peopleType;
        this.email = email;
        this.position = position;
        this.employeeStatus = employeeStatus;
        this.quitType = quitType;
        this.remark = remark;
        this.photo = photo;
        this.storeId = storeId;
        this.storeName = storeName;
        this.entryTime = entryTime;
        this.birthday = birthday;
        this.teZhengMa = teZhengMa;
        this.departmentName = departmentName;
        this.daka = daka;
        this.shijian = shijian;
        this.view = view;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }


    public long getAttentionTime() {
        return attentionTime;
    }

    public void setAttentionTime(long attentionTime) {
        this.attentionTime = attentionTime;
    }



    public float getSmile() {
        return smile;
    }

    public void setSmile(float smile) {
        this.smile = smile;
    }



    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }



    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public byte[] getTxBytes() {
        return txBytes;
    }

    public void setTxBytes(byte[] txBytes) {
        this.txBytes = txBytes;
    }

    public String getDisplayPhoto() {
        return displayPhoto;
    }

    public void setDisplayPhoto(String displayPhoto) {
        this.displayPhoto = displayPhoto;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public int getDaka() {
        return daka;
    }

    public void setDaka(int daka) {
        this.daka = daka;
    }

    @Transient
    private View view;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getTeZhengMa() {
        return teZhengMa;
    }

    public void setTeZhengMa(String teZhengMa) {
        this.teZhengMa = teZhengMa;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(String peopleType) {
        this.peopleType = peopleType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(int employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public int getQuitType() {
        return quitType;
    }

    public void setQuitType(int quitType) {
        this.quitType = quitType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public int compare(Subject o1, Subject o2) {
        return o1.getSid().compareTo(o2.getSid());
    }


    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", companyId='" + companyId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", workNumber='" + workNumber + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", peopleType='" + peopleType + '\'' +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                ", employeeStatus=" + employeeStatus +
                ", quitType=" + quitType +
                ", remark='" + remark + '\'' +
                ", photo='" + photo + '\'' +
                ", storeId='" + storeId + '\'' +
                ", storeName='" + storeName + '\'' +
                ", entryTime='" + entryTime + '\'' +
                ", birthday='" + birthday + '\'' +
                ", teZhengMa='" + teZhengMa + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", daka=" + daka +
                ", shijian='" + shijian + '\'' +
                ", displayPhoto='" + displayPhoto + '\'' +
                ", txBytes=" + Arrays.toString(txBytes) +
                ", w=" + w +
                ", h=" + h +
                ", view=" + view +
                '}';
    }
}

