/**
 * Author: Fuq
 * Date:   2018/10/16 13:56
 * Descripition:
 */
package com.newlife.charge.config;

import com.newlife.charge.common.dozer.TimestampConverter;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DozerBeanMapperConfigure {
    @Bean
    public DozerBeanMapper mapper() {
        TimestampConverter timestampConverter = new TimestampConverter("yyyy-MM-dd HH:mm:ss");
        Map<String,CustomConverter> customConverterMap = new HashMap<>();
        customConverterMap.put("timestampConverter",timestampConverter);
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList("config/basecode-mapper.xml"));
        mapper.setCustomConvertersWithId(customConverterMap);
        return mapper;
    }
}
