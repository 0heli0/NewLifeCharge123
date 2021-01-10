package com.newlife.charge.common.serialize;

import org.nustaq.serialization.FSTConfiguration;

import java.sql.Timestamp;


public class FstSerializer {

    private static FSTConfiguration configuration = FSTConfiguration.createUnsafeBinaryConfiguration();

    static {
        configuration.registerClass(Timestamp.class);
    }


    private FstSerializer() {
    }


    public static Object decode(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return configuration.asObject(bytes);
    }

    public static byte[] encode(Object o) {
        if (null == o) {
            return new byte[0];
        }
        return configuration.asByteArray(o);
    }

}
