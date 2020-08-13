package com.lst.aspect;

import com.lst.controller.LoginController;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

@Aspect
@Component
public class Logger {
    @Autowired
    private LoginController loginController;
    private  final Logger logger= LoggerFactory.getLogger(Log.class);
}
