package com.newlife.charge.service;


import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.SuggestionOut;

/**
 * 站内信 service 类
 * <p>
 */
public interface SuggestionService {

    /**
     * 根据搜索条件分页查询
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return
     */
    PageInfo<SuggestionOut> page(int pageNo, int pageSize,Integer userId);


    /**
     * 意见反馈意见详情
     * @param id
     * @return
     */
    SuggestionOut info(Integer id);

    /**
     * 根据条件更新
     * @param query
     * @return
     */
    int update(SuggestionUpdateIn query);

    /**
     * 新增
     * @param info
     * @return
     */
    int insert(SuggestionSaveIn info);

    /**
     * 单个删除
     * @param info
     * @return
     */
    int delete(SuggestionDelIn info);

    /**
     * 批量删除
     * @param ids
     */
    void deleteByIds(SuggestionDelsIn ids);



}
