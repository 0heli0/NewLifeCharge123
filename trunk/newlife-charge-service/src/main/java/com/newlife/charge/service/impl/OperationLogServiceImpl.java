package com.newlife.charge.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.core.domain.OperationLog;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.OperationLogQueryIn;
import com.newlife.charge.core.dto.out.OperationLogInfoOut;
import com.newlife.charge.core.dto.out.OperationLogPageOut;
import com.newlife.charge.core.enums.log.PlatLogOperateType;
import com.newlife.charge.dao.OperationLogMapper;
import com.newlife.charge.service.OperationLogService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private Mapper dozer;

    @Override
    public void save(OperationLog operationLog) {
        this.operationLogMapper.insert(operationLog);
    }

    @Override
    public PageInfo<OperationLogPageOut> page(OperationLogQueryIn queryIn) {
        PageHelper.startPage(queryIn.getPageNo(), queryIn.getPageSize());

        Page<OperationLog> page = this.operationLogMapper.page(queryIn);
        List<OperationLog> list = page.getResult();
        List<OperationLogPageOut> outList = BeanMapper.mapList(list, OperationLogPageOut.class);
        if (outList != null && outList.size() > 0) {
            Iterator<OperationLogPageOut> it = outList.iterator();
            OperationLogPageOut info;
            Integer operationType;
            while (it.hasNext()) {
                info = it.next();
                operationType = info.getOperationType();
                if (operationType != null) {
                    info.setOperationTypeName(PlatLogOperateType.getDescriptionByValue(operationType));
                }
            }
        }

        return new PageInfo<>(queryIn.getPageNo(), queryIn.getPageSize(), page.getTotal(), outList);
    }

    @Override
    public OperationLogInfoOut info(Integer id) {
        OperationLogInfoOut result = null;
        OperationLog info = this.operationLogMapper.get(id);

        if (info != null) {
            result = dozer.map(info, OperationLogInfoOut.class);
            result.setOperationTypeName(PlatLogOperateType.getDescriptionByValue(result.getOperationType()));
        }

        return  result;

    }
}


