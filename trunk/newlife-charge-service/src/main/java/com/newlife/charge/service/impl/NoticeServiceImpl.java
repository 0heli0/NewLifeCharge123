package com.newlife.charge.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.core.domain.Notice;
import com.newlife.charge.core.domain.NoticeUser;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.NoticeSaveIn;
import com.newlife.charge.core.dto.in.NoticeUpdateIn;
import com.newlife.charge.core.dto.out.NoticeOut;
import com.newlife.charge.core.dto.out.CarNoticeOut;
import com.newlife.charge.core.enums.ReadStatusEnum;
import com.newlife.charge.dao.NoticeMapper;
import com.newlife.charge.dao.NoticeUserMapper;
import com.newlife.charge.service.NoticeService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper mapper;

    @Autowired
    private NoticeUserMapper noticeUserMapper;


    @Autowired
    private Mapper dozer;


    @Override
    public PageInfo<NoticeOut> page(int pageNo, int pageSize,String title) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Notice> page = this.mapper.page(title);
        List<Notice> list = page.getResult();
        List<NoticeOut> outList = BeanMapper.mapList(list, NoticeOut.class);
        return new PageInfo<>(pageNo, pageSize, page.getTotal(), outList);
    }

    @Override
    public PageInfo<CarNoticeOut> carPage(int pageNo, int pageSize, Integer userId) {

        PageHelper.startPage(pageNo, pageSize);
        Page<CarNoticeOut> page = this.mapper.pageCar(userId);
        List<CarNoticeOut> list = page.getResult();
        return new PageInfo<>(pageNo, pageSize, page.getTotal(), list);
    }

    @Override
    public void carUpdateStatus(Integer userId, Integer noticeId, Integer readStatus) {
        //先删除
        noticeUserMapper.removeByUserIdAndNoticeId(userId, noticeId);

        //再插入一条插住站内信阅读记录
        NoticeUser noticeUser = new NoticeUser();
        noticeUser.setCreateTime(new Date());
        noticeUser.setReadTime(new Date());
        noticeUser.setNoticeId(noticeId);
        noticeUser.setStatus(ReadStatusEnum.READED.getValue());
        noticeUser.setUserId(userId);
        noticeUserMapper.insert(noticeUser);

    }

    @Override
    public NoticeOut info(Integer id) {
        Notice notice = this.mapper.get(id);
        return this.dozer.map(notice, NoticeOut.class);
    }

    @Override
    public void save(NoticeSaveIn saveIn) {
        Notice notice = this.dozer.map(saveIn, Notice.class);
        notice.setCreateTime(new Date());
        this.mapper.insert(notice);
    }

    @Override
    public void update(NoticeUpdateIn updateIn) {
        Notice notice = this.dozer.map(updateIn, Notice.class);
        this.mapper.update(notice);
    }

    @Override
    public void delete(Integer id) {
        this.deletes(new int[]{id});
    }

    @Override
    public void deletes(int[] ids) {
        this.mapper.deleteByIds(ids);
        //TODO：删除站内信时，是否需要删除站内容-用户关联表记录
    }
}
