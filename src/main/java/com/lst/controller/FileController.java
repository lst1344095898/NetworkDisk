package com.lst.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

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
import org.springframework.web.bind.annotation.*;
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
	// 上传文件
	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file , HttpServletRequest request) throws IOException {
		if (file.isEmpty()) {
			return "文件为空";
		}
		// 获取前端上传的文件名字=存入服务器的文件名
		String fileName=file.getOriginalFilename();
		// 得到文件名字除去后缀
		String tmpString=fileName;
		int j=tmpString.indexOf(".");
		tmpString=tmpString.substring(0,j);
		// 获取后缀名
		String type = fileName.substring(fileName.lastIndexOf("."));
		//服务器本地文件地址
		String filePath="H:/testdown/";
		//从seesion中得到用户名
		String username;
		try {
			HttpSession session=request.getSession(false);
			username=(String) session.getAttribute("username");
		}catch (Exception e){
			System.out.println("session错误");
			if (request.getParameter("htmlName")!=""){
				username=request.getParameter("htmlName");
			}else{
				return "上传失败";
			}

		}

		System.out.println("用户名："+ username);
		// 保存路径 服务器文件地址+文件名字 +上用户名
		fileName = filePath + username+ "/" + fileName;
		System.out.println("服务器完整存储路径"+fileName);
		long fileSize=file.getSize();
		// new 一个file对象
		File dest = new File(fileName);
		// 判断路径是否存在，如果不存在则创建
		if(!dest.getParentFile().exists()) {
			// 创建文件夹
			dest.getParentFile().mkdirs();
		}
		try {
				// 保存到服务器中
				file.transferTo(dest);
				// 判断是否把文件路径存储到数据表中
				if(fileService.saveFile(tmpString, fileName, type, fileSize,username)) {
					return "上传成功";
				}else {
					return "上传失败";
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "上传失败";
	}
	// 防治出错提醒 ResponseEntity<T> // 下载文件
	@SuppressWarnings("rawtypes")
	@RequestMapping("/download")
	public ResponseEntity download(HttpServletRequest request)throws Exception{
		//得到session 通过不同的用户权限从不同的文件夹得到文件
		HttpSession session=request.getSession(false);
		String username;
		try {
			username = (String) session.getAttribute("username");
		}catch (Exception e){
			System.out.println("没有登录“session”为空下载的是public资源");
			username = "public";
		}
//		username = (String) session.getAttribute("username");
		System.out.println(username);
		// 得到前端需要下载的文件id
		String fileid=request.getParameter("fileid");
		// 从数据库中通过fileid获取文件信息 		String filePath="H:/testdown/";
		FileEntity fileInfo= fileService.getFilePathByFileId(fileid);
		String Path= "H:/testdown/"+username+"/"+fileInfo.getFileName()+fileInfo.getType();
		// 通过文件地址从文件夹获取文件
		FileSystemResource file=new FileSystemResource(Path);
		// new 一个响应头
		HttpHeaders headers=new HttpHeaders();
		//在响应头添加文件名加后缀
		String fileNameOver=fileInfo.getFileName()+fileInfo.getType();
		headers.add( "Content-Disposition", "attachment;filename=" + new String(fileNameOver.getBytes("gb2312"), "ISO8859-1" ));
		//"application/octet-stream"字节流响应
		return ResponseEntity.ok().
				headers(headers).
				contentLength(file.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(file.getInputStream()));
		
	}
	// 通过用户名得到文件信息
	@PostMapping("/getFileByUserName")
	public State<ArrayList<FileEntity>> getFileByUserName(@RequestBody Map<String,String> map, HttpServletRequest request) throws  Exception{
		HttpSession session=request.getSession(false);
		String username;
		try {
			username = (String) session.getAttribute("username");
		}catch (Exception e){
			username = "";
			System.out.println("没有登录“session”");
		}
		try {
			if (map.get("public").equals("public")){
				username="public";
			}
		}catch (Exception e){
			System.out.println("不是public所以报错");
		}
		System.out.println("public"+map.get("public"));
		System.out.println("username:"+ username);
		State<ArrayList<FileEntity>> state=fileService.getFileByUserName(username);
		System.out.println(state);
		return state;
	}
	// 通过用户名得到所有图片信息
	@PostMapping("/getAllImagesByUsername")
	public State<ArrayList<FileEntity>> getAllImagesByUsername(@RequestBody Map<String,String> map,HttpServletRequest request)throws  Exception{
		State<ArrayList<FileEntity>> state=new State<ArrayList<FileEntity>>();
		String username;
		try {
			HttpSession session=request.getSession(false);
			username=(String) session.getAttribute("username");
		}catch (Exception e){
			username="";
			System.out.println("session错误");
		}
		try {
			if (map.get("public").equals("public")){
				username="public";
			}
		}catch (Exception e){
			System.out.println("不是public页面所以报错");
		}
		state=fileService.getAllImagesByUsername(username);
		System.out.println("username:"+username+"state:"+ state);
		return  state;
	}
	// 得到所有图片信息
	@PostMapping("/getAllImages")
	public State<ArrayList<FileEntity>> getAllImages(HttpServletRequest request){
		State<ArrayList<FileEntity>> state=new State<ArrayList<FileEntity>>();
		state = fileService.getAllImages();
		return state;
	}
	//通过图片ID得到所有图片
	@RequestMapping(value = "/images/getImageByfileId/{imageId}",method = RequestMethod.GET)
	public String getImageByImageId(@PathVariable String imageId,HttpServletRequest request,HttpServletResponse response) throws IOException{
		ServletOutputStream out= null;
		FileInputStream ips=null;
		try {
			//从数据库中获取图片位置
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
