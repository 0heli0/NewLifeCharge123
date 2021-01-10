package com.newlife.charge.service.impl;

import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.core.domain.StationTimePrice;
import com.newlife.charge.core.dto.out.GeneralStationTimePriceOut;
import com.newlife.charge.dao.StationTimePriceMapper;
import com.newlife.charge.service.StationTimePriceService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StationTimePriceServiceImpl implements StationTimePriceService {

    @Autowired
    private StationTimePriceMapper mapper;


    @Autowired
    private Mapper dozer;

    @Override
    public List<GeneralStationTimePriceOut> getByStationId(Integer stationId) {
        List<GeneralStationTimePriceOut> outList = null;

        List<StationTimePrice> timePriceList = this.mapper.getByStationId(stationId);
        if (timePriceList != null) {
            outList = BeanMapper.mapList(timePriceList, GeneralStationTimePriceOut.class);
        }
        return outList;
    }
}
