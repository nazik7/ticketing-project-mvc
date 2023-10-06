package com.cydeo.controller;

import com.cydeo.bootstrap.DataGenerator;
import com.cydeo.dto.ProjectDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping ("/project")
@Controller
public class ProjectController {
    ProjectService projectService;
    UserService userService;

    public ProjectController(ProjectService projectService, UserService userService){
        this.projectService = projectService;
        this.userService = userService;
    }
    @GetMapping("/create")
    public String createProject(Model model){
        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("projects",projectService.findAll());
        model.addAttribute("managers", userService.findManagers());
        return "/project/create";
    }

    @PostMapping("/create")
    public String insertProject(@ModelAttribute("project") ProjectDTO projectDTO){
        projectService.save(projectDTO);
        return "redirect:/project/create";
    }

    @GetMapping("update/{projectCode}")
    public String update(@PathVariable String projectCode, Model model){
        model.addAttribute("project", projectService.findById(projectCode));
        model.addAttribute("projects",projectService.findAll());
        model.addAttribute("managers", userService.findManagers());
        return "project/update";
    }

}
