package com.newlife.charge.common.serialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SerializeUtils {

    private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static Object deserialize(byte[] bytes) {
        Object result = null;

        if (isEmpty(bytes)) {
            return null;
        }

        ByteArrayInputStream byteStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            byteStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteStream);
            result = objectInputStream.readObject();
        } catch (Exception e) {
            logger.error("Failed to deserialize", e);
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    logger.error("IO close exception",e);
                }
            }

            if (byteStream != null) {
                try {
                    byteStream.close();
                } catch (IOException e) {
                    logger.error("IO close exception",e);
                }
            }
        }
        return result;
    }

    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        byte[] result = null;

        if (object == null) {
            return new byte[0];
        }

        if (!(object instanceof Serializable)) {
            throw new IllegalArgumentException(SerializeUtils.class.getSimpleName() + " requires a Serializable payload " +
                    "but received an object of type [" + object.getClass().getName() + "]");
        }

        ByteArrayOutputStream byteStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteStream = new ByteArrayOutputStream(256);
            objectOutputStream = new ObjectOutputStream(byteStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            result = byteStream.toByteArray();
        } catch (Exception ex) {
            logger.error("Failed to serialize", ex);
        } finally {
            if (byteStream != null) {
                try {
                    byteStream.close();
                } catch (IOException e) {
                    logger.error("Failed to serialize", e);
                }
            }

            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    logger.error("Failed to serialize", e);
                }
            }
        }

        return result;
    }
}
