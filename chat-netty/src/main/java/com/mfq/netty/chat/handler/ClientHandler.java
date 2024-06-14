package com.mfq.netty.chat.handler;

import com.mfq.netty.chat.codec.PacketCodeC;
import com.mfq.netty.chat.net.packet.Packet;
import com.mfq.netty.chat.net.request.LoginRequestPacket;
import com.mfq.netty.chat.net.response.LoginResponsePacket;
import com.mfq.netty.chat.util.LoginUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + " : 客户端开始登录");
        //创建登录对象
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId(UUID.randomUUID().toString());
        requestPacket.setUsername("mfq");
        requestPacket.setPassword("123");
        //编码
        ByteBuf buffer = PacketCodeC.INSTANCE.encode(ctx.alloc(), requestPacket);
        //写数据
        ctx.channel().writeAndFlush(buffer);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()) {
                LoginUtils.markAsLogin(ctx.channel());
                System.out.println(new Date() + " : 客户端登录成功");
            } else {
                System.out.println(new Date() + " : 客户端登录失败");
            }
        }
     }
}
