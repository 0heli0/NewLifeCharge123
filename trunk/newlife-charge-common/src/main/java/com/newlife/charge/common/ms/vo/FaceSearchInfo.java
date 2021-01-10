package com.newlife.charge.common.ms.vo;

/**
 * 人脸 搜索 详情
 *
 * @Author lincz on 2019/1/15 0015.
 */
public class FaceSearchInfo {

    private String group_id;

    private String user_id;

    private String user_info;

    private Integer score;


    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_info() {
        return user_info;
    }

    public void setUser_info(String user_info) {
        this.user_info = user_info;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
