package com.mylearn.netty.server;

import java.util.Calendar;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class FirstNettyServer {

	private final int port;

	public FirstNettyServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {

		//incoming connection
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//deal connection
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			//set up server
			ServerBootstrap boot = new ServerBootstrap();
			boot.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {

						ch.pipeline().addLast(new TimeEncoder(), new TimeServerHandler());
					}
				})
				.option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true);

			// Bind and start to accept incoming connections.
			ChannelFuture future = boot.bind(port).sync();

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to gracefully
			// shut down your server.
			future.channel().closeFuture().sync();
		} finally {

			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {

		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		else {
			port = 8080;
		}

		//new FirstNettyServer(port).run();
		System.out.println(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
	}
}
