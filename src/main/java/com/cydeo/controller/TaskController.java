package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task")
public class TaskController {
    TaskService taskService;
    ProjectService projectService;
    UserService userService;

    public TaskController(TaskService taskService, ProjectService projectService, UserService userService){
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createTask(Model model){
        model.addAttribute("task",new TaskDTO());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees", userService.findAll() );
        model.addAttribute("tasks", taskService.findAll());
        return "/task/create";
    }

    @PostMapping("/create")
    public String insertTask(@ModelAttribute("task") TaskDTO taskDTO){
        taskService.save(taskDTO);
        return "redirect:/task/create";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id")Long id){
        taskService.deleteById(id);
        return "redirect:/task/create";
    }
    @GetMapping("/update/{taskId}")
    public String editTask(@PathVariable("taskId")Long taskId, Model model){
        model.addAttribute("task",taskService.findById(taskId));
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees", userService.findAll() );
        model.addAttribute("tasks", taskService.findAll());
        return "/task/update";
    }
//    @PostMapping("/update/{taskId}")
//    public String updateTask(@PathVariable("taskId") Long taskId,@ModelAttribute("task") TaskDTO taskDTO){
//        taskDTO.setId(taskId);
//        taskService.update(taskDTO);
//        return "redirect:/task/create";
//    }

    @PostMapping("/update/{id}")
    public String updateTask(@ModelAttribute("task") TaskDTO taskDTO){
        taskService.update(taskDTO);
        return "redirect:/task/create";
    }
}
