package com.kaiyun.until;
import java.util.UUID;

public class RandomUUID {

	/**
	 * 获得随机UUID(不带“-”符号)
	 * @return
	 */
	public static String random(){
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
	
	/**
	 * 获得随机UUID
	 * @return
	 */
	public static String randomUUID(){
		return UUID.randomUUID().toString();
	}
	
}
