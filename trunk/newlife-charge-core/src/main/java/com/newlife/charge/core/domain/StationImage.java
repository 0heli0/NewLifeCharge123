/* -------------------------------------------
 * StationImage.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;



/**
 * 桩站图片表-暂未使用 v1.0
 */

@Deprecated
public class StationImage {

    //桩站图片表主键ID
    private Integer id;

    //所属桩站ID
    private Integer stationId;

    //图标标题
    private String title;

    //图片地址
    private String imgUrl;

    //是否封面(0否1是)
    private Integer coverFlag;

    //创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Integer getCoverFlag() {
        return coverFlag;
    }

    public void setCoverFlag(Integer coverFlag) {
        this.coverFlag = coverFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}