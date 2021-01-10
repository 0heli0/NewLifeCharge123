package com.newlife.charge.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.common.DateUtils;
import com.newlife.charge.core.domain.GeneralizeStationLog;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.GeneralizeStationLogOut;
import com.newlife.charge.dao.GeneralizeStationLogMapper;
import com.newlife.charge.service.GeneralizeStationLogService;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 推广建站记录 service 类
 * <p>
 */
@Service
public class GeneralizeStationLogServiceImpl implements GeneralizeStationLogService {


    @Autowired
    private GeneralizeStationLogMapper stationLogMapper;

    @Autowired
    private Mapper dozer;

    @Override
    public PageInfo<GeneralizeStationLogOut> page(GeneralizeStationLogQueryIn query, int pageNo, int pageSize) {

        PageHelper.startPage(pageNo, pageSize);

        Page<GeneralizeStationLog> page = stationLogMapper.page(query);
        List<GeneralizeStationLog> list = page.getResult();
        List<GeneralizeStationLogOut> resultList = BeanMapper.mapList(list, GeneralizeStationLogOut.class);

        return new PageInfo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), resultList);
    }


    /**
     * 添加
     *
     * @param saveIn
     * @return
     */
    @Override
    public int insert(GeneralizeStationLogSaveIn saveIn) {

        if (saveIn != null) {
            String buildTime = saveIn.getBuildTime();
            saveIn.setBuildTime(null);

            Date buildTime_date = null;
            try {
                if (StringUtils.isNotBlank(buildTime)) {
                    buildTime_date = DateUtils.parseDate(buildTime, "yyyy-MM-dd");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            GeneralizeStationLog info = dozer.map(saveIn, GeneralizeStationLog.class);
            info.setBuildTime(buildTime_date);

            // 添加创建时间
            info.setCreateTime(new Date());

            return this.stationLogMapper.insert(info);
        } else {
            return 0;
        }

    }

    @Override
    public int update(GeneralizeStationLogUpdateIn updateIn) {
        GeneralizeStationLog info = dozer.map(updateIn, GeneralizeStationLog.class);
        int result = stationLogMapper.update(info);
        return result;
    }

    @Override
    public int delete(GeneralizeStationLogDelIn delIn) {
        GeneralizeStationLog info = dozer.map(delIn, GeneralizeStationLog.class);
        int result = stationLogMapper.delete(info);
        return result;
    }

    @Override
    public void deleteByIds(GeneralizeStationLogDelsIn delsIn) {

        stationLogMapper.deleteByIds(delsIn.getIds());
    }
}
