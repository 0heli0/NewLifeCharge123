package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.StationUserRefMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.StationUserRef;
import com.newlife.charge.core.dto.in.StationUserRefQuery;
import com.newlife.charge.core.dto.out.StationUserRefOut;
import com.newlife.charge.dao.common.CrudRepository;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StationUserRefMapper  extends CrudRepository<StationUserRef> {

    /**
     * @Description: 根据条件 查询桩站
     * @Author: Linzq
     * @CreateDate:  2019/5/7 0007 11:44
     */
    List<StationUserRefOut> getByUserIdAndCompanyId(StationUserRefQuery query);

    /**
     * @Description: 删除桩站用户关联
     * @Author: Linzq
     * @CreateDate:  2019/5/30 0030 11:08
     */
    void deleteByIdsAndStation(@Param("userIds") int[] userIds,@Param("stationId") Integer stationId);

    /**
     * @Description: 根据条件 查询桩站
     * @Author: Linzq
     * @CreateDate:  2019/5/30 0030 11:41
     */
    List<StationUserRefOut> getByQuery(StationUserRefQuery query);
}