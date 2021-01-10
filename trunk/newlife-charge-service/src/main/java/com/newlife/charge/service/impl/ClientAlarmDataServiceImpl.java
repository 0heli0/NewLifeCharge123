package com.newlife.charge.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.core.domain.ClientAlarmData;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.ClientAlarmDataPageIn;
import com.newlife.charge.core.dto.in.PileAlarmDataIn;
import com.newlife.charge.core.dto.out.ClientAlarmDataPageOut;
import com.newlife.charge.dao.ClientAlarmDataMapper;
import com.newlife.charge.service.ClientAlarmDataService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Service
public class ClientAlarmDataServiceImpl implements ClientAlarmDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientAlarmDataService.class);

    @Autowired
    private ClientAlarmDataMapper mapper;


    @Autowired
    private Mapper dozer;

    @Override
    public PageInfo<ClientAlarmDataPageOut> page(ClientAlarmDataPageIn pageIn) {
        PageHelper.startPage(pageIn.getPageNo(), pageIn.getPageSize());
        Page<ClientAlarmDataPageOut> page = this.mapper.page(pageIn.getStationId(), pageIn.getStartTime(), pageIn.getEndTime());

        return new PageInfo<>(pageIn.getPageNo(), pageIn.getPageSize(), page.getTotal(), page.getResult());
    }

    @Override
    public ClientAlarmData getByParams(String stationClientCode, Integer recordNo, Integer recordStorageNo) {
        return this.mapper.getByParams(stationClientCode, recordNo, recordStorageNo);
    }

    @Override
    public int dealPileAlarmData(PileAlarmDataIn pileAlarmDataIn) {
        //1.保存数据
        int result = this.saveAlarmData(pileAlarmDataIn);
        return result;
    }

    private int saveAlarmData(PileAlarmDataIn pileAlarmDataIn) {

        //存库或者放弃
        synchronized (this) {
            String stationClientCode = pileAlarmDataIn.getStationClientCode();
            Integer recordNo = pileAlarmDataIn.getRecordNo();
            Integer recordStorageNo = pileAlarmDataIn.getRecordStorageNo();
            ClientAlarmData alarmData = this.getByParams(stationClientCode, recordNo, recordStorageNo);
            if (alarmData != null) {
                LOGGER.info(stationClientCode + " " + recordNo + " " + recordStorageNo + " 告警记录已经处理，本次不再处理");
                return 0;
            } else {
                Date now = new Date();
                ClientAlarmData clientAlarmData = this.dozer.map(pileAlarmDataIn, ClientAlarmData.class);
                clientAlarmData.setId(null);
                clientAlarmData.setCreateTime(now);
                int insert = this.mapper.insert(clientAlarmData);
                return insert;
            }
        }
    }


}
