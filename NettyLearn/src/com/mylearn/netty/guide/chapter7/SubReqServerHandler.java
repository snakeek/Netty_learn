package com.mylearn.netty.guide.chapter7;

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
		SubscribeReq req = (SubscribeReq) msg;
		if ("snakeEK".equalsIgnoreCase(req.getUserName())) {
			System.out.println("Service accept client subscribe req : {" + req.toString() + "}");
			ctx.writeAndFlush(resp(req.getSubReqID()));
		}
	}

	private SubscribeResp resp(int subReqID) {

		SubscribeResp resp = new SubscribeResp();
		resp.setSubReqID(subReqID);
		resp.setResqCode(2);
		resp.setResqDesc("Netty book order succeed, 3 days later, sent to the designated address");

		return resp;
	}
}
