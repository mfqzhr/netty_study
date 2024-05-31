package com.mfq.nio.netty.protocol.serializer;

import com.mfq.nio.netty.protocol.JSONSerializer;

public interface Serializer {
    /**
     * JSON序列化
     */
    byte JSON_SERIALIZER = 1;

    com.mfq.nio.netty.protocol.Serializer DEFAULT = new JSONSerializer();
}
