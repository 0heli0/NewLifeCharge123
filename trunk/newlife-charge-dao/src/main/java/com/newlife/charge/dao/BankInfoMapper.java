package com.newlife.charge.dao;
/* -------------------------------------------
 * com.newlife.charge.dao.BankInfoMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.BankInfo;
import com.newlife.charge.core.dto.out.BankInfoOut;
import com.newlife.charge.dao.common.CrudRepository;

import java.util.List;

public interface BankInfoMapper extends CrudRepository<BankInfo> {

    /**
     * 银行信息分页 或 列表查询
     *
     * @return
     */
    List<BankInfoOut> page();
}