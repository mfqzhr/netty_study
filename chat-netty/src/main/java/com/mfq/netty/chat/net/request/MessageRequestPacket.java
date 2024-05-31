package com.mfq.netty.chat.net.request;

import com.mfq.netty.chat.constants.CommandConstants;
import com.mfq.netty.chat.net.packet.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return CommandConstants.MESSAGE_REQUEST;
    }
}
