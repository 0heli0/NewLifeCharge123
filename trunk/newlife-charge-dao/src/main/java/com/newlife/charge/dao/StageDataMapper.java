/* -------------------------------------------
 * stageDataMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-05-24 Created
 * ------------------------------------------- */


package com.newlife.charge.dao;

import com.newlife.charge.core.domain.StageData;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StageDataMapper extends CrudRepository<StageData> {

    /**
     * 批量保存
     *
     * @param stageDataList
     * @return
     */
    int insertBatch(@Param("stageDataList") List<StageData> stageDataList);

}