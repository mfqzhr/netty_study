package com.mfq.netty.chat;

import com.mfq.netty.chat.serializer.JSONSerializer;

public interface Serializer {
    /**
     * JSON序列化
     */
    byte JSON_SERIALIZER = 1;

    com.mfq.netty.chat.serializer.Serializer DEFAULT = new JSONSerializer();
}
