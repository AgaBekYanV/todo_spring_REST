package com.my.ToDo.controller;

import com.my.ToDo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskWebController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public String getTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "tasks";
    }

    @PostMapping("/tasks")
    public String addTask(@RequestParam String title) {
        taskService.createTask(title);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}/toggle")
    public String toggleTask(@PathVariable Long id) {
        taskService.toggleTask(id).orElseThrow(() -> new IllegalArgumentException("Не верный ID задачи"));
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}")
    public String deleteTask(@PathVariable Long id) {
        if (!taskService.deleteTask(id)) {
            throw new IllegalArgumentException("Не верный ID задачи");
        }
        return "redirect:/tasks";
    }
}
