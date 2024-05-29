package com.mfq.nio.netty.bytebuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufferTest {
    public static void main(String[] args) {
        //初始化分配 9个byte
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocate byteBuf", byteBuf);
        //write方法改变写指针,写完之后写指针未到capacity的时候,buffer仍然可写,写指针增加4
        byteBuf.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(new byte[]{1, 2, 3, 4})", byteBuf);
        //write方法改变写指针,写完一个int类型后,buffer任然可写,写指针增加4,因为一个int = 4 byte
        byteBuf.writeInt(Integer.MAX_VALUE);
        print("writeInt(Integer.MAX_VALUE)", byteBuf);
        //write方法改变写指针,写完之后=capacity,buffer不可写,但是没有达到maxCapacity
        byteBuf.writeBytes(new byte[]{5});
        print("writeBytes(new byte[]{5})", byteBuf);
        //write方法改变写指针,写完之后 容量 > capacity 并且 buffer不可写已经不可写,触发扩容
        //扩容后capacity改变
        byteBuf.writeBytes(new byte[]{6});
        print("writeBytes(new byte[]{6})", byteBuf);
        //get方法不改变读写指针
        System.out.println("getBytes(3) return : " + byteBuf.getByte(3));
        System.out.println("getShort(3) return : " + byteBuf.getShort(3));
        System.out.println("getInt(3) return : " + byteBuf.getInt(3));
        print("getByte()", byteBuf);
        //read方法改变读指针
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
