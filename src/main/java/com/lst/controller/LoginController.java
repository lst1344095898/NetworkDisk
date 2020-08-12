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
	private UserService userService;
	@RequestMapping("/")
	public  ModelAndView  goPublish(HttpServletRequest request){
		ModelAndView  modelAndView = new ModelAndView ();
		modelAndView.setViewName("public");
		return modelAndView;
	}
	@PostMapping("/login")
	@ResponseBody
	public State<String> login(@RequestBody Map<String, String> user, HttpServletRequest request) {
		State<String> stringState = new State<>();
		if (userService.login(user.get("username"), user.get("password"))) {
			stringState.setState_id(true);
			// 获取session对象
			HttpSession session=request.getSession();
			// 向Session域写入属性
			String seeString=user.get("username");
			// 将登录的用户名写入session
			session.setAttribute("username", seeString);
			if (user.get("username").equals("lst")){
				System.out.println("验证admin");
				stringState.setData("admin");
				return  stringState;
			}
			return stringState;
		}
		stringState.setState_id(false);
		return stringState;
	}
	// 暂时获得用户名字后期再加
	@PostMapping("/getUserInfo")
	public State<String> getUserInfo(HttpServletRequest request) throws Exception{
		String username;
		try {
			HttpSession session=request.getSession(false);
			username=(String) session.getAttribute("username");
		}catch (Exception e){
			System.out.println("抛出session错误没有登录无法获得session值");
			username = "";
		}
		//得到session
		State<String> stringState = new State<>();
		stringState.setState_id(true);
		stringState.setData(username);
		return stringState;
	}
	//修改密码
	@PostMapping("/editPassword")
	public boolean editPassword(@RequestBody Map<String ,String> user,HttpServletRequest request){
		String username;
		try {
			HttpSession session=request.getSession(false);
			username=(String) session.getAttribute("username");
		}catch (Exception e){
			System.out.println("session错误无法修改密码");
			username="";
			return false;
		}
		System.out.println("username:" +username+"password:"+user.get("password"));
		if (userService.editPassword(username,user.get("password"))){
			return true;
		}
		return false;
	}
	@PostMapping("/cleanSession")
	public boolean cleanSession(HttpServletRequest request) throws  Exception{
		try {
			HttpSession session=request.getSession(false);
			session.removeAttribute("username");
			return true;
		}catch (Exception e){
			System.out.println("session:删除是吧i，因为你没有登录");
			return false;
		}
	}

}
