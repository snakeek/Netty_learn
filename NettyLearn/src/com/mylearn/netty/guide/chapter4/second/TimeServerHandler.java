package com.mylearn.netty.guide.chapter4.second;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {

	private int counter;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String body = (String) msg;
		System.out.println("The time Server receive order : " + body
				+ " ; the counter is : " + ++counter);
		String currentTime = "Query Time Order".equalsIgnoreCase(body) ? new java.util.Date(
				System.currentTimeMillis()).toString() : "Bad Order";
				currentTime = currentTime + System.getProperty("line.separator");
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.writeAndFlush(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
}
