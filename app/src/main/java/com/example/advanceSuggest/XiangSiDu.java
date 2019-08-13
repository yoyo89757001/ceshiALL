package com.example.advanceSuggest;

import java.util.List;

public class XiangSiDu {


    /**
     * face : {"rect":{"left":320,"top":270,"width":332,"height":332},"confidence":0.999959}
     * groups : [{"group":"ruitongtest","photos":[{"id":1,"tag":"","score":96.08299}]}]
     */

    private FaceBean face;
    private List<GroupsBean> groups;

    public FaceBean getFace() {
        return face;
    }

    public void setFace(FaceBean face) {
        this.face = face;
    }

    public List<GroupsBean> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupsBean> groups) {
        this.groups = groups;
    }

    public static class FaceBean {
        /**
         * rect : {"left":320,"top":270,"width":332,"height":332}
         * confidence : 0.999959
         */

        private RectBean rect;
        private double confidence;

        public RectBean getRect() {
            return rect;
        }

        public void setRect(RectBean rect) {
            this.rect = rect;
        }

        public double getConfidence() {
            return confidence;
        }

        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }

        public static class RectBean {
            /**
             * left : 320
             * top : 270
             * width : 332
             * height : 332
             */

            private int left;
            private int top;
            private int width;
            private int height;

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }

    public static class GroupsBean {
        /**
         * group : ruitongtest
         * photos : [{"id":1,"tag":"","score":96.08299}]
         */

        private String group;
        private List<PhotosBean> photos;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public List<PhotosBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosBean> photos) {
            this.photos = photos;
        }

        public static class PhotosBean {
            /**
             * id : 1
             * tag :
             * score : 96.08299
             */

            private int id;
            private String tag;
            private double score;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }
        }
    }

    @Override
    public String toString() {
        return "XiangSiDu{" +
                "face=" + face +
                ", groups=" + groups +
                '}';
    }
}
