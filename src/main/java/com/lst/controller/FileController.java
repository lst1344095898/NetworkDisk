package com.lst.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lst.entity.FileEntity;
import com.lst.entity.State;
import com.lst.service.FileService;
import com.mysql.cj.Session;
@CrossOrigin(origins = { "http://127.0.0.1:5500", "null" })
@RestController
@RequestMapping("/file")
public class FileController{
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
		String fileid=request.getParameter("fileid");
		// 通过fileid获取filePath
		FileEntity fileInfo= fileService.getFilePathByFileId(fileid);
		System.out.println(fileid);
		// 从文件夹获取文件
		FileSystemResource file=new FileSystemResource(fileInfo.getFilePath());
		// new 一个响应头
		HttpHeaders headers=new HttpHeaders();
		//在响应头添加文件名
		String fileNameOver=fileInfo.getFileName()+fileInfo.getType();
		headers.add( "Content-Disposition", "attachment;filename=" + new String(fileNameOver.getBytes("gb2312"), "ISO8859-1" ));
		//"application/octet-stream"字节流响应
		return ResponseEntity.ok().
				headers(headers).
				contentLength(file.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(file.getInputStream()));
		
	}
	@ResponseBody
	@PostMapping("/getFileByUserName")
	public State<ArrayList<FileEntity>> getFileByUserName(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String usernameString=(String) session.getAttribute("username");
		System.out.println("session:"+usernameString);
		State<ArrayList<FileEntity>> state=new State<ArrayList<FileEntity>>();
		System.out.println(request.getParameter("username"));
		state = fileService.getFileByUserName(request.getParameter("username"));
		System.out.println(state);
		return state;
	}
	// 得到所有图片
	@PostMapping("/getAllImages")
	public State<ArrayList<FileEntity>> getAllImages(HttpServletRequest request){
		State<ArrayList<FileEntity>> state=new State<ArrayList<FileEntity>>();
		state = fileService.getAllImages();
		return state;
	}
	@RequestMapping(value = "/images/getImageByfileId/{imageId}",method = RequestMethod.GET)
	public String getImageByImageId(@PathVariable String imageId,HttpServletRequest request,HttpServletResponse response) throws IOException{
		System.out.println(imageId);
		ServletOutputStream out= null;
		FileInputStream ips=null;
		try {
			//获取图片位子
			String imgPath=fileService.getFilePathByFileId(imageId).getFilePath();
			ips=new FileInputStream(new File(imgPath));
			response.setContentType("multipart/form-data");
			out = response.getOutputStream();
			//读取文件流
			int len=0;
			byte[] buffer=new byte[1024 * 10];
			while ((len=ips.read(buffer))!=-1) {
				out.write(buffer,0,len);
			}
			out.flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			out.close();
			ips.close();
		}
		return null;
		
	}
}
