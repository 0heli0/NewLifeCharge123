package com.newlife.charge.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.core.domain.StationClientGun;
import com.newlife.charge.core.domain.StationParkingLot;
import com.newlife.charge.core.domain.exModel.StationInfoEx;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.GeneralStationParkingLotPageIn;
import com.newlife.charge.core.dto.in.StationParkingLotSaveIn;
import com.newlife.charge.core.dto.in.StationParkingLotUpdateIn;
import com.newlife.charge.core.dto.out.GeneralStationParkingLotInfoOut;
import com.newlife.charge.core.dto.out.GeneralStationParkingLotPageOut;
import com.newlife.charge.core.dto.out.SelectListOut;
import com.newlife.charge.core.enums.YesNoEnum;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.StationParkingLotMapper;
import com.newlife.charge.service.StationClientGunService;
import com.newlife.charge.service.StationInfoService;
import com.newlife.charge.service.StationParkingLotService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class StationParkingLotServiceImpl implements StationParkingLotService {

    @Autowired
    private StationParkingLotMapper mapper;

    @Autowired
    private StationInfoService stationInfoService;

    @Autowired
    private StationClientGunService stationClientGunService;


    @Autowired
    private Mapper dozer;

    @Override
    public List<SelectListOut> selectList(Integer stationId, Integer stationParkingLotId) {
        return this.mapper.selectList(stationId,stationParkingLotId);
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
    public GeneralStationParkingLotInfoOut getInfoById(Integer id) {
        GeneralStationParkingLotInfoOut out = null;
        StationParkingLot stationParkingLot = this.mapper.get(id);
        if(stationParkingLot!=null){
            out = this.dozer.map(stationParkingLot,GeneralStationParkingLotInfoOut.class);
            List<StationClientGun> gunList = this.stationClientGunService.getByStationParkingLotId(id);
            if(gunList!=null&&gunList.size()>0){
                out.setStationClientGunId(gunList.get(0).getId());
            }
        }
        return  out;
    }

    @Override
    public List<StationParkingLot> getInfoByStationClientId(Integer stationClientId) {
        return this.mapper.getInfoByStationClientId(stationClientId);

    }

    @Override
    public StationParkingLot getByCodeAndStationId(String code, Integer stationId) {
        return this.mapper.getByCodeAndStationId(code,stationId);
    }

    @Override
    public PageInfo<GeneralStationParkingLotPageOut> page(GeneralStationParkingLotPageIn pageIn) {

        PageHelper.startPage(pageIn.getPageNo(), pageIn.getPageSize());
        Page<GeneralStationParkingLotPageOut> page = this.mapper.page(pageIn.getStationName());
        return new PageInfo<>(pageIn.getPageNo(), pageIn.getPageSize(), page.getTotal(), page.getResult());
    }


    @Override
    public void save(StationParkingLotSaveIn saveIn) {

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

        //车位编号某桩站唯一性校验
        StationParkingLot parkingLot = this.getByCodeAndStationId(saveIn.getCode(),saveIn.getStationId());
        if (parkingLot != null) {
            throw new BizException(ERROR.EXISTS_DATA_ALREADY);
        }

        Date now = new Date();
        //保存车位信息
        parkingLot = this.dozer.map(saveIn, StationParkingLot.class);
        parkingLot.setCreateTime(now);
        this.mapper.insert(parkingLot);
    }


    @Override
    public void update(StationParkingLotUpdateIn updateIn) {
        //更新车位信息
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

        //车位编号桩站内唯一性校验
        StationParkingLot parkingLot = this.getByCodeAndStationId(updateIn.getCode(),updateIn.getStationId());

        if (parkingLot != null && parkingLot.getId().intValue() != updateIn.getId().intValue()) {
            throw new BizException(ERROR.EXISTS_DATA_ALREADY);
        }
        parkingLot = this.mapper.get(updateIn.getId());
        //车位存在校验
        if (parkingLot == null) {
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        //车位未绑定充电枪，所属桩站才能更新
        List<StationClientGun> gunList = this.stationClientGunService.getByStationParkingLotId(updateIn.getId());
        if(gunList==null || gunList.size()<=0){
            parkingLot.setStationId(updateIn.getStationId());
        }
        parkingLot.setCode(updateIn.getCode());

        this.mapper.update(parkingLot);
    }

    @Override
    public void delete(Integer id) {
        this.deletes(new int[]{id});
    }

    @Override
    public void deletes(int[] ids) {
        if(ids.length>0){
            List<StationClientGun> gunList;
            for (int id :ids) {
                gunList = this.stationClientGunService.getByStationParkingLotId(id);
                if(gunList!=null&&gunList.size()>0){
                    //已绑定充电枪的车位不能删除
                    throw  new BizException(ERROR.STATION_PARKING_LOT_HAS_GUN);
                }
            }
            this.mapper.deleteByIds(ids);
        }

    }
}
