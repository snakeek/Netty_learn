package com.mylearn.netty.guide.chapter7;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqClientHandler extends ChannelHandlerAdapter {

	public SubReqClientHandler() {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 10; i++)
			ctx.writeAndFlush(subReq(i));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("Receive server response : {" + msg + "}");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
		for (int i = 0; i < 10; i++)
			ctx.writeAndFlush(subReq(i));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	private SubscribeReq subReq(int i) {

		SubscribeReq req = new SubscribeReq();
		req.setAddress("北京市海淀区健翔大厦2-4-13");
		req.setPhoneNumber("12345678901");
		req.setProductName("Netty 指南");
		req.setSubReqID(i);
		req.setUserName("snakeek");
		return req;
	}
}
