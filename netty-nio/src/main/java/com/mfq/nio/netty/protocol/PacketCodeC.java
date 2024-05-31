package com.mfq.nio.netty.protocol;

import com.mfq.nio.netty.protocol.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.Map;

public class PacketCodeC {
    public static final int MAGIC_NUMBER = 0x12345678;

    public ByteBuf encode(Packet packet) {
        //1. 串讲ByteBuf对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        //2. 序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        //3. 实际编码过程
        // 魔术
        byteBuf.writeInt(MAGIC_NUMBER);
        //版本号
        byteBuf.writeByte(packet.getVersion());
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
        Class<? extends Packet> requestType = null;
        Serializer serializer = null;
        if (requestType != null && serializer != null) {
            return Serializer.DEFAULT.deserialize(requestType, bytes);
        }
        return null;
    }
}
