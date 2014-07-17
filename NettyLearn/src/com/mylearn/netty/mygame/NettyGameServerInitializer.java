package com.mylearn.netty.mygame;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class NettyGameServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
//		pipeline.addLast(new HttpServerCodec());
//		pipeline.addLast(new HttpObjectAggregator(65536));
//		pipeline.addLast(new ChunkedWriteHandler());
//		pipeline.addLast("framedecoder",new LengthFieldBasedFrameDecoder(1024*1024*1024, 0, 4,0,4));
//		pipeline.addLast(new StringDecoder());
		pipeline.addLast(new NettyGameServerHandler());
	}

}
