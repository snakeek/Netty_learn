package com.mylearn.netty.guide.chapter7;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class SubReqClient {

	public void connect(int port, String host) throws Exception {

		EventLoopGroup group = new NioEventLoopGroup();
	
		try {
			Bootstrap boot = new Bootstrap();
			boot.group(group)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch)
							throws Exception {
						ch.pipeline().addLast(new ObjectDecoder(1024,
								ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
						ch.pipeline().addLast(new ObjectEncoder());
						ch.pipeline().addLast(new SubReqClientHandler());
					}
				});
			ChannelFuture future = boot.connect(host, port).sync();
			future.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new SubReqClient().connect(8080, "localhost");
	}
}
