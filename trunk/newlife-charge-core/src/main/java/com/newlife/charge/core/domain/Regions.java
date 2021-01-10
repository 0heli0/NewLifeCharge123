/* -------------------------------------------
 * Regions.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;



/**
 * 行政区域表 v1.0
 */


public class Regions {

    //主键-行政区域id
    private Integer id;

    //名称
    private String name;

    //拼写
    private String spell;

    //简拼
    private String shortSpell;

    //排序
    private Integer displayOrder;

    //父id
    private Integer parentId;

    //级别
    private Integer layer;

    //省id
    private Integer distrctId;

    //省名称
    private String distrctName;

    //市id
    private Integer cityId;

    //市名称
    private String cityName;

    //省份
    private String province;

    //热门城市(0否 1是)
    private Integer hot;

    //状态(0隐藏 1显示) 
    private Integer status;

    //更新时间
    private Date updateTime;

    //创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell == null ? null : spell.trim();
    }

    public String getShortSpell() {
        return shortSpell;
    }

    public void setShortSpell(String shortSpell) {
        this.shortSpell = shortSpell == null ? null : shortSpell.trim();
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Integer getDistrctId() {
        return distrctId;
    }

    public void setDistrctId(Integer distrctId) {
        this.distrctId = distrctId;
    }

    public String getDistrctName() {
        return distrctName;
    }

    public void setDistrctName(String distrctName) {
        this.distrctName = distrctName == null ? null : distrctName.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}