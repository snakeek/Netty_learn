package com.mylearn.netty.guide.chapter8.bookorder;

import com.mylearn.netty.guide.chapter8.protobuf.SubscribeRegProto;

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
//		for (int i = 0; i < 10; i++)
//			ctx.writeAndFlush(subReq(i));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	private SubscribeRegProto.SubscribeReq subReq(int i) {

		SubscribeRegProto.SubscribeReq.Builder builder = SubscribeRegProto.SubscribeReq.newBuilder();
		builder.setAddress("北京市海淀区健翔大厦2-4-13");
		builder.setProduceName("12345678901");
		builder.setSubReqID(i);
		builder.setUserName("snakeek");
		return builder.build();
	}
}
