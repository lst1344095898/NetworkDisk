package com.lst.controller;

import com.lst.entity.FileEntity;
import com.lst.entity.State;
import com.lst.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

@CrossOrigin(origins = { "http://127.0.0.1:5500", "null" })
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/upPublicFile")
    public String upPublicFile(@RequestParam("file") MultipartFile file , HttpServletRequest request) throws Exception{
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
        String filePath="H:/testdown/public/";
        fileName = filePath  + fileName;
        //文件大小
        long fileSize=file.getSize();
        // new 一个file对象
        File dest = new File(fileName);
        // 判断路径是否存在，如果不存在则创建
        // 上传用户权限
        String username="public";
        if(!dest.getParentFile().exists()) {
            // 创建文件夹
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            // 判断是否把文件路径存储到数据表中
            if(adminService.saveFile(tmpString, fileName, type, fileSize,username)) {
                return "上传成功";
            }else {
                return "上传失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }
    @PostMapping("/getAllFile")
    public State<ArrayList<FileEntity>> getAllFile(){
        State<ArrayList<FileEntity>> state=adminService.getAllFile();
        return state;
    }
    @PostMapping("/getAllImages")
    public State<ArrayList<FileEntity>> getAllImages(HttpServletRequest request){
        State<ArrayList<FileEntity>> state= adminService.getAllImages();
        return state;
    }
    //通过图片ID得到所有图片
    @RequestMapping(value = "/getImageByfileId/{imageId}",method = RequestMethod.GET)
    public String getImageByImageId(@PathVariable String imageId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletOutputStream out= null;
        FileInputStream ips=null;
        try {
            //从数据库中获取图片位置
            String imgPath=adminService.getFilePathByFileId(imageId).getFilePath();
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
