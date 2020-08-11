package com.lst.service;

import java.util.ArrayList;

import com.lst.entity.FileEntity;
import com.lst.entity.State;

public interface FileService {
	boolean saveFile(String fileName,String filePath, String type,long fileSize);
	State<ArrayList<FileEntity>> getFileByUserName(String username);
	FileEntity getFilePathByFileId(String fileid);
	State<ArrayList<FileEntity>> getAllImages();
}
