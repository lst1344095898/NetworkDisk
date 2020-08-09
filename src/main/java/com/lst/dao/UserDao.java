package com.lst.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
	String getPasswordByUsername(@Param("username") String username);
}
