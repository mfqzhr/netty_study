package com.mfq.netty.chat.codec;

import com.mfq.netty.chat.constants.SerializerAlgorithmConstants;
import com.mfq.netty.chat.net.request.MessageRequestPacket;
import com.mfq.netty.chat.net.response.LoginResponsePacket;
import com.mfq.netty.chat.serializer.JSONSerializer;
import com.mfq.netty.chat.serializer.Serializer;
import com.mfq.netty.chat.constants.CommandConstants;
import com.mfq.netty.chat.net.request.LoginRequestPacket;
import com.mfq.netty.chat.net.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class PacketCodeC {
    public static final int MAGIC_NUMBER = 0x12345678;

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    public ByteBuf encode(ByteBufAllocator alloc, Packet packet) {
        //1. 创建ByteBuf对象
        ByteBuf byteBuf = alloc.ioBuffer();
        //2. 序列化java对象
        //Serializer serializer = getSerializer();
        byte[] bytes = com.mfq.netty.chat.Serializer.DEFAULT.serialize(packet);
        //3. 实际编码过程
        // 魔术
        byteBuf.writeInt(MAGIC_NUMBER);
        //版本号
        byteBuf.writeByte(packet.getVersion());
        //序列化算法
        byteBuf.writeByte(SerializerAlgorithmConstants.JSON);
        //指令类型
        byteBuf.writeByte(packet.getCommand());
        //body的长度
        byteBuf.writeInt(bytes.length);
        //body数据
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        //跳过魔术
        byteBuf.skipBytes(4);
        //跳过版本号
        byteBuf.skipBytes(1);
        //序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();
        //指令
        byte command = byteBuf.readByte();
        //数据包长度
        int len = byteBuf.readInt();
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);
        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        if (serializeAlgorithm == SerializerAlgorithmConstants.JSON) {
            return new JSONSerializer();
        }
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        if (command == CommandConstants.LOGIN_IN_REQUEST) {
            return LoginRequestPacket.class;
        }
        if (command == CommandConstants.LOGIN_IN_RESPONSE) {
            return LoginResponsePacket.class;
        }
        if (command == CommandConstants.MESSAGE_REQUEST) {
            return MessageRequestPacket.class;
        }
        return null;
    }
}
