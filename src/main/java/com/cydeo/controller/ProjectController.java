package com.cydeo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping ("/project")
@Controller
public class ProjectController {

    @GetMapping("/create")
    public String createProject(){
        return "/project/create";
    }
}
