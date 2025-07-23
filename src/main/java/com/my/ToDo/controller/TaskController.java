package com.my.ToDo.controller;

import com.my.ToDo.model.Task;
import com.my.ToDo.repository.TaskRepository;
import com.my.ToDo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Получить все задачи
    @Operation(summary = "Получить все задачи", description = "Возвращает список всех задач в базе данных")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Создать новую задачу
    @Operation(summary = "Создать новую задачу", description = "Создает новую задачу на основе переданных данных")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные в запросе"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task.getTitle());
    }

    // Получить задачу по ID
    @Operation(summary = "Получить задачу по ID", description = "Возвращает задачу с указанным ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача найдена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Обновить задачу
    @Operation(summary = "Обновить задачу", description = "Обновляет существующую задачу по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные в запросе"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return taskService.updateTask(id, taskDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Удалить задачу
    @Operation(summary = "Удалить задачу", description = "Удаляет задачу по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskService.deleteTask(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
