package com.mylearn.netty.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.mylearn.netty.object.UnixTime;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	private int count = 1;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Time Server Handler!");
		System.out.println("Client Number is : " + count);
		ChannelFuture future = ctx.writeAndFlush(new UnixTime());
		future.addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		count++;
		super.handlerAdded(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("------------------------------------------------------------");
		super.handlerRemoved(ctx);
	}
	
}
