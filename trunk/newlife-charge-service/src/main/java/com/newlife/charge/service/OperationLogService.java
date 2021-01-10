package com.newlife.charge.service;

import com.newlife.charge.core.domain.OperationLog;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.OperationLogQueryIn;
import com.newlife.charge.core.dto.out.OperationLogInfoOut;
import com.newlife.charge.core.dto.out.OperationLogPageOut;

/**
 * 操作日志
 * <p>
 */
public interface OperationLogService {

    /**
     * 操作日志
     *
     * @param operationLog
     */
    void save(OperationLog operationLog);

    /**
     * 分页查询 操作日志
     *
     * @param queryIn
     * @return
     */
    PageInfo<OperationLogPageOut> page(OperationLogQueryIn queryIn);

    /**
     * 查看日志详情
     *
     * @param id 日志主键ID
     * @return
     */
    OperationLogInfoOut info(Integer id);

}
