package com.newlife.charge.service;


import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.PreChargeOut;

import java.util.List;

/**
 * 预充值 service 类
 * <p>
 */
public interface PreChargeService {

    /**
     * 保存单个
     * @param saveIn
     * @return
     */
   int insertOne(PreChargeSaveIn saveIn);

    /**
     * 批量添加
     * @param saveIn
     * @return
     */
   int batchSave(PreChargeBatchSaveIn saveIn);

    /**
     * 预充值更新
     * @param updateIn
     * @return
     */
   int update(PreChargeUpdateIn updateIn);

    /**
     * 分页查询
     * @return
     */
   PageInfo<PreChargeOut> page(int pageNo,int pageSize);

    /**
     * 查找预充值列表
     * @return
     */
    List<PreChargeOut> preChargeList();

    /**
     * 查询单个信息
     * @param id
     * @return
     */
   PreChargeOut info(int id);

    /**
     * 根据id删除
     * @param deleteIn
     * @return
     */
   int delete(PreChargeDeleteIn deleteIn);

    /**
     * 批量删除
     * @param deleteIdsIn
     */
   void deleteByIds(PreChargeDeleteIdsIn deleteIdsIn);



}
