package com.newlife.charge.core.domain.exModel;


import com.newlife.charge.core.domain.User;

/**
 * 用户
 * <p>
 */
public class UserEx extends User {


    //角色名称
    private String roleName;
    //角色ID
    private Integer roleId;

    //公司Id
    private Integer companyInfoId;

    //桩站Id
    private Integer stationId;

    //桩站公司信息审核状态
    private Integer auditStatus;

    //微信openID
    private String openId;

    //桩站公司审核拒绝信息
    private String auditMessage;

    //是否有多家公司
    private boolean hasMoreStation;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditMessage() {
        return auditMessage;
    }

    public void setAuditMessage(String auditMessage) {
        this.auditMessage = auditMessage;
    }

    public Integer getCompanyInfoId() {
        return companyInfoId;
    }

    public void setCompanyInfoId(Integer companyInfoId) {
        this.companyInfoId = companyInfoId;
    }

    public boolean isHasMoreStation() {
        return hasMoreStation;
    }

    public void setHasMoreStation(boolean hasMoreStation) {
        this.hasMoreStation = hasMoreStation;
    }
}
