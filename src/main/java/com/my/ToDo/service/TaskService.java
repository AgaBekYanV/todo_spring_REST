package com.my.ToDo.service;

import com.my.ToDo.model.Task;
import com.my.ToDo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(String title) {
        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(Long id, Task taskDetails) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if(taskOptional.isPresent()){
            Task task = taskOptional.get();
            task.setTitle(taskDetails.getTitle());
            task.setCompleted(taskDetails.isCompleted());
            return Optional.of(taskRepository.save(task));
        }
        return Optional.empty();
    }

    public Optional<Task> toggleTask(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if(taskOptional.isPresent()){
            Task task = taskOptional.get();
            task.setCompleted(!task.isCompleted());
            return Optional.of(taskRepository.save(task));
        }
        return Optional.empty();
    }

    public boolean deleteTask(Long id) {
        if(taskRepository.existsById(id)){
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
