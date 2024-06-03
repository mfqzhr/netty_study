package com.mfq.netty.chat.constants;

import io.netty.util.AttributeKey;

public interface Attributes {
    /**
     * 是否登录
     */
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
