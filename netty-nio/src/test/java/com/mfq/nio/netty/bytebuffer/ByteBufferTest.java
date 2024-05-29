package com.mfq.nio.netty.bytebuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufferTest {
    public static void main(String[] args) {
        //��ʼ������ 9��byte
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocate byteBuf", byteBuf);
        //write�����ı�дָ��,д��֮��дָ��δ��capacity��ʱ��,buffer��Ȼ��д,дָ������4
        byteBuf.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(new byte[]{1, 2, 3, 4})", byteBuf);
        //write�����ı�дָ��,д��һ��int���ͺ�,buffer��Ȼ��д,дָ������4,��Ϊһ��int = 4 byte
        byteBuf.writeInt(Integer.MAX_VALUE);
        print("writeInt(Integer.MAX_VALUE)", byteBuf);
        //write�����ı�дָ��,д��֮��=capacity,buffer����д,����û�дﵽmaxCapacity
        byteBuf.writeBytes(new byte[]{5});
        print("writeBytes(new byte[]{5})", byteBuf);
        //write�����ı�дָ��,д��֮�� ���� > capacity ���� buffer����д�Ѿ�����д,��������
        //���ݺ�capacity�ı�
        byteBuf.writeBytes(new byte[]{6});
        print("writeBytes(new byte[]{6})", byteBuf);
        //get�������ı��дָ��
        System.out.println("getBytes(3) return : " + byteBuf.getByte(3));
        System.out.println("getShort(3) return : " + byteBuf.getShort(3));
        System.out.println("getInt(3) return : " + byteBuf.getInt(3));
        print("getByte()", byteBuf);
        //read�����ı��ָ��
        byte[] dst = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(dst);
        print("readBytes", byteBuf);
    }

    private static void print(String action, ByteBuf buffer) {
        System.out.println("after ===========" + action + "============");
        System.out.println("capacity() : " + buffer.capacity());

        System.out.println("maxCapacity() : " + buffer.maxCapacity());

        System.out.println("readerIndex() : " + buffer.readerIndex());
        System.out.println("readableBytes() : " + buffer.readableBytes());

        System.out.println("isReadable() : " + buffer.isReadable());

        System.out.println("writerIndex() : " + buffer.writerIndex());

        System.out.println("writableBytes() : " + buffer.writableBytes());

        System.out.println("isWritable() : " + buffer.isWritable());

        System.out.println("maxWritableBytes() : " + buffer.maxWritableBytes());

        System.out.println();
    }
}
