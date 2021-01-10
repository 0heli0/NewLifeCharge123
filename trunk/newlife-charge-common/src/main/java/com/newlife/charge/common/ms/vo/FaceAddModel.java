package com.newlife.charge.common.ms.vo;

/**
 * 人脸注册或更新解析
 *
 * @Author lincz on 2019/1/15 0015.
 */
public class FaceAddModel extends BaseModel {

    private FaceInfo data;

    public FaceInfo getData() {
        return data;
    }

    public void setData(FaceInfo data) {
        this.data = data;
    }
}
