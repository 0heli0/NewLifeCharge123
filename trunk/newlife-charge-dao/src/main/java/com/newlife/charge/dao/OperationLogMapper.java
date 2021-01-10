package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.OperationLogMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.OperationLog;
import com.newlife.charge.core.dto.in.OperationLogDetailIn;
import com.newlife.charge.core.dto.in.OperationLogQueryIn;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface OperationLogMapper  extends CrudRepository<OperationLog> {

    /**
     * 分页或列表查询
     *
     * @param queryIn
     * @return
     */
    Page<OperationLog> page(OperationLogQueryIn queryIn);

    /**
     * 日志详情查看
     * @param detailIn
     * @return
     */
    OperationLog detailInfo(OperationLogDetailIn detailIn);
}