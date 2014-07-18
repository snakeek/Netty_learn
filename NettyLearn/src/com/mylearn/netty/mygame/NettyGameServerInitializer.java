package com.mylearn.netty.mygame;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class NettyGameServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
//		pipeline.addLast(new HttpServerCodec());
//		pipeline.addLast(new HttpObjectAggregator(65536));
//		pipeline.addLast(new ChunkedWriteHandler());
//		pipeline.addLast("framedecoder",new LengthFieldBasedFrameDecoder(1024*1024*1024, 0, 4,0,4));
//		pipeline.addLast(new StringDecoder());
		// 解码
		pipeline.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
		// 构造函数传递要解码成的类型
		pipeline.addLast("protobufDecoder", new ProtobufDecoder(
				JavaPackage.Test.getDefaultInstance()));
		// 编码
		pipeline.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());
		pipeline.addLast("protobufEncoder", new ProtobufEncoder());
		pipeline.addLast(new NettyGameServerHandler());
	}

}
