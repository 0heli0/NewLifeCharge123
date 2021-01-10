package com.newlife.charge.service;


import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.GeneralizeStationLogOut;
import com.newlife.charge.core.dto.out.SuggestionOut;

/**
 * 站内信 service 类
 * <p>
 */
public interface GeneralizeStationLogService {

    /**
     * 根据搜索条件分页查询
     * @param query
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<GeneralizeStationLogOut> page(GeneralizeStationLogQueryIn query, int pageNo, int pageSize);

    /**
     * 保存
     * @param saveIn
     * @return
     */
    int insert(GeneralizeStationLogSaveIn saveIn);

    /**
     * 根据条件更新
     * @param updateIn
     * @return
     */
    int update(GeneralizeStationLogUpdateIn updateIn);

    /**
     * 单个删除
     * @param delIn
     * @return
     */
    int delete(GeneralizeStationLogDelIn delIn);

    /**
     * 批量删除
     * @param delsIn
     */
    void deleteByIds(GeneralizeStationLogDelsIn delsIn);



}
