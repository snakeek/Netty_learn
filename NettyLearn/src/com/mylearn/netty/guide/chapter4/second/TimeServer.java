package com.mylearn.netty.guide.chapter4.second;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeServer {

	public void bind(int port) throws Exception {

		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();

		try {
			ServerBootstrap boot = new ServerBootstrap();
			boot.group(boss, worker)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childHandler(new ChildChannelHandler());
			ChannelFuture future = boot.bind(port).sync();

			future.channel().closeFuture().sync();
		} finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
			ch.pipeline().addLast(new StringDecoder());
			ch.pipeline().addLast(new TimeServerHandler());
		}
	}

	public static void main(String[] args) throws Exception {

		int port = 8080;

		new TimeServer().bind(port);
	}
}
