package com.ruitong.huiyi3.beans;

import android.util.Log;

public class KaoLaBeans {

//      Log.d("MyWebServer", name);
//      Log.d("MyWebServer", headImage);
//      Log.d("MyWebServer", interviewees);

    private String name;
    private String headImage;
    private String interviewees;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getInterviewees() {
        return interviewees;
    }

    public void setInterviewees(String interviewees) {
        this.interviewees = interviewees;
    }

    @Override
    public String toString() {
        return "KaoLaBeans{" +
                "name='" + name + '\'' +
                ", headImage='" + headImage + '\'' +
                ", interviewees='" + interviewees + '\'' +
                '}';
    }
}
