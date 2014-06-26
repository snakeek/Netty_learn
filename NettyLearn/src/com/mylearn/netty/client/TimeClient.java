package com.mylearn.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {

	public static void main(String[] args) throws Exception {

		//for (int i = 0; i < 1000; i++) {
			new TimeClient().run();
		//}
    }

	public void run() throws Exception {

		final String host = "localhost";
		final int port = 8080;
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			final Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new TimeDecoder(),
							new TimeClientHandler());
				}
			});

			for (int i = 0; i < 1000; i++) {
			
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
						// Start the client.
						ChannelFuture f = b.connect(host, port).sync(); // (5)
						
							Thread.sleep(300000);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				t.start();
			}

			// Wait until the connection is closed.
			//f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
