package com.kaiyun.until;

import java.io.IOException;
import java.net.URLEncoder;

import sun.misc.BASE64Encoder;

public class FileUtil {
	
	  public static String encodeDownloadFilename(String filename, String agent)
	            throws IOException {
	        if (agent.contains("Firefox")) {
	            filename = "=?UTF-8?B?"
	                    + new BASE64Encoder().encode(filename.getBytes("utf-8"))
	                    + "?=";
	            filename = filename.replaceAll("\r\n", "");
	        }else if (agent.contains("Chrome")){
	            filename = "=?UTF-8?B?"
	                    + new BASE64Encoder().encode(filename.getBytes("utf-8"))
	                    + "?=";
	            filename = filename.replaceAll("\r\n", "");
	        }
	        else { // ie浏览器和其他
	            filename = URLEncoder.encode(filename, "utf-8");
	            filename = filename.replace("+"," ");
	        }
	        return filename;
	    }

}
