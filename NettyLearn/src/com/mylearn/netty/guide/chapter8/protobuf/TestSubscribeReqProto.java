package com.mylearn.netty.guide.chapter8.protobuf;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;

public class TestSubscribeReqProto {

	private static byte[] encode(SubscribeRegProto.SubscribeReq req) {

		return req.toByteArray();
	}

	private static SubscribeRegProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
		return SubscribeRegProto.SubscribeReq.parseFrom(body);
	}

	private static SubscribeRegProto.SubscribeReq createSubscribeReq() {
		SubscribeRegProto.SubscribeReq.Builder builder = SubscribeRegProto.SubscribeReq.newBuilder();
		builder.setSubReqID(1);
		builder.setUserName("snakeEK");
		builder.setProduceName("Netty book");
		List<String> address = new ArrayList<String>();
		address.add("Beijing Haidian");
		address.add("Tianjin Puyuan");
		address.add("测试中文数据");
		builder.setAddress("测试中文信息");
		return builder.build();
	}

	public static void main(String[] args) throws Exception {
		SubscribeRegProto.SubscribeReq req = createSubscribeReq();
		System.out.println("Before encode : " + req.toString());
		SubscribeRegProto.SubscribeReq req2 = decode(encode(req));
		System.out.println("After decode : " + req.toString());
		System.out.println("Assert equal : -->" + req2.equals(req));
	}
}
