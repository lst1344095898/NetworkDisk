package com.lst.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {
    @GetMapping("/home")
    public  String goHome(){
        return  "home";
    }
    @GetMapping("/images")
    public  String goImages(){
        return  "images";
    }
}
