package com.kaiyun.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kaiyun.pojo.User;
import com.kaiyun.until.FileUtil;

@Controller
@RequestMapping("/excel")
public class ExcelController {
	
	@RequestMapping("import")
	public void importExcel(){
		
	}
	@RequestMapping("/toExcelImport")
	public ModelAndView toExcelImport(ModelAndView mv){
		mv.setViewName("excelImport");
		return mv;
	}
	
	/**
	 * app文件上传
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping("appUploadFile")
	public String saveAppFile(MultipartFile file,HttpServletRequest request,String filename){
		
		String result = "SUCCESS";
		
		try {
			
		filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
		
		String savePath = "D:/dc";
		
		File tempFile = new File(savePath+"/"+filename);
		
		File parentFile = tempFile.getParentFile();
		
		if(!parentFile.exists()){
			
			parentFile.mkdirs();
		}
			
			file.transferTo(tempFile);
			
		} catch (Exception e) {
			e.printStackTrace();
			result="ERROR";
		}
		
		return result;
	}
	
	/**
	 * 浏览器文件上传
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping("uploadFile")
	public ModelAndView saveFile(MultipartFile file,HttpServletRequest request,ModelAndView mv){
		
		String result = "SUCCESS";
		
		String fileName = file.getOriginalFilename();
		
		String realPath = request.getSession().getServletContext().getRealPath("excel/"+fileName);
		
		File tempFile = new File(realPath);
		
		File parentFile = tempFile.getParentFile();
		
		if(!parentFile.exists()){
			
			parentFile.mkdirs();
			
		}
		
		try {
			
			file.transferTo(tempFile);
			
		} catch (Exception e) {
			e.printStackTrace();
			result="ERROR";
		}
		
		mv.addObject("result", result);
		
		mv.setViewName("excelImport");
		
		return mv;
	}
	
	//文件下载
	@RequestMapping("fileDownLoad")
	public void downLoad(HttpServletRequest request, HttpServletResponse response){
		try {
			String filename = "";
			String path = request.getSession().getServletContext().getRealPath("excel");
			File file = new File(path);
			File[] files = file.listFiles();
			if(files.length==0){
				return;
			}
			Long fileSize = 0l;
			for (int i = 0; i < files.length; i++) {
				if(files[i].lastModified()>=fileSize){
					fileSize=files[i].lastModified();
					filename=files[i].getName();
				}
			}
			
			String agent = request.getHeader("User-Agent");
			String filenameEncoder = FileUtil.encodeDownloadFilename(filename, agent);
			//要下载的这个文件的类型-----客户端通过文件的MIME类型去区分类型
			response.setContentType(request.getSession().getServletContext().getMimeType(filename));
			//告诉客户端该文件不是直接解析 而是以附件形式打开(下载)----filename="+filename 客户端默认对名字进行解码
			response.setHeader("Content-Disposition", "attachment;filename="+filenameEncoder);
			//获取文件的绝对路径
			String finalPath = path+"/"+filename;
			//获得该文件的输入流
			InputStream in = new FileInputStream(finalPath);
			//获得输出流---通过response获得的输出流 用于向客户端写内容
			ServletOutputStream out = response.getOutputStream();
			//文件拷贝的模板代码
			int len = 0;
			byte[] buffer = new byte[1024];
			while((len=in.read(buffer))>0){
				out.write(buffer, 0, len);
			}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	@RequestMapping("/testParameter")
	@ResponseBody
	public String testParameter(HttpServletRequest request,User testParameter){
		System.out.println(testParameter.toString());
		return testParameter.toString();
	}

}
