package com.ruitong.huiyi3.huiyixinxi;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/6/12.
 */
@Entity
public class HuiYiID {

        @NotNull@Id
        private Long id;
        private String subConferenceCode;
        private long startTime;
        private long endTime;
        @Generated(hash = 690645971)
        public HuiYiID(@NotNull Long id, String subConferenceCode, long startTime,
                long endTime) {
            this.id = id;
            this.subConferenceCode = subConferenceCode;
            this.startTime = startTime;
            this.endTime = endTime;
        }
        @Generated(hash = 1166177203)
        public HuiYiID() {
        }
        public Long getId() {
            return this.id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getSubConferenceCode() {
            return this.subConferenceCode;
        }
        public void setSubConferenceCode(String subConferenceCode) {
            this.subConferenceCode = subConferenceCode;
        }
        public long getStartTime() {
            return this.startTime;
        }
        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }
        public long getEndTime() {
            return this.endTime;
        }
        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }


}
