package com.lst.dao;

import com.lst.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface AdminDao {
    int saveFile(@Param("fileName") String fileName, @Param("filePath") String filePath,
                 @Param("type") String type, @Param("fileSize") long fileSize, @Param("username") String username, @Param("uptime") String uptime);
    ArrayList<FileEntity> getAllFile();
    ArrayList<FileEntity> getAllImages();
    FileEntity getFilePathByFileId(@Param("fileid") int fileid);
}
