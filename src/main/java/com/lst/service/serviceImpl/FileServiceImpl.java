package com.lst.service.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lst.dao.FileDao;
import com.lst.entity.FileEntity;
import com.lst.entity.State;
import com.lst.service.FileService;
@Service
public class FileServiceImpl  implements FileService{
	@Autowired
	private FileDao fileDao;
	@Override
	public boolean saveFile(String fileName, String filePath, String type, long fileSize,String username) {
		// TODO Auto-generated method stub

		if (fileDao.saveFile(fileName, filePath, type, fileSize,username,getDate())!=0) {
			return true;
		}
		return false;
	}
	public String getDate(){
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		String data=(String) formatter.format(date);
		return data;
	}
	@Override
	public State<ArrayList<FileEntity>> getFileByUserName(String username) {
		State<ArrayList<FileEntity>> state=new State<ArrayList<FileEntity>>();
		ArrayList<FileEntity> statEntities= new ArrayList<FileEntity>();
		statEntities=fileDao.getFileByUserName(username);
		if (statEntities!=null) {
			state.setState_id(true);
			state.setData(statEntities);
			state.setError(null);
			return state;
		}
		state.setState_id(false);
		state.setData(null);
		state.setError(null);
		return state;
	}
	@Override
	public FileEntity getFilePathByFileId(String fileid) {
		int id= Integer.parseInt(fileid);
		FileEntity filePath=fileDao.getFilePathByFileId(id);
		return filePath;
	}
	// 得到所有图片
	@Override
	public State<ArrayList<FileEntity>> getAllImages() {
		State<ArrayList<FileEntity>> state=new State<ArrayList<FileEntity>>();
		ArrayList<FileEntity> statEntities= new ArrayList<FileEntity>();
		statEntities=fileDao.getAllImages();
		if (statEntities!=null) {
			state.setState_id(true);
			state.setData(statEntities);
			state.setError(null);
			return state;
		}
		state.setState_id(false);
		state.setData(null);
		state.setError(null);
		return state;
	}

	@Override
	public State<ArrayList<FileEntity>> getAllImagesByUsername(String username) {
		State<ArrayList<FileEntity>> state=new State<ArrayList<FileEntity>>();
		ArrayList<FileEntity> fileEntities=fileDao.getAllImagesByUsername(username);
		if (fileEntities!=null) {
			state.setState_id(true);
			state.setData(fileEntities);
			state.setError(null);
			return state;
		}
		state.setState_id(false);
		state.setData(null);
		state.setError(null);
		return state;
	}

}
