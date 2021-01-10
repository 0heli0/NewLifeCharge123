package com.newlife.charge.service;


import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.NoticeSaveIn;
import com.newlife.charge.core.dto.in.NoticeUpdateIn;
import com.newlife.charge.core.dto.out.NoticeOut;
import com.newlife.charge.core.dto.out.CarNoticeOut;

/**
 * 站内信 service 类
 * <p>
 */
public interface NoticeService {

    /**
     * 总后台分页查询
     * @param pageNo
     * @param pageSize
     * @param title 标题
     * @return
     */
    PageInfo<NoticeOut> page(int pageNo, int pageSize,String title);

    /**
     * 车主用户小程序分页查询
     *
     * @return
     */
    PageInfo<CarNoticeOut> carPage(int pageNo, int pageSize, Integer userId);


    /**
     * 车主 站内信更新阅读状态
     *
     * @param userId
     * @param noticeId
     * @param readStatus
     */
    void carUpdateStatus(Integer userId, Integer noticeId, Integer readStatus);


    /**
     * 总后台站内信详情
     *
     * @param id
     * @return
     */
    NoticeOut info(Integer id);

    /**
     * 总后台站内信新增
     *
     * @param saveIn
     */
    void save(NoticeSaveIn saveIn);

    /**
     * 总后台 站内信更新
     *
     * @param updateIn
     */
    void update(NoticeUpdateIn updateIn);

    /**
     * 总后台 站内信单条删除
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 总后台站内信批量删除
     *
     * @param ids
     */
    void deletes(int[] ids);

}
