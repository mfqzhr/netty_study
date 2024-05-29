package com.mfq.nio.netty.protocol;

import static com.mfq.nio.netty.protocol.Command.LOGIN_REQUEST;

public class LoginRequestPacket extends Packet{
    private Integer userId;
    private Integer username;
    private Integer password;
    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
