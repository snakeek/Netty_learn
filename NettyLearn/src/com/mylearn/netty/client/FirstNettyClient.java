package com.mylearn.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.example.echo.EchoClientHandler;

public class FirstNettyClient {

	private final String host;
    private final int port;
    private final int firstMessageSize;

    public FirstNettyClient(String host, int port, int firstMessageSize) {
        this.host = host;
        this.port = port;
        this.firstMessageSize = firstMessageSize;
    }

    public void run() throws Exception {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(
                             //new LoggingHandler(LogLevel.INFO),
                             new EchoClientHandler(firstMessageSize));
                 }
             });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        // Print usage if no argument is specified.
//        if (args.length < 2 || args.length > 3) {
//            System.err.println(
//                    "Usage: " + EchoClient.class.getSimpleName() +
//                    " <host> <port> [<first message size>]");
//            return;
//        }

        // Parse options.
        final String host = "localhost";
        final int port = 8080;
        final int firstMessageSize = 256;
//        if (args.length == 3) {
//            firstMessageSize = Integer.parseInt(args[2]);
//        } else {
//            firstMessageSize = 256;
//        }

        new FirstNettyClient("localhost", 8080, 256).run();
    }
}