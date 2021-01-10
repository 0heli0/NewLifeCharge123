/* -------------------------------------------
 * ClientHistoryDataMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-05-24 Created
 * ------------------------------------------- */


package com.newlife.charge.dao;

import com.newlife.charge.core.domain.ClientHistoryData;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

public interface ClientHistoryDataMapper extends CrudRepository<ClientHistoryData> {


    /**
     * 根据条件查询
     *
     * @param stationClientCode 充电桩编号
     * @param recordNo          记录流水号
     * @param recordStorageNo   记录存储序号
     * @return
     */
    ClientHistoryData getByParams(@Param("stationClientCode") String stationClientCode,
                                  @Param("recordNo") Integer recordNo,
                                  @Param("recordStorageNo") Integer recordStorageNo);

}