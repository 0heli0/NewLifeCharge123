package com.newlife.charge.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.Suggestion;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.SuggestionDelIn;
import com.newlife.charge.core.dto.in.SuggestionDelsIn;
import com.newlife.charge.core.dto.in.SuggestionSaveIn;
import com.newlife.charge.core.dto.in.SuggestionUpdateIn;
import com.newlife.charge.core.dto.out.SuggestionOut;
import com.newlife.charge.dao.SuggestionMapper;
import com.newlife.charge.service.SuggestionService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 站内信 service 类
 * <p>
 */
@Service
public class SuggestionServiceImpl implements SuggestionService {

    @Autowired
    private SuggestionMapper suggestionMapper;

    @Autowired
    private Mapper dozer;

    /**
     * 根据搜索条件分页查询
     *
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return
     */
    @Override
    public PageInfo<SuggestionOut> page(int pageNo, int pageSize, Integer userId) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Suggestion> page = this.suggestionMapper.page(userId);
        List<Suggestion> list = page.getResult();
        List<SuggestionOut> result = BeanMapper.mapList(list, SuggestionOut.class);
        return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), result);
    }

    /**
     * 查看意见详情
     *
     * @param id
     * @return
     */
    @Override
    public SuggestionOut info(Integer id) {
        Suggestion result = this.suggestionMapper.get(id);
        return dozer.map(result, SuggestionOut.class);
    }

    /**
     * 根据条件更新
     *
     * @param entry
     * @return
     */
    @Override
    public int update(SuggestionUpdateIn entry) {
        Suggestion suggestionInfo = dozer.map(entry, Suggestion.class);
        int result = this.suggestionMapper.update(suggestionInfo);
        return result;
    }

    /**
     * 添加
     *
     * @param info
     * @return
     */
    @Override
    public int insert(SuggestionSaveIn info) {

        if (info != null) {
            Suggestion suggestionInfo = dozer.map(info, Suggestion.class);
            // 添加创建时间
            suggestionInfo.setCreateTime(new Date());
            // 添加用户id
            if (UserContext.getUserId() != null) {
                suggestionInfo.setUserId(UserContext.getUserId());
            }

            int result = this.suggestionMapper.insert(suggestionInfo);
            return result;
        } else {
            return 0;
        }

    }

    /**
     * 单个删除
     *
     * @param entry
     * @return
     */
    @Override
    public int delete(SuggestionDelIn entry) {
        Suggestion suggestionInfo = dozer.map(entry, Suggestion.class);
        int result = this.suggestionMapper.delete(suggestionInfo);
        return result;
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(SuggestionDelsIn ids) {
        this.suggestionMapper.deleteByIds(ids.getIds());
    }


}
