package com.mfq.netty.chat.serializer;

public interface Serializer {
    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java转换成二进制数据
     */
    byte[] serialize(Object o);

    /**
     * 二进制数据转换成java数据
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
