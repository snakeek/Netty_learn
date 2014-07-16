package com.mylearn.netty.mygame;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class NettyGameClient {
	
	public static void main(String[] args) throws Exception {
		new NettyGameClient("localhost");
	}

	private Socket socket;
	private OutputStream writer;
	private BufferedInputStream reader;
	
	ByteArrayOutputStream bout = new ByteArrayOutputStream();

	public NettyGameClient(String ip) throws IOException {
		socket = new Socket();
		socket.connect(new InetSocketAddress(ip, 8080));
		writer = socket.getOutputStream();
		reader = new BufferedInputStream(socket.getInputStream());

		byte[] resultArray = null;

		byte[] sendData = "abcde".getBytes();
		try {
			// 定义一个发送消息协议格式：|--header:4 byte--|--content:10MB--|
			// 获取一个4字节长度的协议体头
			byte[] dataLength = intToByteArray(4, sendData.length);
			// 和请求的数据组成一个请求数据包
			byte[] requestMessage = combineByteArray(dataLength, sendData);
			// 发送数据-------------------------------
			writer.write(requestMessage);
			writer.flush();
			// 接收数据-------------------------------
			resultArray = copy(reader, bout);
			System.out.println(new String(resultArray));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	private static byte[] intToByteArray(int byteLength, int intValue) {
		return ByteBuffer.allocate(byteLength).putInt(intValue).array();
	}

	private static byte[] combineByteArray(byte[] array1, byte[] array2) {
		byte[] combined = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, combined, 0, array1.length);
		System.arraycopy(array2, 0, combined, array1.length, array2.length);
		return combined;
	}

	private static byte[] copy(InputStream is, ByteArrayOutputStream bout)
			throws Exception {
		byte[] b = new byte[1024];
		int len = 0;
		len = is.read(b);
		while (len != -1) {
			bout.write(b, 0, len);
			len = is.read(b);
		}
		return bout.toByteArray();
	}

	public void close() throws IOException{
		writer.close();
		reader.close();
		socket.close();
	}
}
