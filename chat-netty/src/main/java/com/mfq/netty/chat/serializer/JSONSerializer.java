package com.mfq.netty.chat.serializer;

import com.alibaba.fastjson.JSON;
import com.mfq.netty.chat.constants.SerializerAlgorithmConstants;

public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithmConstants.JSON;
    }

    @Override
    public byte[] serialize(Object o) {
        return JSON.toJSONBytes(o);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
