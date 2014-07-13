package com.mylearn.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class SecondNettyClient {

	private final String host;
	private final int port;

	public SecondNettyClient(String host, int port) {

		this.host = host;
		this.port = port;
	}

	public void start() throws Exception {

		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap boot = new Bootstrap();
			boot.group(group)
				.channel(NioSocketChannel.class)
				.remoteAddress(new InetSocketAddress(host, port))
				.handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new SecondNettyClientHandler());
					}
				});

			ChannelFuture future = boot.connect().sync();

			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {

		new SecondNettyClient("127.0.0.1", 8080).start();
	}
}
