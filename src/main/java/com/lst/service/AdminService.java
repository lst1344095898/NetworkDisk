package com.lst.service;

import com.lst.entity.FileEntity;
import com.lst.entity.State;

import java.util.ArrayList;

public interface AdminService {
    boolean saveFile(String fileName,String filePath, String type,long fileSize,String username);
    State<ArrayList<FileEntity>> getAllFile();
    State<ArrayList<FileEntity>> getAllImages();
    FileEntity getFilePathByFileId(String fileid);

}
