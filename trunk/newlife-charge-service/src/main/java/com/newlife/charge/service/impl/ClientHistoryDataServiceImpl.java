package com.newlife.charge.service.impl;

import com.newlife.charge.common.Collections3;
import com.newlife.charge.common.RedisUtils;
import com.newlife.charge.common.SpringContextHolder;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.*;
import com.newlife.charge.core.domain.exModel.StationClientGunEx;
import com.newlife.charge.core.domain.exModel.TruckSpaceEx;
import com.newlife.charge.core.dto.in.PileHistoryDataIn;
import com.newlife.charge.core.dto.in.StageDataIn;
import com.newlife.charge.core.enums.GunStatusEnum;
import com.newlife.charge.core.enums.NewLifeSpendLogTypeEnum;
import com.newlife.charge.core.enums.NewLifeSpendLogTypeRemarkEnum;
import com.newlife.charge.core.enums.UserTypeEnum;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.*;
import com.newlife.charge.service.ClientHistoryDataService;
import com.newlife.charge.service.WebSocketService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ClientHistoryDataServiceImpl implements ClientHistoryDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHistoryDataService.class);

    @Autowired
    private ClientHistoryDataMapper mapper;

    @Autowired
    private StageDataMapper stageDataMapper;

    @Autowired
    private StationClientGunMapper stationClientGunMapper;

    @Autowired
    private Mapper dozer;

    @Override
    public ClientHistoryData getByParams(String stationClientCode, Integer recordNo,Integer recordStorageNo) {
        return this.mapper.getByParams(stationClientCode,recordNo,recordStorageNo);
    }

    @Override
    public int dealPileHistoryData(PileHistoryDataIn pileHistoryDataIn) {
        //1.保存历史数据
        int result = this.saveHistoryData(pileHistoryDataIn);
        //2.TODO:如果有其他业务，在这里启动线程去处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO:2.扣款、生成新活资金流水、处理订单


                // 执行充电停止成功后操作
                WebSocketService webSocketService = SpringContextHolder.getBean(WebSocketService.class);
                webSocketService.chargeStopSuccess(pileHistoryDataIn);
            }
        }).start();

        return result;
    }

    private int saveHistoryData(PileHistoryDataIn pileHistoryDataIn) {

        //存库或者放弃
        synchronized (this){
            String stationClientCode = pileHistoryDataIn.getStationClientCode();
            Integer recordNo = pileHistoryDataIn.getRecordNo();
            Integer recordStorageNo = pileHistoryDataIn.getRecordStorageNo();
            ClientHistoryData historyData = this.getByParams(stationClientCode, recordNo,recordStorageNo);
            if(historyData!=null){
                LOGGER.info(stationClientCode+" "+recordNo+" "+recordStorageNo+" 历史记录已经处理，本次不再处理");
                return 0;
            }else{
                Date now = new Date();
                List<StageDataIn> stageDataInList = pileHistoryDataIn.getStageDataInList();
                ClientHistoryData clientHistoryData = this.dozer.map(pileHistoryDataIn, ClientHistoryData.class);
                clientHistoryData.setId(null);
                clientHistoryData.setCreateTime(now);
                int insert = this.mapper.insert(clientHistoryData);
                Integer clientHistoryDataId = clientHistoryData.getId();
                if (Collections3.isNotEmpty(stageDataInList)) {
                    List<StageData> stageDataList = new ArrayList<>();
                    stageDataInList.stream().forEach(item -> {
                        StageData stageData = dozer.map(item, StageData.class);
                        stageData.setClientHistoryId(clientHistoryDataId);
                        stageData.setCreateTime(now);
                        stageDataList.add(stageData);
                    });
                    this.stageDataMapper.insertBatch(stageDataList);
                }
                return insert;
            }
        }
    }

    /**
     * 获取redis车位key
     * @param prefix key前缀
     * @param gunId 充电枪id
     * @return key
     */
    public String getKeyByGun(String prefix, Integer gunId){

        // 查询充电枪详情
        StationClientGunEx detail = this.stationClientGunMapper.getInfoByParams(gunId,
                null, null, null, null, null);

        if(detail == null){
            LOGGER.info("===> 车位信息查询失败");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        String key = prefix+detail.getStationId()+"_"+detail.getId()+"_"+detail.getCode();

        if(key == null){
            LOGGER.info("===> 车位信息错误");
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        return key;
    }
}
