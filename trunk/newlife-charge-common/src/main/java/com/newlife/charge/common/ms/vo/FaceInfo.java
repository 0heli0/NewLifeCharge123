package com.newlife.charge.common.ms.vo;

/**
 * 人脸信息
 *
 * @Author lincz on 2019/1/15 0015.
 */
public class FaceInfo {

    private String face_token;

    private FaceLocation location;

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public FaceLocation getLocation() {
        return location;
    }

    public void setLocation(FaceLocation location) {
        this.location = location;
    }
}
