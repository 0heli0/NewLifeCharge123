/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-20 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.core.domain.page.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 总后台账号分页查询
 */
@ApiModel
public class GeneralUserPageIn extends Pageable {


    //账号名称，对应用户昵称
    @ApiModelProperty(value = "账号名称,最大长度50位")
    @Length(max = 50, message = "账号名称长度不能超过50位")
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}