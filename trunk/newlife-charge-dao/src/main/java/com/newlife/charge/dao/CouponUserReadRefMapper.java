/* -------------------------------------------
 * CouponUserReadRefMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-26 Created
 * ------------------------------------------- */


package com.newlife.charge.dao;

import com.newlife.charge.core.domain.CouponUserReadRef;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponUserReadRefMapper extends CrudRepository<CouponUserReadRef> {

    /**
     * 批量添加
     * @param list
     */
    void batchInsert(@Param("list") List<CouponUserReadRef> list);

    List<CouponUserReadRef> getByQuery(CouponUserReadRef query);


}