/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 总后台 审核通过的公司详情
 */
@Setter
@Getter
@ApiModel
public class StationCompanyInfoOut {

    //公司ID
    @ApiModelProperty(value = "公司ID")
    private Integer id;

    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    //统一社会信用代码
    @ApiModelProperty(value = "统一社会信用代码")
    private String companyCode;

    //管理员姓名
    @ApiModelProperty(value = "管理员姓名")
    private String managerName;

    //管理员身份证号
    @ApiModelProperty(value = "管理员身份证号")
    private String managerIdCardNo;

    //银行ID
    @ApiModelProperty(value = "银行ID")
    private Integer bankId;

    //银行名称
    @ApiModelProperty(value = "银行名称")
    private String bankName;

    //银行卡账号
    @ApiModelProperty(value = "银行卡账号")
    private String debitCardNo;

    //开户省ID
    @ApiModelProperty(value = "开户省ID")
    private Integer bankProvinceId;

    //开户省名称
    @ApiModelProperty(value = "开户省名称")
    private String bankProvinceName;

    //开户市ID
    @ApiModelProperty(value = "开户市ID")
    private Integer bankCityId;

    //开户市名称
    @ApiModelProperty(value = "开户市名称")
    private String bankCityName;

    //开户支行名称
    @ApiModelProperty(value = "开户支行名称")
    private String subBankName;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //营业执照
    @ApiModelProperty(value = "营业执照")
    private String companyImg;

    //管理员手持身份证照片（身份证正面+本人拍照）
    @ApiModelProperty(value = "管理员手持身份证照片正面")
    private String managerHandIdCardImg;

    //管理员手持身份证照片反面（身份证反面+本人拍照）
    @ApiModelProperty(value = "管理员手持身份证照片反面")
    private String managerHandIdCardImgBack;

    //审核状态：0审核中 1审核通过 2审核不通过
    @ApiModelProperty(value = "审核状态：0审核中 1审核通过 2审核不通过")
    private Integer auditStatus;

    //审核时间
    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    //备注
    @ApiModelProperty(value = "备注")
    private String remark;

    //银行卡户地址
    @ApiModelProperty(value = "银行卡户地址")
    private String bankAddress;

    //抽佣比例(小数)
    @ApiModelProperty(value = "抽佣比例(小数)")
    private BigDecimal commissionRation;

}