package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.NoticeMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.Notice;
import com.newlife.charge.core.dto.out.CarNoticeOut;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

public interface NoticeMapper extends CrudRepository<Notice> {


    /**
     * 分页 或 列表查询
     *
     * @param title 标题
     * @return
     */
    Page<Notice> page(@Param("title") String title);

    /**
     * 车主 分页 或 列表查询
     *
     * @return
     */
    Page<CarNoticeOut> pageCar(@Param("userId") Integer userId);

    /**
     * 查询新站内信数量
     * @param userId
     * @return
     */
    int getNewCount(Integer userId);
}