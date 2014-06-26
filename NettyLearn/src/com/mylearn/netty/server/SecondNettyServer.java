package com.mylearn.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

public class SecondNettyServer {

	private final int port;

	public SecondNettyServer(int port) {
		this.port = port;
	}

	public void run() throws Exception{

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap boot = new ServerBootstrap();
			boot.group(bossGroup)
				.channel(NioServerSocketChannel.class)
				.localAddress(new InetSocketAddress(port))
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new SecondNettyServerhandler());
					}
				});

			ChannelFuture future = boot.bind().sync();

			System.out.println(SecondNettyServer.class.getName() + "start and listen on "
					+ future.channel().localAddress());
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully().sync();
		}
	}

	public static void main(String[] args) throws Exception {
		new SecondNettyServer(8080).run();
	}
}