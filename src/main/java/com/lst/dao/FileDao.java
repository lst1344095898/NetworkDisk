package com.lst.dao;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lst.entity.FileEntity;
@Mapper
public interface FileDao {
		int saveFile(@Param("fileName") String fileName,@Param("filePath") String filePath, 
				@Param("type") String type,@Param("fileSize") long fileSize);
		ArrayList<FileEntity> getFileByUserName(@Param("username") String username);
		FileEntity getFilePathByFileId(@Param("fileid") int fileid);
		ArrayList<FileEntity> getAllImages();
}
		
