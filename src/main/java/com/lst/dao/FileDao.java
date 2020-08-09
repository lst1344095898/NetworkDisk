package com.lst.dao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface FileDao {
		int saveFile(@Param("fileName") String fileName,@Param("filePath") String filePath, 
				@Param("type") String type,@Param("fileSize") long fileSize);
}
