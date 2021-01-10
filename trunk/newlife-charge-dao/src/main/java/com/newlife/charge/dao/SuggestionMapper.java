package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.SuggestionMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.Suggestion;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

public interface SuggestionMapper extends CrudRepository<Suggestion> {

    /**
     * 根据搜索条件分页查询
     *
     * @param userId
     * @return
     */
    Page<Suggestion> page(@Param("userId") Integer userId);
}