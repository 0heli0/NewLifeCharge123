package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.UserWeixinMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.UserWeixin;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

public interface UserWeixinMapper  extends CrudRepository<UserWeixin> {

    /**
     * 通过userId获取用户微信信息
     * @param userId
     * @return
     */
    UserWeixin getByUserId(@Param("userId") Integer userId);
}