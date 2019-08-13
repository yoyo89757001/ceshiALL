package com.ruitong.huiyi3.beans;

public class TuiSongBena {


    /**
     * short_group : -1
     * confidence : 59.541946
     * fmp_error : 0
     * group : -1
     * event_type : 0
     * timestamp : 1527076819
     * photo : image base64
     * age : 46.27829360961914
     * photo_md5 : 148dae134019f2d9d11a052166f60708
     * fmp : 0
     * screen_token : 59f35a
     * gender : 0.9878131151199341
     * quality : 0.9966946775093675
     * subject_id :
     * subject_photo_id : 0
     */

    private int short_group;
    private double confidence;
    private int fmp_error;
    private int group;
    private int event_type;
    private int timestamp;
    private String photo;
    private double age;
    private String photo_md5;
    private int fmp;
    private String screen_token;
    private double gender;
    private double quality;
    private String subject_id;
    private int subject_photo_id;

    public int getShort_group() {
        return short_group;
    }

    public void setShort_group(int short_group) {
        this.short_group = short_group;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public int getFmp_error() {
        return fmp_error;
    }

    public void setFmp_error(int fmp_error) {
        this.fmp_error = fmp_error;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getEvent_type() {
        return event_type;
    }

    public void setEvent_type(int event_type) {
        this.event_type = event_type;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getPhoto_md5() {
        return photo_md5;
    }

    public void setPhoto_md5(String photo_md5) {
        this.photo_md5 = photo_md5;
    }

    public int getFmp() {
        return fmp;
    }

    public void setFmp(int fmp) {
        this.fmp = fmp;
    }

    public String getScreen_token() {
        return screen_token;
    }

    public void setScreen_token(String screen_token) {
        this.screen_token = screen_token;
    }

    public double getGender() {
        return gender;
    }

    public void setGender(double gender) {
        this.gender = gender;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public int getSubject_photo_id() {
        return subject_photo_id;
    }

    public void setSubject_photo_id(int subject_photo_id) {
        this.subject_photo_id = subject_photo_id;
    }

    @Override
    public String toString() {
        return "TuiSongBena{" +
                "short_group=" + short_group +
                ", confidence=" + confidence +
                ", fmp_error=" + fmp_error +
                ", group=" + group +
                ", event_type=" + event_type +
                ", timestamp=" + timestamp +
                ", age=" + age +
                ", photo_md5='" + photo_md5 + '\'' +
                ", fmp=" + fmp +
                ", screen_token='" + screen_token + '\'' +
                ", gender=" + gender +
                ", quality=" + quality +
                ", subject_id='" + subject_id + '\'' +
                ", subject_photo_id=" + subject_photo_id +
                '}';
    }
}
