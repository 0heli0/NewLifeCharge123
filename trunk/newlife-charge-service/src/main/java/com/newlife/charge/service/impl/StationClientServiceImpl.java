package com.newlife.charge.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.core.domain.StationClient;
import com.newlife.charge.core.domain.StationClientGun;
import com.newlife.charge.core.domain.StationParkingLot;
import com.newlife.charge.core.domain.exModel.StationClientEx;
import com.newlife.charge.core.domain.exModel.StationInfoEx;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.StationClientPageIn;
import com.newlife.charge.core.dto.in.StationClientSaveIn;
import com.newlife.charge.core.dto.in.StationClientUpdateIn;
import com.newlife.charge.core.dto.out.SelectListOut;
import com.newlife.charge.core.dto.out.StationClientGunNumOut;
import com.newlife.charge.core.dto.out.StationClientInfoOut;
import com.newlife.charge.core.dto.out.StationClientPageOut;
import com.newlife.charge.core.enums.ChargeInterfaceEnum;
import com.newlife.charge.core.enums.ClientChargeTypeEnum;
import com.newlife.charge.core.enums.GunTypeEnum;
import com.newlife.charge.core.enums.YesNoEnum;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.StationClientMapper;
import com.newlife.charge.service.StationClientGunService;
import com.newlife.charge.service.StationClientService;
import com.newlife.charge.service.StationInfoService;
import com.newlife.charge.service.StationParkingLotService;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class StationClientServiceImpl implements StationClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StationClientServiceImpl.class);

    @Autowired
    private StationClientMapper mapper;


    @Autowired
    private StationInfoService stationInfoService;

    @Autowired
    private StationParkingLotService stationParkingLotService;

    @Autowired
    private StationClientGunService stationClientGunService;


    @Autowired
    private Mapper dozer;

    @Override
    public List<String> getAllStationClientCode() {
        return this.mapper.getAllStationClientCode();
    }

    @Override
    public List<StationClient> getByStationId(Integer stationId) {
        return this.mapper.getByStationId(stationId);
    }

    @Override
    public List<StationClientGunNumOut> getGunNumGroupByStationClientId(Integer stationId) {
        return this.mapper.getGunNumGroupByStationClientId(stationId);
    }

    @Override
    public List<SelectListOut> selectList(Integer stationId, Integer stationClientId) {

        List<SelectListOut> outList = new ArrayList<>();

        //某桩站下所有充电桩
        List<StationClient> list = this.getByStationId(stationId);
        //某桩站下各充电桩绑定充电枪数量统计
        List<StationClientGunNumOut> gunNumOutList = this.getGunNumGroupByStationClientId(stationId);
        Map<Integer, Integer> gunNumMap = new HashMap<>();
        gunNumOutList.stream().forEach(item -> {
            gunNumMap.put(item.getStationClientId(), item.getNum());
        });
        Iterator<StationClient> it = list.iterator();
        StationClient stationClient;
        while (it.hasNext()) {
            stationClient = it.next();
            Integer clientId = stationClient.getId();
            Integer gunNum = gunNumMap.get(clientId);
            if ((stationClientId != null && stationClientId.equals(clientId)) || (gunNum < stationClient.getGunType())) {
                //特定充电桩或者充电桩绑定的充电枪未达上限
                SelectListOut out = new SelectListOut();
                out.setId(clientId);
                out.setName(stationClient.getCode());
                outList.add(out);
            }
        }

        return outList;
    }

    @Override
    public Integer countByStationId(Integer stationId) {
        return this.mapper.countByStationId(stationId);
    }

    @Override
    public Integer countByCompanyId(Integer companyId) {
        return this.mapper.countByCompanyId(companyId);
    }

    @Override
    public StationClientInfoOut getInfoById(Integer id) {
        StationClientInfoOut info = null;

        StationClientEx stationClientEx = this.mapper.getInfoByParams(id, null, null);

        if (stationClientEx != null) {
            info = this.dozer.map(stationClientEx, StationClientInfoOut.class);
            //类型名称转换
            info.setGunTypeName(GunTypeEnum.getDescriptionByValue(info.getGunType()));
            info.setChargeTypeName(ClientChargeTypeEnum.getDescriptionByValue(info.getChargeType()));
            info.setChargeInterfaceTypeName(ChargeInterfaceEnum.getDescriptionByValue(info.getChargeInterfaceType()));


            List<String> stationParkingLotCodeList;
            List<String> stationClientGunCodeList;
            //TODO:绑定车位编号,重构优化
            stationParkingLotCodeList = new ArrayList<>();
            List<StationParkingLot> stationParkingLotList = this.stationParkingLotService.getInfoByStationClientId(stationClientEx.getId());
            if (stationParkingLotList != null && stationParkingLotList.size() > 0) {
                Iterator<StationParkingLot> it_lot = stationParkingLotList.iterator();
                while (it_lot.hasNext()) {
                    stationParkingLotCodeList.add(it_lot.next().getCode());
                }
            }
            info.setStationParkingLotCodes(StringUtils.join(stationParkingLotCodeList, ","));

            //TODO:绑定充电枪编号,重构优化
            stationClientGunCodeList = new ArrayList<>();
            List<StationClientGun> stationClientGunList = this.stationClientGunService.getInfoByStationClientId(stationClientEx.getId());
            if (stationClientGunList != null && stationClientGunList.size() > 0) {
                Iterator<StationClientGun> it_gun = stationClientGunList.iterator();
                while (it_gun.hasNext()) {
                    stationClientGunCodeList.add(it_gun.next().getCode());
                }
            }
            info.setStationClientGunCodes(StringUtils.join(stationClientGunCodeList, ","));


        }
        return info;
    }

    @Override
    public StationClientEx getByCode(String code) {
        return this.mapper.getByCode(code);
    }

    @Override
    public List<StationClientEx> getByCodeList(List<String> codeList) {
        return this.mapper.getByCodeList(codeList);
    }

    @Override
    public PageInfo<StationClientPageOut> page(StationClientPageIn pageIn) {
        PageHelper.startPage(pageIn.getPageNo(), pageIn.getPageSize());
        Page<StationClientPageOut> page = this.mapper.page(pageIn.getCode(), pageIn.getGunType());
        List<StationClientPageOut> list = page.getResult();
        if (list != null && list.size() > 0) {
            Iterator<StationClientPageOut> it = list.iterator();
            StationClientPageOut next;
            List<String> stationParkingLotCodeList;
            List<String> stationClientGunCodeList;
            while (it.hasNext()) {
                next = it.next();
                //TODO:绑定车位编号,重构优化
                stationParkingLotCodeList = new ArrayList<>();
                List<StationParkingLot> stationParkingLotList = this.stationParkingLotService.getInfoByStationClientId(next.getId());
                if (stationParkingLotList != null && stationParkingLotList.size() > 0) {
                    Iterator<StationParkingLot> it_lot = stationParkingLotList.iterator();
                    while (it_lot.hasNext()) {
                        stationParkingLotCodeList.add(it_lot.next().getCode());
                    }
                }
                next.setStationParkingLotCodes(StringUtils.join(stationParkingLotCodeList, ","));

                //TODO:绑定充电枪编号,重构优化
                stationClientGunCodeList = new ArrayList<>();
                List<StationClientGun> stationClientGunList = this.stationClientGunService.getInfoByStationClientId(next.getId());
                if (stationClientGunList != null && stationClientGunList.size() > 0) {
                    Iterator<StationClientGun> it_gun = stationClientGunList.iterator();
                    while (it_gun.hasNext()) {
                        stationClientGunCodeList.add(it_gun.next().getCode());
                    }
                }
                next.setStationClientGunCodes(StringUtils.join(stationClientGunCodeList, ","));

                //设置枪类型名称
                next.setGunTypeName(GunTypeEnum.getDescriptionByValue(next.getGunType()));
            }
        }

        return new PageInfo<>(pageIn.getPageNo(), pageIn.getPageSize(), page.getTotal(), page.getResult());
    }


    @Override
    public void save(StationClientSaveIn saveIn) {
        StationClient saveObj = null;
        //桩站存在校验
        StationInfoEx stationInfoEx = stationInfoService.getById(saveIn.getStationId());
        if (stationInfoEx == null) {
            //桩站不存在
            throw new BizException(ERROR.STATION_INFO_NOT_EXIST);
        }
        if (!(YesNoEnum.NO.getValue().intValue() == stationInfoEx.getDelFlag())) {
            //桩站已删除
            throw new BizException(ERROR.STATION_INFO_DELETED);
        }

        //保存信息
        saveObj = this.dozer.map(saveIn, StationClient.class);
        saveObj.setCreateTime(new Date());

        //充电桩编号后台自动生成,不能修改
        synchronized (this){
            String stationClientCode = this.createStationClientCode(saveIn.getStationId(), stationInfoEx.getType());
            //1.充电桩编号全平台唯一性校验
            StationClientEx client_db = this.getByCode(stationClientCode);
            if (client_db != null) {
                LOGGER.info(stationClientCode+ERROR.EXISTS_STATION_CODE.message());
                throw new BizException(ERROR.EXISTS_STATION_CODE);
            }
            saveObj.setCode(stationClientCode);
            saveObj.setTerminalNo(stationClientCode);
            this.mapper.insert(saveObj);
        }

    }

    @Override
    public String getMaxCode(Integer stationId, Integer stationType) {
        return this.mapper.getMaxCode(stationId, stationType);
    }

    /**
     * 生成充电桩编号
     * 桩站ID（4位，桩站ID格式化成4位）+电站类型（1位）+自然编号组成（3位，基于电站编号+电站类型下进行自增长）
     *
     * @param stationId   桩站ID
     * @param stationType 桩站类型（0.公用 1.专用 2.私人）
     * @return
     */
    private String createStationClientCode(Integer stationId, Integer stationType) {
        StringBuilder builder = new StringBuilder();
        //桩站Id格式化
        String stationCode = StringUtils.leftPad(String.valueOf(stationId), 4, "0");
        //桩站类型格式化
        String stationTypeCode = String.valueOf(stationType);
        //自然编号
        String numCode = "001";//默认001

        //查询某桩站某类型下充电桩最大自然编号
        String maxCode = this.getMaxCode(stationId, stationType);
        if (StringUtils.isNotBlank(maxCode)) {
            numCode = maxCode.substring(5);
            numCode = StringUtils.leftPad(String.valueOf(Integer.valueOf(numCode) + 1), 3, "0");//自增1并格式化
        }
        builder.append(stationCode).append(stationTypeCode).append(numCode);
        return builder.toString();
    }


    @Override
    public void update(StationClientUpdateIn updateIn) {
        StationClient updateObj = null;
        StationClient updateObj_db = null;
        //桩站存在校验
        StationInfoEx stationInfoEx = stationInfoService.getById(updateIn.getStationId());
        if (stationInfoEx == null) {
            //桩站不存在
            throw new BizException(ERROR.STATION_INFO_NOT_EXIST);
        }
        if (!(YesNoEnum.NO.getValue().intValue() == stationInfoEx.getDelFlag())) {
            //桩站已删除
            throw new BizException(ERROR.STATION_INFO_DELETED);
        }

        updateObj_db = this.mapper.get(updateIn.getId());

        if (updateObj_db == null) {
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        updateObj = this.dozer.map(updateIn, StationClient.class);

        //充电桩已绑定充电枪，所属桩站和枪类型不能更新
        List<StationClientGun> gunList = this.stationClientGunService.getInfoByStationClientId(updateIn.getId());
        if (gunList != null && gunList.size() > 0) {
            updateObj.setStationId(updateObj_db.getStationId());
            updateObj.setGunType(updateObj_db.getGunType());
        }


        //充电桩编号、终端号、创建时间均不能修改
        updateObj.setCode(updateObj_db.getCode());
        updateObj.setTerminalNo(updateObj_db.getTerminalNo());
        updateObj.setCreateTime(updateObj_db.getCreateTime());

        this.mapper.update(updateObj);
    }

    @Override
    public void delete(Integer id) {
        this.deletes(new int[]{id});
    }

    @Override
    public void deletes(int[] ids) {
        if (ids.length > 0) {
            List<StationClientGun> gunList;
            for (int id : ids) {
                gunList = this.stationClientGunService.getInfoByStationClientId(id);
                if (gunList != null && gunList.size() > 0) {
                    //已绑定充电枪的充电桩不能删除
                    throw new BizException(ERROR.STATION_CLIENT_HAS_GUN);
                }
            }
            this.mapper.deleteByIds(ids);
        }
    }
}
