package com.lst.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lst.dao.FileDao;
import com.lst.service.FileService;
@Service
public class FileServiceImpl  implements FileService{
	@Autowired
	private FileDao fileDao;
	@Override
	public boolean saveFile(String fileName, String filePath, String type, long fileSize) {
		// TODO Auto-generated method stub
		if (fileDao.saveFile(fileName, filePath, type, fileSize)!=0) {
			return true;
		}
		return false;
	}

}
