package com.newlife.charge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.common.Collections3;
import com.newlife.charge.common.SpringContextHolder;
import com.newlife.charge.common.rpc.Ok3HttpUtil;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.StationClientGun;
import com.newlife.charge.core.domain.exModel.StationClientEx;
import com.newlife.charge.core.domain.exModel.StationClientGunEx;
import com.newlife.charge.core.domain.exModel.StationInfoEx;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.GunStatusEnum;
import com.newlife.charge.core.enums.GunTypeEnum;
import com.newlife.charge.core.enums.StationClientGunCodeEnum;
import com.newlife.charge.core.enums.YesNoEnum;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.StationClientGunMapper;
import com.newlife.charge.service.*;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class StationClientGunServiceImpl implements StationClientGunService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StationInfoServiceImpl.class);


    //充电桩socket服务地址
    @Value("${charge.pile.server.path}")
    private String chargePileServerPath;

    //充电桩socket服务-启动/停止充电
    @Value("${charge.pile.server.startStopChargeUri}")
    private String startStopChargeUri;

    @Autowired
    private StationClientGunMapper mapper;


    @Autowired
    private StationInfoService stationInfoService;

    @Autowired
    private StationClientService stationClientService;

    @Autowired
    private StationParkingLotService stationParkingLotService;

    @Autowired
    private Mapper dozer;

    @Override
    public int updateStatusByStationClientId(Integer stationClientId, Integer status) {
        int i=0;
        List<StationClientGun> gunList = this.getInfoByStationClientId(stationClientId);
        if(gunList!=null&&gunList.size()>0){
            Iterator<StationClientGun> it = gunList.iterator();
            while (it.hasNext()){
                StationClientGun gun = it.next();
                i=i+this.updateStatus(gun.getId(),status,null);

            }
        }
        return i;
    }

    @Override
    public int updateStatusByClientCodeList(List<String> codeList,Integer status) {
        if(codeList!=null&&codeList.size()>0){
            List<StationClientEx> clientExList = this.stationClientService.getByCodeList(codeList);
            if(clientExList!=null&&clientExList.size()>0){
                List<Integer> stationClientIdList = new ArrayList<>();
                clientExList.stream().forEach(item->stationClientIdList.add(item.getId()));
                return this.mapper.updateStatus(stationClientIdList,status);
            }
        }
        return 0;
    }


    @Override
    public int updateStatus(Integer id, Integer status,PileRealTimeDataIn pileRealTimeDataIn) {

        StationClientGun gun = new StationClientGun();
        gun.setId(id);
        gun.setStatus(status);

        //TODO:更新枪状态时，需要更新或清空枪的实时数据
        if (GunStatusEnum.OFFLINE.getValue() == status
                || GunStatusEnum.FREE.getValue() == status
                || GunStatusEnum.LINKING.getValue() == status
                || GunStatusEnum.BOOKED.getValue() == status
                || GunStatusEnum.LINE.getValue() == status) {
            LOGGER.info("枪状态为：离线、空闲、连接、排队、预约时，需要清空枪的实时数据");
            gun.setTemperature("");//TODO：充电温度
            gun.setPower("");//TODO:充电功率
            gun.setPercentage("");
            gun.setElectric("");
            gun.setVoltage("");
            gun.setChargeTime("");
            gun.setChargeEnergy("");
            gun.setChargeAmount("");

        } else if (GunStatusEnum.CHARGING.getValue() == status) {
            LOGGER.info("枪状态为：充电中时，需要更新枪的实时数据");
            if (pileRealTimeDataIn != null) {
                gun.setTemperature("");//TODO：充电温度
                gun.setPower("");//TODO:充电功率
                gun.setPercentage(pileRealTimeDataIn.getCurrentSoc());
                gun.setElectric(pileRealTimeDataIn.getChargeVoltage());
                gun.setVoltage(pileRealTimeDataIn.getChargeVoltage());
                gun.setChargeTime(pileRealTimeDataIn.getChargeTime());
                gun.setChargeEnergy(pileRealTimeDataIn.getChargeEnergy());
                gun.setChargeAmount(pileRealTimeDataIn.getChargeAmount());
            }

        } else {
            LOGGER.info("未知状态，do nothing");
            gun.setTemperature("");//TODO：充电温度
            gun.setPower("");//TODO:充电功率
            gun.setPercentage("");
            gun.setElectric("");
            gun.setVoltage("");
            gun.setChargeTime("");
            gun.setChargeEnergy("");
            gun.setChargeAmount("");
        }
        int update = this.mapper.update(gun);

        //TODO：当枪状态为连接中时，判断为插入枪的状态，在此进行webSocket操作
        if (GunStatusEnum.LINKING.getValue() == status) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    WebSocketService webSocketService = SpringContextHolder.getBean(WebSocketService.class);
                    webSocketService.plugInGun(id,status,pileRealTimeDataIn);
                }
            }).start();

        }else if(GunStatusEnum.CHARGING.getValue() == status){
            // 当充电中时，返回实时数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    WebSocketService webSocketService = SpringContextHolder.getBean(WebSocketService.class);
                    webSocketService.sendRealTimeMessage(id,status,pileRealTimeDataIn);
                }
            }).start();
        }
            return update;

    }

    @Override
    public List<SelectListOut> selectList(Integer stationClientId, String stationClientGunCode) {
        List<SelectListOut>  selectListOutList = new ArrayList<>();

        Map<Integer, String> gunCodeMap = new HashMap<>();

        if(stationClientId!=null){
            StationClientInfoOut stationClientInfoOut = this.stationClientService.getInfoById(stationClientId);
            //查询某充电桩已绑定的充电枪编号
            List<StationClientGun> gunList = this.getInfoByStationClientId(stationClientId);
            Integer gunType = stationClientInfoOut.getGunType();
            if(GunTypeEnum.ONE.getValue()==gunType){
                //单枪
                gunCodeMap.put(StationClientGunCodeEnum.ONE.getValue(),StationClientGunCodeEnum.ONE.getDescription());

            }else  if(GunTypeEnum.TWO.getValue()==gunType){
                //双枪
                gunCodeMap.put(StationClientGunCodeEnum.ONE.getValue(),StationClientGunCodeEnum.ONE.getDescription());
                gunCodeMap.put(StationClientGunCodeEnum.TWO.getValue(),StationClientGunCodeEnum.TWO.getDescription());
            }else{
                //其他
            }


            //移除已经使用的枪编号
            if(gunList!=null&&gunList.size()>0){
                gunList.stream().forEach(item->{
                    gunCodeMap.remove(Integer.valueOf(item.getCode()));
                });
            }
        }

        //将特殊的枪编号加入结果集
        if(StringUtils.isNotBlank(stationClientGunCode)){
            gunCodeMap.put(Integer.valueOf(stationClientGunCode),stationClientGunCode);
        }

        Set<Map.Entry<Integer, String>> entrySet = gunCodeMap.entrySet();
        if(entrySet.size()>0){
           entrySet.stream().forEach(item->{
               SelectListOut out =new SelectListOut(item.getKey(),item.getValue());
               selectListOutList.add(out);
           });
        }
        return selectListOutList;
    }

    @Override
    public List<StationClientGun> getByStationParkingLotId(Integer stationParkingLotId) {
        return this.mapper.getByStationParkingLotId(stationParkingLotId);
    }

    @Override
    public List<StationClientGun> getInfoByStationClientId(Integer stationClientId) {
        return this.mapper.getInfoByStationClientId(stationClientId);
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
    public StationClientGunInfoOut getInfoById(Integer id) {
        StationClientGunInfoOut info = null;
        StationClientGunEx stationClientGunEx = this.mapper.getInfoByParams(id, null, null, null,null, null);

        if (stationClientGunEx != null) {
            info = this.dozer.map(stationClientGunEx, StationClientGunInfoOut.class);
            info.setStatusName(GunStatusEnum.getDescriptionByValue(info.getStatus()));
        }
        return info;
    }

    @Override
    public List<StationClientGunInfoOut> getListByStationId(Integer stationId) {
        Page<StationClientGunInfoOut> page = this.mapper.page(null, null, null, null, stationId);
        return page.getResult();
    }


    @Override
    public List<StationClientGunInfoSimpleOut> getSimpleInfoListByStationId(Integer stationId) {
        //未处理状态名称和当前电费，外部业务逻辑处理
        return this.mapper.simpleInfoList(stationId);
    }

    @Override
    public StationClientGunEx getByCode(String code,Integer stationClientId) {
        return this.mapper.getInfoByParams(null, code, null, null,stationClientId, null);
    }

    @Override
    public GunStatusStatisticsOut getGunStatusStatistics(Integer stationId) {
        return this.mapper.getGunStatusStatistics(stationId);
    }

    @Override
    public PageInfo<StationClientGunInfoOut> page(StationClientGunPageIn pageIn) {
        PageHelper.startPage(pageIn.getPageNo(), pageIn.getPageSize());
        Page<StationClientGunInfoOut> page = this.mapper.page(pageIn.getCode(), pageIn.getStationName(),
                pageIn.getStationParkingLotCode(), pageIn.getStatus(), pageIn.getStationId());

        List<StationClientGunInfoOut> list = page.getResult();
        if(list!=null&&list.size()>0){
            Iterator<StationClientGunInfoOut> it = list.iterator();
            StationClientGunInfoOut info;
            while (it.hasNext()){
                info = it.next();
                info.setStatusName(GunStatusEnum.getDescriptionByValue(info.getStatus()));
            }
        }

        return new PageInfo<>(pageIn.getPageNo(), pageIn.getPageSize(), page.getTotal(), list);
    }

    @Override
    public PageInfo<GeneralIndexStationClientGunInfoOut> generalIndexGunStatusPageList(GeneralIndexStationClientGunPageIn pageIn) {

        StationClientGunPageIn stationClientGunPageIn = new StationClientGunPageIn();
        stationClientGunPageIn.setStationId(pageIn.getStationId());
        stationClientGunPageIn.setStatus(pageIn.getStatus());
        stationClientGunPageIn.setPageNo(pageIn.getPageNo());
        stationClientGunPageIn.setPageSize(pageIn.getPageSize());
        PageInfo<StationClientGunInfoOut> page = this.page(stationClientGunPageIn);
        List<GeneralIndexStationClientGunInfoOut> outList = BeanMapper.mapList(page.getData(), GeneralIndexStationClientGunInfoOut.class);
        return new PageInfo<>(pageIn.getPageNo(), pageIn.getPageSize(), page.getTotalCount(), outList);
    }

    @Override
    public void save(StationClientGunSaveIn saveIn) {
        StationClientGun saveObj = null;

        //桩站存在校验
        StationInfoEx stationInfoEx = stationInfoService.getById(saveIn.getStationId());
        if(stationInfoEx==null){
            //桩站不存在
            throw new BizException(ERROR.STATION_INFO_NOT_EXIST);
        }
        if(!(YesNoEnum.NO.getValue().intValue()==stationInfoEx.getDelFlag())){
            //桩站已删除
            throw new BizException(ERROR.STATION_INFO_DELETED);
        }

        //充电桩存在性校验
        StationClientInfoOut stationClientInfoOut = stationClientService.getInfoById(saveIn.getStationClientId());
        if(stationClientInfoOut==null){
            //充电桩不存在
            throw new BizException(ERROR.STATION_CLIENT_INFO_NOT_EXIST);
        }

        //车位存在性校验
        GeneralStationParkingLotInfoOut stationParkingLot = stationParkingLotService.getInfoById(saveIn.getStationParkingLotId());
        if(stationParkingLot==null){
            //车位不存在
            throw new BizException(ERROR.STATION_PARKING_LOT_NOT_EXIST);
        }

        List<StationClientGun> stationClientGunList;
        //车位是否已经绑定充电枪校验
        stationClientGunList = this.getByStationParkingLotId(saveIn.getStationParkingLotId());
        if(stationClientGunList!=null&&stationClientGunList.size()>0){
            //该车位已被其他的充电枪绑定
            throw new BizException(ERROR.STATION_PARKING_LOT_USED);
        }

        //充电桩绑定充电枪数量是否已经达到限制
        stationClientGunList= this.getInfoByStationClientId(saveIn.getStationClientId());
        if(stationClientGunList!=null){
            int hasSize = stationClientGunList.size();
            int maxSize = stationClientInfoOut.getGunType();
            if(hasSize>=maxSize){
                throw new BizException(ERROR.STATION_CLIENT_HAS_MAX_GUN);
            }
        }

        //某个充电桩下充电枪编号唯一性校验
        saveObj = this.getByCode(saveIn.getCode(),saveIn.getStationClientId());
        if (saveObj != null) {
            throw new BizException(ERROR.EXISTS_STATION_CODE);
        }

        //保存信息
        saveObj = this.dozer.map(saveIn, StationClientGun.class);
        saveObj.setGunNo(saveIn.getCode());
        saveObj.setCreateTime(new Date());
        saveObj.setStatus(GunStatusEnum.OFFLINE.getValue());//默认离线中
        saveObj.setVoltage("");
        saveObj.setElectric("");
        saveObj.setPercentage("");
        saveObj.setPower("");
        saveObj.setTemperature("");
        saveObj.setChargeTime("");
        saveObj.setChargeAmount("");
        saveObj.setChargeEnergy("");

        this.mapper.insert(saveObj);
    }


    @Override
    public void update(StationClientGunUpdateIn updateIn) {
        StationClientGun updateObj = null;
        StationClientGun updateObj_db = null;

        //桩站存在校验
        StationInfoEx stationInfoEx = stationInfoService.getById(updateIn.getStationId());
        if(stationInfoEx==null){
            //桩站不存在
            throw new BizException(ERROR.STATION_INFO_NOT_EXIST);
        }
        if(!(YesNoEnum.NO.getValue().intValue()==stationInfoEx.getDelFlag())){
            //桩站已删除
            throw new BizException(ERROR.STATION_INFO_DELETED);
        }

        //充电桩存在性校验
        StationClientInfoOut stationClientInfoOut = stationClientService.getInfoById(updateIn.getStationClientId());
        if(stationClientInfoOut==null){
            //充电桩不存在
            throw new BizException(ERROR.STATION_CLIENT_INFO_NOT_EXIST);
        }

        //车位存在性校验
        GeneralStationParkingLotInfoOut stationParkingLot = stationParkingLotService.getInfoById(updateIn.getStationParkingLotId());
        if(stationParkingLot==null){
            //车位不存在
            throw new BizException(ERROR.STATION_PARKING_LOT_NOT_EXIST);
        }

        List<StationClientGun> stationClientGunList;
        //车位是否已经绑定充电枪校验-应该只有一把枪
        stationClientGunList = this.getByStationParkingLotId(updateIn.getStationParkingLotId());

        if(stationClientGunList!=null&&stationClientGunList.size()>0){
            Iterator<StationClientGun> it = stationClientGunList.iterator();
            boolean used = false;
            while (it.hasNext()){
                StationClientGun gun = it.next();
                if(!gun.getId().equals(updateIn.getId())){
                    used = true;
                    break;
                }
            }
            if(used){
                //该车位已被其他的充电枪绑定
                throw new BizException(ERROR.STATION_PARKING_LOT_USED);
            }
        }

        //充电桩绑定充电枪数量是否已经达到限制
        stationClientGunList= this.getInfoByStationClientId(updateIn.getStationClientId());
        if(stationClientGunList!=null && stationClientGunList.size()>0){
            int hasSize = 0;
            int maxSize = stationClientInfoOut.getGunType();

            Iterator<StationClientGun> it = stationClientGunList.iterator();
            while (it.hasNext()){
                StationClientGun gun = it.next();
                if(!gun.getId().equals(updateIn.getId())){
                    hasSize++;
                }
            }
            if(hasSize>=maxSize){
                throw new BizException(ERROR.STATION_CLIENT_HAS_MAX_GUN);
            }
        }


        //1.某个充电桩下充电枪编号唯一性校验
        updateObj_db = this.getByCode(updateIn.getCode(),updateIn.getStationClientId());
        if (updateObj_db != null && updateObj_db.getId().intValue() != updateIn.getId().intValue()) {
            throw new BizException(ERROR.EXISTS_STATION_CODE);
        }

        //充电枪数据存在性校验
        updateObj_db = this.mapper.get(updateIn.getId());

        if (updateObj_db == null) {
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        updateObj = this.dozer.map(updateIn, StationClientGun.class);

        updateObj.setGunNo(updateIn.getCode());
        updateObj.setCreateTime(updateObj_db.getCreateTime());
        updateObj.setStatus(updateObj_db.getStatus());

        updateObj.setVoltage(updateObj_db.getVoltage());
        updateObj.setElectric(updateObj_db.getElectric());
        updateObj.setPercentage(updateObj_db.getPercentage());
        updateObj.setPower(updateObj_db.getPower());
        updateObj.setTemperature(updateObj_db.getTemperature());
        updateObj.setChargeTime(updateObj_db.getChargeTime());
        updateObj.setChargeAmount(updateObj_db.getChargeAmount());
        updateObj.setChargeEnergy(updateObj_db.getChargeEnergy());

        this.mapper.update(updateObj);
    }

    @Override
    public void delete(Integer id) {
        this.deletes(new int[]{id});
    }

    @Override
    public void deletes(int[] ids) {
        this.mapper.deleteByIds(ids);
    }

    @Override
    public ClientGunStatusCountOut stationClientGunStatus(StationClientGunPageIn stationClientGunPageIn) {

        ClientGunStatusCountOut clientGunStatusCountOut = new ClientGunStatusCountOut();

        //查询桩站下所有的充电枪
        List<StationClientGunInfoSimpleOut> stationClientGuns = this.mapper.simpleInfoList(UserContext.getStationId());
        if(Collections3.isNotEmpty(stationClientGuns)) {
            //筛选各种状态充电枪的 数量 (1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中,7:故障)

            //充电中
            List<StationClientGunInfoSimpleOut> chargerCount = stationClientGuns.stream().filter(item -> GunStatusEnum.CHARGING.getValue() == item.getStatus()).collect(Collectors.toList());
            clientGunStatusCountOut.setChargerCount(chargerCount.size());

            //闲置数量
            List<StationClientGunInfoSimpleOut> unusedCount = stationClientGuns.stream().filter(item -> GunStatusEnum.FREE.getValue() == item.getStatus()).collect(Collectors.toList());
            clientGunStatusCountOut.setUnusedCount(unusedCount.size());

            //连接中数量
            List<StationClientGunInfoSimpleOut> linkCount = stationClientGuns.stream().filter(item -> GunStatusEnum.LINKING.getValue() == item.getStatus()).collect(Collectors.toList());
            clientGunStatusCountOut.setLinkCount(linkCount.size());

            //排队中数量
            List<StationClientGunInfoSimpleOut> lineUpCount = stationClientGuns.stream().filter(item -> GunStatusEnum.LINE.getValue() == item.getStatus()).collect(Collectors.toList());
            clientGunStatusCountOut.setLineUpCount(lineUpCount.size());

            //预约数量
            List<StationClientGunInfoSimpleOut> appointmentCount = stationClientGuns.stream().filter(item -> GunStatusEnum.BOOKED.getValue() == item.getStatus()).collect(Collectors.toList());
            clientGunStatusCountOut.setAppointmentCount(appointmentCount.size());

            //离线数量
            List<StationClientGunInfoSimpleOut> offLindCount = stationClientGuns.stream().filter(item -> GunStatusEnum.OFFLINE.getValue() == item.getStatus()).collect(Collectors.toList());
            clientGunStatusCountOut.setOffLindCount(offLindCount.size());

            //总数量
            clientGunStatusCountOut.setTotalCount(stationClientGuns.size());

            //使用中
            clientGunStatusCountOut.setUsedCount(linkCount.size() + chargerCount.size() + lineUpCount.size() + appointmentCount.size());
        }

        return clientGunStatusCountOut;
    }


    @Override
    public String startCharge(StartStopCommandIn startStopCommandIn) {
        String result  = "";
        String url = this.chargePileServerPath + this.startStopChargeUri;
        //发送请求
        String jsonData = JSONObject.toJSONString(startStopCommandIn);
        LOGGER.info("url="+url);
        LOGGER.info("jsonData="+jsonData);
        try{
            result = Ok3HttpUtil.sendJSONPost(url, jsonData);
        }catch (Exception e){
            LOGGER.error("开始充电命令下发失败",e);
            throw  new BizException("开始充电命令下发失败");
        }

        return result;
    }

    @Override
    public String stopCharge(StartStopCommandIn startStopCommandIn) {
        String result  = "";
        String url = this.chargePileServerPath + this.startStopChargeUri;
        //发送请求
        String jsonData = JSONObject.toJSONString(startStopCommandIn);
        LOGGER.info("url="+url);
        LOGGER.info("jsonData="+jsonData);
        try{
            result = Ok3HttpUtil.sendJSONPost(url, jsonData);
        }catch (Exception e){
            LOGGER.error("停止充电命令下发失败",e);
            throw  new BizException("停止充电命令下发失败");
        }
        return result;
    }
}
