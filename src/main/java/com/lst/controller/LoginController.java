package com.lst.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.lst.service.UserService;

@CrossOrigin(origins = {"http://127.0.0.1:5500", "null" })
@RestController
public class LoginController {
	@Autowired
	private UserService UserService;
	@GetMapping("/")
	public ModelAndView getindex() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		return mv;
	}
	
@PostMapping("/login")
@ResponseBody
public boolean login(@RequestBody Map<String, String> user, HttpServletRequest request) {
	if (UserService.login(user.get("username"), user.get("password"))) {
		// 获取session对象
		HttpSession session=request.getSession();
		// 向Session域写入属性
		System.out.println(user.get("username"));
		String seeString=user.get("username");
		session.setAttribute("username", seeString);
		System.out.println("getsession:"+ session.getAttribute("username"));
		return true;
	}
	return false;
}

}
