package com.lst.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lst.service.UserService;

@CrossOrigin(origins = {"http://127.0.0.1:5500", "null" })
@RestController
public class LoginController {
@Autowired
private UserService UserService;
@PostMapping("/login")
public boolean login(@RequestBody Map<String, String> user) {
	if (UserService.login(user.get("username"), user.get("password"))) {
		return true;
	}
	return false;
}
}
