package com.mfq.nio.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * nio-client-server
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap
                //指定线程模型
                .group(group)
                //指定io为NIO
                .channel(NioSocketChannel.class)
                //IO处理逻辑
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline()
                                .addLast(new StringEncoder())
                                //ChannelInboundHandlerAdapter 会在建立连接后调用
                                .addLast(new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println(new Date() + " : " + "客户端写出数据");
                                        // 1. 获取数据
                                        ByteBuf buffer = ctx.alloc().buffer();
                                        buffer.writeBytes("hello i am a client".getBytes(StandardCharsets.UTF_8));
                                        ctx.channel().writeAndFlush(buffer);
                                    }
                                });
                    }
                });
        Channel channel = bootstrap.connect("127.0.0.1", 38888).channel();
        while (true) {
            channel.writeAndFlush(new Date() + " : hello world!");
            Thread.sleep(2000);
        }
    }
}
