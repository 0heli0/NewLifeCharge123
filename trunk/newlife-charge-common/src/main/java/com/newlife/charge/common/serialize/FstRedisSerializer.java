package com.newlife.charge.common.serialize;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;


public class FstRedisSerializer<T> implements RedisSerializer<T> {

    @Override
    public byte[] serialize(T t) throws SerializationException {
        return FstSerializer.encode(t);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return (T) FstSerializer.decode(bytes);
    }
}
