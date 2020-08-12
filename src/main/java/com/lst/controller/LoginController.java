package com.lst.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lst.entity.State;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.lst.service.UserService;

@CrossOrigin(origins = {"http://127.0.0.1:5500", "null" })
@RestController
public class LoginController {
	@Autowired
	private UserService UserService;
	@RequestMapping("/")
	public  String goPublish(){
		return "public";
	}
	@PostMapping("/login")
	@ResponseBody
	public boolean login(@RequestBody Map<String, String> user, HttpServletRequest request) {
		if (UserService.login(user.get("username"), user.get("password"))) {
			// 获取session对象
			HttpSession session=request.getSession();
			// 向Session域写入属性
			String seeString=user.get("username");
			// 将登录的用户名写入session
			session.setAttribute("username", seeString);
			return true;
		}
		return false;
	}
	// 暂时获得用户名字后期再加
	@PostMapping("/getUserInfo")
	public State<String> getUserInfo(HttpServletRequest request){
			//得到session
			HttpSession session=request.getSession(false);
			String username=(String) session.getAttribute("username");
			State<String> stringState = new State<>();
			stringState.setState_id(true);
			stringState.setData(username);
			return stringState;
	}

}
