package com.lst.controller;
import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lst.service.FileService;
@CrossOrigin(origins = { "http://127.0.0.1:5500", "null" })
@RestController
@RequestMapping("/file")
public class FileController {
	@Autowired
	private FileService fileService;
	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return "文件为空";
		}
		// 获取原始名字
		String fileName=file.getOriginalFilename();
		// 得到文件名字
		String tmpString=fileName;
		int j=tmpString.indexOf(".");
		tmpString=tmpString.substring(0,j);
		// 获取后缀名
		String type = fileName.substring(fileName.lastIndexOf("."));
		String filePath="H:/testdown/";
		// 保存路径
		fileName = filePath + fileName;
		System.out.println(fileName);
		long fileSize=file.getSize();
		// new 一个file对象
		File dest = new File(fileName);
		// 判断路径是否存在，如果不存在则创建
		if(!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			// 保存到服务器中
				file.transferTo(dest);
				if(fileService.saveFile(tmpString, fileName, type, fileSize)) {
					return "上传成功";
				}else {
					return "上传失败";
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "上传失败";
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping("/download")
	public ResponseEntity download(HttpServletRequest request)throws Exception{
		String fileName=request.getParameter("fileName");
		System.out.println(fileName);
		FileSystemResource file=new FileSystemResource("H:/testdown/相片.jpg");
		HttpHeaders headers=new HttpHeaders();
		//在响应头添加文件名
		headers.add("Content-Disposition","attachment; filename=123.jpg");
		//"application/octet-stream"字节流响应
		return ResponseEntity.ok().
				headers(headers).
				contentLength(file.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(file.getInputStream()));
		
	}
}
