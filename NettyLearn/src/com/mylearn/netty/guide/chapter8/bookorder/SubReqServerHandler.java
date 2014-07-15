package com.mylearn.netty.guide.chapter8.bookorder;

import com.mylearn.netty.guide.chapter8.protobuf.SubscribeRegProto;
import com.mylearn.netty.guide.chapter8.protobuf.SubscribeRespProto;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqServerHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		SubscribeRegProto.SubscribeReq req = (SubscribeRegProto.SubscribeReq) msg;

		if ("snakeEK".equalsIgnoreCase(req.getUserName())) {
			System.out.println("Service accept client subscribe req : {" + req.toString() + "}");
			ctx.writeAndFlush(resp(req.getSubReqID()));
		}
	}

	private SubscribeRespProto.SubscribeResp resp(int subReqID) {

		SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
		builder.setSubReqID(subReqID);
		builder.setRespCode(2);
		builder.setDesc("Netty book order succeed, 3 days later, sent to the designated address");

		return builder.build();
	}
}
