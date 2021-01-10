package com.newlife.charge.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.core.domain.PreCharge;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.PreChargeOut;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.PreChargeMapper;
import com.newlife.charge.service.PreChargeService;
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
public class PreChargeServiceImpl implements PreChargeService {

    @Autowired
    private PreChargeMapper preChargeMapper;
    @Autowired
    private Mapper dozer;


    @Override
    public int insertOne(PreChargeSaveIn saveIn) {
        int result = 0;
        if(saveIn != null){
            // 查看是否存在同样数据
            PreCharge info =  this.preChargeMapper.getByPrice(saveIn.getOriginalPrice());
            if(info != null){
                throw new BizException(ERROR.PRE_CHARGE_REPEAT);
            }
            PreCharge preCharge = dozer.map(saveIn, PreCharge.class);
            preCharge.setFinalPrice(preCharge.getOriginalPrice());
            preCharge.setCreateTime(new Date());
            result = this.preChargeMapper.insert(preCharge);
        }
        return result;
    }

    @Override
    public int batchSave(PreChargeBatchSaveIn saveIn) {

        List<PreCharge> preCharges = BeanMapper.mapList(saveIn.getParams(),PreCharge.class);

        preCharges.stream().forEach(item ->{
            item.setFinalPrice(item.getOriginalPrice());
            item.setCreateTime(new Date());
        });

        int result = this.preChargeMapper.batchSave(preCharges);

        return result;
    }

    @Override
    public int update(PreChargeUpdateIn updateIn) {
        int result = 0;
        if(updateIn != null){
            // 查看是否存在同样数据
            PreCharge info =  this.preChargeMapper.getByPrice(updateIn.getOriginalPrice());
            if(info != null){
                throw new BizException(ERROR.PRE_CHARGE_REPEAT);
            }
            PreCharge preCharge = dozer.map(updateIn, PreCharge.class);
            preCharge.setFinalPrice(preCharge.getOriginalPrice());
            result = this.preChargeMapper.update(preCharge);
        }
        return result;
    }

    @Override
    public PageInfo<PreChargeOut> page(int pageNo, int pageSize) {

        PageHelper.startPage(pageNo, pageSize);
        Page<PreCharge> page = this.preChargeMapper.page();
        List<PreCharge> list = page.getResult();
        List<PreChargeOut> result = BeanMapper.mapList(list, PreChargeOut.class);
        return new PageInfo<>(page.getPageNum(),page.getPageSize(),page.getTotal(),result);
    }

    @Override
    public List<PreChargeOut> preChargeList() {
        Page<PreCharge> page = this.preChargeMapper.page();
        List<PreCharge> list = page.getResult();
        List<PreChargeOut> result = BeanMapper.mapList(list, PreChargeOut.class);
        return result;
    }

    @Override
    public PreChargeOut info(int id) {
        PreCharge info = this.preChargeMapper.get(id);

        if(info == null){
            return null;
        }
        PreChargeOut out = dozer.map(info, PreChargeOut.class);
        return out;
    }

    @Override
    public int delete(PreChargeDeleteIn deleteIn) {
        PreCharge preCharge = dozer.map(deleteIn,PreCharge.class);
        int result = this.preChargeMapper.delete(preCharge);
        return result;
    }

    @Override
    public void deleteByIds(PreChargeDeleteIdsIn deleteIdsIn) {
        this.preChargeMapper.deleteByIds(deleteIdsIn.getIds());
    }
}
