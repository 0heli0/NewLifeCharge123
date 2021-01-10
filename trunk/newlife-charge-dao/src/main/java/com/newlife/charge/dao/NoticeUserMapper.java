package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.NoticeUserMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.NoticeUser;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

public interface NoticeUserMapper  extends CrudRepository<NoticeUser> {

    /**
     * 根据车主用户Id和站内信ID查询
     * @param userId
     * @param noticeId
     * @return
     */
    int removeByUserIdAndNoticeId(@Param("userId") Integer userId,@Param("noticeId") Integer noticeId);


}