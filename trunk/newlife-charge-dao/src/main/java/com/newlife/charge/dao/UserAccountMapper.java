/* -------------------------------------------
 * UserAccountMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-30 Created
 * ------------------------------------------- */


package com.newlife.charge.dao;

import com.newlife.charge.core.domain.UserAccount;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

public interface UserAccountMapper extends CrudRepository<UserAccount> {

    /**
     * 通过用户id获取账户信息
     * @param userId
     * @return
     */
    UserAccount getByUserId(@Param("userId") Integer userId);

}