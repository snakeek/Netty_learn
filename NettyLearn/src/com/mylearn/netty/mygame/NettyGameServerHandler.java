package com.mylearn.netty.mygame;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

import com.alibaba.fastjson.JSONObject;

public class NettyGameServerHandler extends ChannelHandlerAdapter {

	public static final ChannelGroup channelGroup = new DefaultChannelGroup("all-channels", GlobalEventExecutor.INSTANCE);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channel active");
		channelGroup.add(ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("channel read...");
		ByteBuffer head = ByteBuffer.allocate(4);
		ByteBuf buf = (ByteBuf) msg;
		buf.readBytes(head);
		ByteBuffer body = ByteBuffer.allocate(getHeadLength(head.array()));
		buf.readBytes(body);
		String str = new String(body.array());
		JSONObject json = JSONObject.parseObject(str);
		System.out.println(json.toJSONString());
		ByteBuf echo = Unpooled.copiedBuffer(packageEvent(json));
		channelGroup.writeAndFlush(echo);
	}

	private byte[] packageEvent(JSONObject jsonIn){
		JSONObject json = new JSONObject();
		json.put("type", EventDefine.CMD_TEST);
		json.put("token", 25645);
		json.put("direction", jsonIn.getString("direction"));
		json.put("isMove", jsonIn.getBoolean("isMove"));
		json.put("x", jsonIn.getIntValue("x") * 50);
		json.put("y", jsonIn.getIntValue("y") * 50);
		String source = json.toString();
		int length = source.length();
		byte[] pa = new byte[length + 5];
		String l = Integer.toHexString(length);
		System.arraycopy(l.getBytes(), 0, pa, 0, l.getBytes().length);
		System.arraycopy(source.getBytes(), 0, pa, 5, length);
		return pa;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channel read complete");
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,
			SocketAddress localAddress, ChannelPromise promise)
			throws Exception {
		System.out.println("connect");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	private int getHeadLength(byte[] bytes){
		int count = 0;
		while(bytes[count] != 0){
			count ++;
		}
		return Integer.valueOf(new String(bytes, 0, count), 16);
	}
}
