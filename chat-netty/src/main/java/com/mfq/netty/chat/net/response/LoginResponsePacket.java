package com.mfq.netty.chat.net.response;

import com.mfq.netty.chat.constants.CommandConstants;
import com.mfq.netty.chat.net.packet.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    @Override
    public Byte getCommand() {
        return CommandConstants.LOGIN_IN_RESPONSE;
    }
}
