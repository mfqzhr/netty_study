package com.mfq.netty.chat.net.request;


import com.mfq.netty.chat.constants.CommandConstants;
import com.mfq.netty.chat.net.packet.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket extends Packet {
    private String userId;
    private String username;
    private String password;
    @Override
    public Byte getCommand() {
        return CommandConstants.LOGIN_IN_REQUEST;
    }
}
