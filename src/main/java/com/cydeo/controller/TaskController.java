package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
        model.addAttribute("employees", userService.findEmployees() );
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
        model.addAttribute("employees", userService.findEmployees() );
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

    @GetMapping("/employee/pending-tasks")
    public String employeeEditTask(Model model){
        model.addAttribute("tasks",taskService.findAllUnfinishedTasks());
        return "/task/pending-tasks";
    }

    @GetMapping("/employee/edit/{taskId}")
    public String employeeEditTask(@PathVariable("taskId") Long taskId, Model model){
        model.addAttribute("task",taskService.findById(taskId));
        model.addAttribute("tasks",taskService.findAllUnfinishedTasks());
        model.addAttribute("statuses", Status.values());
        return "/task/status-update";
    }

    @PostMapping("/employee/update/{taskId}")
    public String employeeUpdateTask(@Validated @ModelAttribute("task")TaskDTO task, Model model, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {

            model.addAttribute("tasks", taskService.findAllUnfinishedTasks());
            model.addAttribute("statuses", Status.values());

            return "/task/status-update";

        }
        taskService.updateStatus(task);
        return "redirect:/task/employee/pending-tasks";
    }

}
