package com.mylearn.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.mylearn.netty.object.UnixTime;

public class TimeDecoder extends ByteToMessageDecoder {

	private static int count = 1;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {

		System.out.println("Time Decoder! and the counter is : " + count);
		count++;
		if (in.readableBytes() < 4) {
            return; // (3)
        }

        out.add(new UnixTime(in.readInt())); // (4)
	}

}
