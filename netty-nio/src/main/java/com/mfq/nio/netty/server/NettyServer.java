package com.mfq.nio.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


/**
 * nio-server-netty
 */
public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                //指定线程模型
                .group(boss, worker)
                //指定IO类型为NIO
                .channel(NioServerSocketChannel.class)
                //IO处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
 //                               .addLast(new StringDecoder())
//                                .addLast(new SimpleChannelInboundHandler<String>() {
//                                    @Override
//                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
//                                        System.out.println(s);
//                                    }
//                                })
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf byteBuf = (ByteBuf) msg;
                                        System.out.println("maxCapacity : " + byteBuf.maxCapacity());
                                        // byteBuf所占用的
                                        System.out.println("capacity : " + byteBuf.capacity());
                                        System.out.println("readerIndex : " + byteBuf.readerIndex());
                                        System.out.println("writerIndex : " + byteBuf.writerIndex());
                                        System.out.println("hello i am server, you are message is " + byteBuf.toString(StandardCharsets.UTF_8));
                                    }
                                });
                    }
                });
        bind(serverBootstrap, 38888);
    }
    private static  void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener((future) -> {
            if (future.isSuccess()) {
                System.out.println("端口绑定成功");
            } else {
                System.out.println("端口绑定失败");
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
