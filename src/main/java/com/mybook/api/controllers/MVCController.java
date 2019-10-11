package com.mybook.api.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class MVCController {
    public String home(){
        return "index.html";
    }
}
