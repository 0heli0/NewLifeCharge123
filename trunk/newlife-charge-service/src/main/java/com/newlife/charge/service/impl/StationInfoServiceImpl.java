package com.newlife.charge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.common.Collections3;
import com.newlife.charge.common.CommonUtil;
import com.newlife.charge.common.DateUtils;
import com.newlife.charge.common.rpc.Ok3HttpUtil;
import com.newlife.charge.core.constant.Constants;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.*;
import com.newlife.charge.core.domain.exModel.StationInfoEx;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.AuditStatusEnum;
import com.newlife.charge.core.enums.StationTypeEnum;
import com.newlife.charge.core.enums.TimeNoEnum;
import com.newlife.charge.core.enums.YesNoEnum;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.StationDetailMapper;
import com.newlife.charge.dao.StationInfoMapper;
import com.newlife.charge.dao.StationTimePriceMapper;
import com.newlife.charge.dao.StationUserRefMapper;
import com.newlife.charge.service.*;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class StationInfoServiceImpl implements StationInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StationInfoServiceImpl.class);


    //充电桩socket服务地址
    @Value("${charge.pile.server.path}")
    private String chargePileServerPath;
    //充电桩socket服务-同步时段电价和服务费地址
    @Value("${charge.pile.server.syncTimeAndServicePriceUri}")
    private String syncTimeAndServicePriceUri;

    @Autowired
    private StationInfoMapper mapper;

    @Autowired
    private StationDetailMapper stationDetailMapper;

    @Autowired
    private StationTimePriceService stationTimePriceService;

    @Autowired
    private StationClientService stationClientService;

    @Autowired
    private StationClientGunService stationClientGunService;

    @Autowired
    private StationParkingLotService stationParkingLotService;
    @Autowired
    private CompanyInfoService companyInfoService;

    @Autowired
    private StationTimePriceMapper stationTimePriceMapper;


    @Autowired
    private StationUserRefMapper stationUserRefMapper;


    @Autowired
    private Mapper dozer;

    @Override
    public StationInfoEx getById(Integer id) {
        return this.mapper.getById(id);
    }

    @Override
    public BigDecimal getCurrentChargePrice(Integer id) {
        return this.mapper.getCurrentChargePrice(id);
    }

    @Override
    public int countByCompanyId(Integer companyId) {
        return this.mapper.countByCompanyId(companyId);
    }

    @Override
    public List<SelectListOut> selectList() {
        return this.mapper.selectList();
    }

    @Override
    public PageInfo<GeneralStationInfoPageOut> page(GeneralStationInfoPageIn pageIn) {
        PageHelper.startPage(pageIn.getPageNo(), pageIn.getPageSize());
        Page<GeneralStationInfoPageOut> page = this.mapper.page(pageIn.getStationName(), pageIn.getCompanyName());
        List<GeneralStationInfoPageOut> result = page.getResult();
        if (result != null && result.size() > 0) {
            Iterator<GeneralStationInfoPageOut> it = result.iterator();
            GeneralStationInfoPageOut out;
            Integer stationId;
            Integer clientCount;
            Integer gunCount;
            Integer parkingLotCount;
            //TODO:需要优化，可以尝试分组统计，10*3->3次查询
            while (it.hasNext()) {
                out = it.next();
                stationId = out.getId();
                clientCount = stationClientService.countByStationId(stationId);
                gunCount = stationClientGunService.countByStationId(stationId);
                parkingLotCount = stationParkingLotService.countByStationId(stationId);
                out.setStationClientCount(clientCount);
                out.setStationClientGunCount(gunCount);
                out.setStationParkingLotCount(parkingLotCount);
            }
        }
        return new PageInfo<>(pageIn.getPageNo(), pageIn.getPageSize(), page.getTotal(), result);
    }

    @Override
    public GeneralStationUpdateInfoOut getUpdateInfoById(Integer id) {
        GeneralStationUpdateInfoOut out = null;
        StationInfoEx infoEx = this.getById(id);
        if (infoEx != null) {
            out = this.dozer.map(infoEx, GeneralStationUpdateInfoOut.class);
        }
        return out;
    }

    @Override
    public GeneralStationInfoOut getInfoById(Integer id) {
        StationInfoEx stationInfoEx = this.getById(id);

        if (stationInfoEx == null) {
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        GeneralStationInfoOut out = new GeneralStationInfoOut();

        out.setId(stationInfoEx.getId());
        out.setName(stationInfoEx.getName());
        out.setCompanyName(stationInfoEx.getCompanyName());
        out.setCoverImg(stationInfoEx.getCoverImg());
        out.setChargePrice(stationInfoEx.getChargePrice());
        out.setServicePrice(stationInfoEx.getServicePrice());

        //时段电价
        List<GeneralStationTimePriceOut> timePriceOutList = this.stationTimePriceService.getByStationId(id);
//        时段序号，【时段数组】 电费、折扣率(1+涨幅)
        if (timePriceOutList != null) {
            //TODO:时段电价结果
            List<GeneralStationTimePriceGroupOut> priceResultList = new ArrayList<>();

            Iterator<GeneralStationTimePriceOut> it = timePriceOutList.iterator();
            GeneralStationTimePriceOut timePriceOut;
            //时段序号分组
            Map<Integer, List<GeneralStationTimePriceOut>> map = new HashMap<>();

            while (it.hasNext()) {
                timePriceOut = it.next();
                Integer timeNo = timePriceOut.getTimeNo();//时段序号
                List<GeneralStationTimePriceOut> timePriceOuts = map.get(timeNo);
                if (timePriceOuts == null) {
                    timePriceOuts = new ArrayList<>();
                }
                timePriceOuts.add(timePriceOut);
                map.put(timeNo, timePriceOuts);
            }

            //处理时段电价
            Set<Integer> keySet = map.keySet();
            if (keySet.size() > 0) {
                Iterator<Integer> keyIt = keySet.iterator();
                while (keyIt.hasNext()) {
                    List<GeneralStationTimePriceOut> list = map.get(keyIt.next());
                    if (list.size() > 0) {
                        GeneralStationTimePriceOut info = list.get(0);
                        GeneralStationTimePriceGroupOut groupOut = new GeneralStationTimePriceGroupOut();
                        groupOut.setTimeNoDesc(TimeNoEnum.getDescriptionByValue(info.getTimeNo()));
                        groupOut.setRate(String.valueOf(100 + info.getIncrease()) + "%");
                        BigDecimal price = out.getChargePrice().multiply(new BigDecimal((100 + info.getIncrease()) * 1.0 / 100));
                        price = price.setScale(4, BigDecimal.ROUND_HALF_UP);
                        groupOut.setPrice(price);//四舍五入，2.33335变2.3334，保留4位小数


                        List<String> timeStringList = new ArrayList<>();
                        for (GeneralStationTimePriceOut priceOut : list) {
                            Time timeBegin = priceOut.getTimeBegin();
                            Time timeEnd = priceOut.getTimeEnd();
                            timeStringList.add(timeBegin.toString() + "~" + timeEnd.toString());
                        }
                        groupOut.setTime(StringUtils.join(timeStringList, ","));
                        priceResultList.add(groupOut);
                    }
                }

            }
            out.setStationTimePriceGroupOutList(priceResultList);
        }

        return out;
    }


    @Override
    public void save(StationInfoSaveIn saveIn) {

        //公司存在性校验
        Integer companyId = saveIn.getCompanyId();
        CompanyInfo companyInfo = this.companyInfoService.getById(companyId);
        if (companyInfo == null) {
            throw new BizException(ERROR.COMPANY_INFO_NOT_EXIST);
        }


        //公司审核状态通过校验
        Integer auditStatus = companyInfo.getAuditStatus();
        if (auditStatus != AuditStatusEnum.PASS.getValue()) {
            throw new BizException(ERROR.COMPANY_AUDIT_STATUS_NOT_PASS);
        }


        Date now = new Date();
        //1.保存桩站信息
        StationInfo stationInfo = new StationInfo();
        stationInfo.setCompanyId(saveIn.getCompanyId());
        stationInfo.setUseBalance(BigDecimal.ZERO);
        stationInfo.setNoCheckBalance(BigDecimal.ZERO);
        stationInfo.setLockedBalance(BigDecimal.ZERO);
        stationInfo.setChargePrice(BigDecimal.ZERO);
        stationInfo.setServicePrice(BigDecimal.ZERO);
        stationInfo.setStatus(YesNoEnum.YES.getValue());
        stationInfo.setDelFlag(YesNoEnum.NO.getValue());
        stationInfo.setRemark("");
        stationInfo.setCreateTime(now);
        this.mapper.insert(stationInfo);

        //2.保存桩站详情
        StationDetail stationDetail = this.dozer.map(saveIn, StationDetail.class);
        stationDetail.setStationId(stationInfo.getId());
        stationDetail.setProvince(Constants.INTEGER_DEFAULT);
        stationDetail.setCity(Constants.INTEGER_DEFAULT);
        stationDetail.setFreeParking(Constants.INTEGER_DEFAULT);
        stationDetail.setParking(Constants.INTEGER_DEFAULT);
        stationDetail.setBusinessHours("");
        stationDetail.setStar(Constants.INTEGER_DEFAULT);
        stationDetail.setType(StationTypeEnum.SPECIAL.getValue());//一期默认桩站类型为专用
        stationDetail.setRemark("");
        stationDetail.setCreateTime(now);
        this.stationDetailMapper.insert(stationDetail);

        //3.添加桩站-桩站主账号关联表
        StationUserRef stationUserRef = new StationUserRef();
        stationUserRef.setCreateTime(now);
        stationUserRef.setStatus(YesNoEnum.YES.getValue());
        stationUserRef.setUserId(companyInfo.getUserId());//桩站web端注册账号即为新建站点主账号
        stationUserRef.setStationId(stationInfo.getId());
        stationUserRefMapper.insert(stationUserRef);

    }

    @Override
    public int updateStationInfo(StationInfo stationInfo) {
        return this.mapper.update(stationInfo);
    }

    @Override
    public void update(StationInfoUpdateIn updateIn) {
        StationInfo stationInfo = this.mapper.get(updateIn.getId());
        if (stationInfo == null) {
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }


        //公司存在性校验
        Integer companyId = updateIn.getCompanyId();
        CompanyInfo companyInfo = this.companyInfoService.getById(companyId);
        if (companyInfo == null) {
            throw new BizException(ERROR.COMPANY_INFO_NOT_EXIST);
        }

        //公司审核状态通过校验
        Integer auditStatus = companyInfo.getAuditStatus();
        if (auditStatus != AuditStatusEnum.PASS.getValue()) {
            throw new BizException(ERROR.COMPANY_AUDIT_STATUS_NOT_PASS);
        }

        //1.更新桩站信息
        stationInfo.setCompanyId(updateIn.getCompanyId());
        this.mapper.update(stationInfo);

        //2.保存桩站详情
        StationDetail stationDetail_db = this.stationDetailMapper.getByStationId(updateIn.getId());
        if (stationDetail_db == null) {
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        StationDetail stationDetail = this.dozer.map(updateIn, StationDetail.class);

        stationDetail.setStationId(stationInfo.getId());
        stationDetail.setProvince(stationDetail_db.getProvince());
        stationDetail.setCity(stationDetail_db.getCity());
        stationDetail.setFreeParking(stationDetail_db.getFreeParking());
        stationDetail.setParking(stationDetail_db.getParking());
        stationDetail.setBusinessHours(stationDetail_db.getBusinessHours());
        stationDetail.setStar(stationDetail_db.getStar());
        stationDetail.setType(stationDetail_db.getType());
        stationDetail.setRemark(stationDetail_db.getRemark());
        stationDetail.setCreateTime(stationDetail_db.getCreateTime());
        this.stationDetailMapper.update(stationDetail);
    }

    @Override
    public void delete(Integer id) {
        this.deletes(new int[]{id});
    }

    @Override
    public void deletes(int[] ids) {
        //逻辑删除
        this.mapper.updateDelFlagByIds(ids, YesNoEnum.YES.getValue());
    }

    @Override
    public void setChargeAndServicePrice(StationInfoSetChargeIn setChargeIn) {

        StationInfo stationInfo = this.mapper.get(UserContext.getStationId());
        if (stationInfo != null) {
            stationInfo.setChargePrice(setChargeIn.getChargePrice());
            stationInfo.setServicePrice(setChargeIn.getServicePrice());

            mapper.update(stationInfo);
        } else {
            throw new BizException(ERROR.DATA_NOT_FOUND.message());
        }
    }

    @Override
    public void setStationTimePrice(StationTimePriceIn stationTimePriceIn) {

        if (Collections3.isNotEmpty(stationTimePriceIn.getStationTimeInList())) {
            //新增 时段电价
            if (stationTimePriceIn.getTimeNo() == null) {
                //查询已添加的 时段序号
                Integer timeNo = 1;
                StationTimePrice stationTimePrice = stationTimePriceMapper.getOneByStationId(UserContext.getStationId());
                if (stationTimePrice != null) {
                    timeNo = stationTimePrice.getTimeNo() + 1;
                }
                //保存
                saveStationTimePrice(stationTimePriceIn, timeNo);

            } else {
                //修改 时段电价
                //先删除之前设置的时段电价
                stationTimePriceMapper.deleteByTimeNoAndStationId(UserContext.getStationId(), stationTimePriceIn.getTimeNo());

                //重新设置时段电价
                saveStationTimePrice(stationTimePriceIn, stationTimePriceIn.getTimeNo());
            }
        } else {
            throw new BizException(ERROR.PARAMATER_ERROR.message());
        }
    }

    private void saveStationTimePrice(StationTimePriceIn stationTimePriceIn, Integer timeNo) {

        //限制 时段电价 只能三种调整比例
        List<StationTimePrice> stationTimePrices = stationTimePriceMapper.getIncrease(UserContext.getStationId());
        if (Collections3.isNotEmpty(stationTimePrices)) {
            List<Integer> increase = stationTimePrices.stream().map(StationTimePrice::getIncrease).collect(Collectors.toList());
            if (increase.size() < 3) {

            } else if (increase.size() == 3) {
                if (!increase.contains(stationTimePriceIn.getIncrease())) {
                    throw new BizException("时段电价已存在三种调整比例");
                }
            } else {
                throw new BizException("数据错误");
            }
        }

        //限制 时段最多只能添加10个
        List<StationTimePrice> stationTimePriceList = stationTimePriceMapper.getByStationId(UserContext.getStationId());
        if(stationTimePriceList.size() < 10) {

        } else {
            throw new BizException("时段最多只能添加10个");
        }

        //循环保存多个 时段
        for (StationTimeIn stationTime : stationTimePriceIn.getStationTimeInList()) {
            //检测 时段电价是否有重复
            checkStationtPriceTime(stationTime.getTimeBegin(), stationTime.getTimeEnd());

            StationTimePrice stationTP = new StationTimePrice();
            stationTP.setStationId(UserContext.getStationId());
            stationTP.setTimeBegin(stationTime.getTimeBegin());
            stationTP.setTimeEnd(stationTime.getTimeEnd());
            stationTP.setIncrease(stationTimePriceIn.getIncrease());
            stationTP.setCreateTime(new Date());
            stationTP.setTimeNo(timeNo);
            stationTimePriceMapper.insert(stationTP);
        }

    }

    //检测 时段电价是否有重复
    public void checkStationtPriceTime(Time timeBegin, Time timeEnd) {

        StationTimePriceQuery query = new StationTimePriceQuery();
        query.setStationId(UserContext.getStationId());
        query.setTimeBegin(timeBegin);
        query.setTimeEnd(timeEnd);
        List<StationTimePrice> stationTimePrices = stationTimePriceMapper.selectStationTime(query);
        if (Collections3.isNotEmpty(stationTimePrices)) {
            String message = "时段" + DateUtils.formatDate(timeBegin, "HH:mm:ss") + "--" + DateUtils.formatDate(timeEnd, "HH:mm:ss") + "已存在或出现重复时段，请重新选择时间";
//            if(stationTimePriceIn.getTimeNo() != null) {
//                List<StationTimePrice> stationTimePriceList = stationTimePrices.stream().filter(p -> p.getId().equals(id)).collect(Collectors.toList());
//                if(Collections3.isEmpty(stationTimePriceList)) {
//                    throw new BizException(message);
//                }
//            } else {

            throw new BizException(message);
//            }
        }
    }

    @Override
    public StationInfoOut chargeAndServicePriceInfo(Integer station) {
        StationInfoOut stationInfoOut = new StationInfoOut();
        StationInfo stationInfo = mapper.getById(station);

        if (stationInfo != null) {
            stationInfoOut = dozer.map(stationInfo, StationInfoOut.class);
        } else {
            throw new BizException(ERROR.DATA_NOT_FOUND.message());
        }

        return stationInfoOut;
    }

    @Override
    public void deleteStationTimePrice(StationTimePriceQuery query) {

        stationTimePriceMapper.deleteByTimeNoAndStationId(UserContext.getStationId(), query.getTimeNo());
    }

    @Override
    public List<StationTimePriceListOut> stationTimePriceList() {

        List<StationTimePriceListOut> result = new ArrayList<>();

        List<StationTimePrice> stationTimePrices = stationTimePriceMapper.getByStationId(UserContext.getStationId());
        if (Collections3.isNotEmpty(stationTimePrices)) {

            //去重  过滤出时段编号
            List<StationTimePrice> stationTimePriceList = stationTimePrices.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(StationTimePrice::getTimeNo))), ArrayList::new));
            List<Integer> timeNos = stationTimePriceList.stream().map(StationTimePrice::getTimeNo).collect(Collectors.toList());
            //查询桩站基础电费
            StationInfo stationInfo = mapper.get(UserContext.getStationId());
            for (Integer timeNo : timeNos) {
                List<StationTimePrice> stationTimePricees = stationTimePrices.stream().filter(item -> item.getTimeNo().equals(timeNo)).collect(Collectors.toList());

                List<StationTimeIn> stationTimeInList = BeanMapper.mapList(stationTimePricees, StationTimeIn.class);

                //封装返回参数
                StationTimePriceListOut stationTimePriceListOut = new StationTimePriceListOut();
                stationTimePriceListOut.setIncrease(stationTimePricees.get(0).getIncrease());
                stationTimePriceListOut.setTimeNo(stationTimePricees.get(0).getTimeNo());
                //计算调整后的电费
                BigDecimal price = CommonUtil.calculatePrice(stationInfo.getChargePrice(), null, stationTimePricees.get(0).getIncrease());
                stationTimePriceListOut.setPrice(price);

                stationTimePriceListOut.setStationTimeInList(stationTimeInList);

                result.add(stationTimePriceListOut);
            }
        }

        return result;
    }

    @Override
    public List<StationTimePrice> stationTimePriceInfo(StationTimePriceQuery stationTimePriceQuery) {

        return stationTimePriceMapper.getByQuery(stationTimePriceQuery);
    }

    @Override
    public List<StationInfoOut> selectListByCompanyId(Integer companyId, Integer userId) {

        return mapper.selectListByCompanyId(companyId, userId);
    }

    @Override
    public String syncTimeAndServicePrice(Integer id) {
        String result = null;
        StationInfoEx stationInfoEx = this.getById(id);
        List<StationClient> stationClientList = this.stationClientService.getByStationId(id);//充电桩
        List<StationTimePrice> timePriceList = this.stationTimePriceMapper.getByStationId(id);//时段电价
        if (stationInfoEx == null) {
            throw new BizException(ERROR.STATION_INFO_NOT_EXIST);
        }
        if(timePriceList==null || timePriceList.size() >10){
            throw new BizException("阶段超过10个，下发计费规则失败");
        }
        if (stationClientList == null || stationClientList.size() <= 0) {

            throw new BizException("该桩站没有需要下发计费规则的充电桩");
        }

        StationTimePrice stationTimePrice;
        int hasTimeNo = 0;
        if(timePriceList==null || timePriceList.size() !=10){
            //当阶段不足10个的时候，补足10个
            hasTimeNo = timePriceList==null?0:timePriceList.size();
            for (int i = hasTimeNo; i < 10; i++) {
                stationTimePrice = new StationTimePrice();
                stationTimePrice.setTimeNo(hasTimeNo+1);
                stationTimePrice.setIncrease(-100);
                stationTimePrice.setTimeBegin(new Time(0,0,0));
                timePriceList.add(stationTimePrice);
            }
        }

        //基础电价
        BigDecimal chargePrice = stationInfoEx.getChargePrice();
        int len = 10;//十个时段
        String[] timeArr = new String[len];
        String[] timePriceArr = new String[len];
//        timePriceList.stream().forEach(item->{
//            Integer timeNo = item.getTimeNo();
//            Integer index = timeNo-1;
//            Integer increase = item.getIncrease();
//            //充电桩时段电价为元
//            BigDecimal currentPrice = chargePrice.multiply(new BigDecimal(100 + increase)).divide(BigDecimal.valueOf(100.0),2,BigDecimal.ROUND_HALF_UP);
//            //时段电价换算成分，只保留整数
//            String currentPriceStr = currentPrice.multiply(BigDecimal.valueOf(100)).setScale(0,BigDecimal.ROUND_DOWN).toString();
//            timeArr[index]=item.getTimeBegin().toString().replaceAll(":","").substring(0,4);//只取时分，13:14:00->1314
//            timePriceArr[index] =currentPriceStr;//0.2857->28
//        });
        //2019-06-03 修改
        for(int i=0; i<timePriceList.size(); i++) {
            Integer increase = timePriceList.get(i).getIncrease();
            //充电桩时段电价为元
            BigDecimal currentPrice = chargePrice.multiply(new BigDecimal(100 + increase)).divide(BigDecimal.valueOf(100.0),2,BigDecimal.ROUND_HALF_UP);
            //时段电价换算成分，只保留整数
            String currentPriceStr = currentPrice.multiply(BigDecimal.valueOf(100)).setScale(0,BigDecimal.ROUND_DOWN).toString();
            timeArr[i]=timePriceList.get(i).getTimeBegin().toString().replaceAll(":","").substring(0,4);//只取时分，13:14:00->1314
            timePriceArr[i] =currentPriceStr;//0.2857->28
        }

        List<String> stationClientCodeList = new ArrayList();
        stationClientList.stream().forEach(item -> stationClientCodeList.add(item.getCode()));
        String url = this.chargePileServerPath + this.syncTimeAndServicePriceUri;
        RpcTimeServicePriceIn rpcTimeServicePriceIn = new RpcTimeServicePriceIn();
        BigDecimal servicePrice = stationInfoEx.getServicePrice();
        servicePrice=servicePrice==null?BigDecimal.ZERO:servicePrice;
        //服务费换算成分，只保留整数
        String servicePriceStr = servicePrice.multiply(BigDecimal.valueOf(100)).setScale(0,BigDecimal.ROUND_DOWN).toString();

        rpcTimeServicePriceIn.setServicePrice(servicePriceStr);//服务费换算成分
        rpcTimeServicePriceIn.setStationClientCodeList(stationClientCodeList);
        rpcTimeServicePriceIn.setTimeArr(timeArr);
        rpcTimeServicePriceIn.setTimePriceArr(timePriceArr);
        //发送请求
        String jsonData = JSONObject.toJSONString(rpcTimeServicePriceIn);
        LOGGER.info("url="+url);
        LOGGER.info("jsonData="+jsonData);
        try{
            result = Ok3HttpUtil.sendJSONPost(url, jsonData);
        }catch (Exception e){
            LOGGER.error("下发桩站计费规则失败",e);
            throw  new BizException("下发桩站计费规则失败");
        }

        return result;
    }
}
