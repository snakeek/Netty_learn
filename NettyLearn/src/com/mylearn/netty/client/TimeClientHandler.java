package com.mylearn.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.mylearn.netty.object.UnixTime;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("Time Client Handler!");
        UnixTime m = (UnixTime) msg;
        System.out.println(m);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
