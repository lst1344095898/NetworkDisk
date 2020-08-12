package com.lst.service;

public interface UserService {
	boolean login(String username,String password);
	boolean editPassword(String username,String password);
}
