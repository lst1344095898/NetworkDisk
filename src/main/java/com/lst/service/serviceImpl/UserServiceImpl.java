package com.lst.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lst.dao.UserDao;
import com.lst.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao UserDao;
	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		if (UserDao.getPasswordByUsername(username).equals(password)) {
			return true;
		}
		return false;
	}

}
