package com.lst.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
