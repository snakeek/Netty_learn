package com.mylearn.netty.mygame;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.alibaba.fastjson.JSONObject;

public class NettyGameClient {
	
	public static void main(String[] args) throws Exception {
		new NettyGameClient("localhost");
	}

	private Socket socket;
	private OutputStream writer;
	private BufferedInputStream reader;

	public NettyGameClient(String ip) throws IOException {
		socket = new Socket();
		socket.connect(new InetSocketAddress(ip, 8080));
		writer = socket.getOutputStream();
		reader = new BufferedInputStream(socket.getInputStream());

		byte[] resultArray = null;

		byte[] sendData = "abcde".getBytes();
		try {
			// 定义一个发送消息协议格式：|--header:5 byte--|--content:XX--|
			// 发送数据-------------------------------
			writer.write(packageEvent());
			writer.flush();
			byte[] head = new byte[5];
			while (reader.read(head) != -1) {
				
				// 接收数据-------------------------------
				resultArray = copy(reader, head);
				System.out.println(new String(resultArray));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	public static byte[] packageEvent(){
		JSONObject json = new JSONObject();
		json.put("type", EventDefine.CMD_TEST);
		json.put("token", 25645);
		json.put("direction", "Foward");
		json.put("isMove", true);
		json.put("x", 100);
		json.put("y", 200);
		String source = json.toString();
		int length = source.length();
		byte[] pa = new byte[length + 5];
		String l = Integer.toHexString(length);
		System.arraycopy(l.getBytes(), 0, pa, 0, l.getBytes().length);
		System.arraycopy(source.getBytes(), 0, pa, 5, length);
		return pa;
	}
	
	private static int getHeadLength(byte[] bytes){
		int count = 0;
		while(bytes[count] != 0){
			count ++;
		}
		return Integer.valueOf(new String(bytes, 0, count), 16);
	}

	private static byte[] copy(BufferedInputStream is, byte[] head)
			throws Exception {
		byte[] body = new byte[getHeadLength(head)];
		is.read(body);
		
		return body;
	}

	public void close() throws IOException{
		writer.close();
		reader.close();
		socket.close();
	}
}
