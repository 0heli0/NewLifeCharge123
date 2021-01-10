package com.newlife.charge.common.dozer;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampConverter extends DozerConverter<Long, String> {

    private SimpleDateFormat formatter;

    public TimestampConverter(String pattern) {
        super(Long.class, String.class);
        formatter = new SimpleDateFormat(pattern);
    }

    @Override
    public String convertTo(Long source, String destination) {
        if (source == null || source.longValue() == 0) {
            return destination;
        }
        return formatter.format(new Date(source));
    }

    @Override
    public Long convertFrom(String source, Long destination) {
        if (StringUtils.isEmpty(source)) {
            return 0L;
        }

        try {
            return formatter.parse(source).getTime();
        } catch (ParseException e) {
            //TODO: exception process.
            e.printStackTrace();
        }
        return 0L;
    }
}
