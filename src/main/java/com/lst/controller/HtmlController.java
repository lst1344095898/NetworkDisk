package com.lst.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/html")
public class HtmlController {
    @GetMapping("/home")
    public  String goHome(){
        return  "home";
    }
    @GetMapping("/images")
    public  String goImages(){
        return  "images";
    }
    @GetMapping("/login")
    public  String gologin(){
        return  "login";
    }
//    @ResponseBody
    @GetMapping("/admin")
    public String goAdmin(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        String username=(String) session.getAttribute("username");
        if (username.equals("lst")){
            System.out.println("跳转admin");
            return "admin";
        }else {
            return  "error";
        }

    }
}
