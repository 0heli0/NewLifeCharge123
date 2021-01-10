package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.PreChargeMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.PreCharge;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PreChargeMapper extends CrudRepository<PreCharge> {

    /**
     * 根据条件分页查询
     *
     * @return
     */
    Page<PreCharge> page();


    /**
     * 批量充值
     *
     * @param saveIn
     * @return
     */
    int batchSave(@Param("list") List<PreCharge> saveIn);

    /**
     * 根据面额查找
     * @param originalPrice
     * @return
     */
    PreCharge getByPrice(BigDecimal originalPrice);
}