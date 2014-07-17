package com.mylearn.netty.mygame;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MyMethodTest {

	public static void main(String[] args) throws Exception {

		packageEvent();
	}

	public static String getEventPackageSource() {
		JSONObject json = new JSONObject();
		json.put("type", "move");
		json.put("token", "63542");
		json.put("direction", "direction");
		json.put("isMove", true);
		json.put("x", 200);
		json.put("y", 100);
		return json.toString();
	}

	public static void packageEvent(){
		String source = getEventPackageSource();
		int length = source.length();
		byte[] pa = new byte[length + 5];
		String l = Integer.toHexString(length);
		System.out.println("source length : " + length);
		System.out.println("l : " + l);
		System.out.println("l byte length : " + l.getBytes().length);
		System.out.println("source byte length :" + source.getBytes().length);
		//System.arraycopy(l.getBytes(), 0, pa, 0, l.getBytes().length);
		System.arraycopy(source.getBytes(), 0, pa, 5, length);
		String str = new String(pa);
		System.out.println(str);
		JSONObject json = JSON.parseObject(new String(pa));
		//System.out.println(json.toString());
	}
}
