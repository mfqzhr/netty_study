package com.mfq.netty.chat.handler;

import com.mfq.netty.chat.codec.PacketCodeC;
import com.mfq.netty.chat.net.request.LoginRequestPacket;
import com.mfq.netty.chat.net.packet.Packet;
import com.mfq.netty.chat.net.request.MessageRequestPacket;
import com.mfq.netty.chat.net.response.LoginResponsePacket;
import com.mfq.netty.chat.util.LoginUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //解码
        ByteBuf requestByteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);
        //判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket requestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket responsePacket = new LoginResponsePacket();
            responsePacket.setVersion(packet.getVersion());
            //登录校验
            if ((vaild(requestPacket))) {
                System.out.println("登录成功了");
                responsePacket.setSuccess(true);
            } else {
                responsePacket.setSuccess(false);
            }
            //编码
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
            ctx.writeAndFlush(responseByteBuf);
            return;
        }
        //如果是发生消息的请求
        if (packet instanceof MessageRequestPacket) {
           MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + " : 收到客户端消息: " + messageRequestPacket.getMessage());
        }
    }

    private boolean vaild(LoginRequestPacket requestPacket) {
        return true;
    }
}
