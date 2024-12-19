package com.task.taskmanager.controller;

import com.task.taskmanager.dto.TaskDTO;
import com.task.taskmanager.dto.TaskResponseDTO;
import com.task.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/addTask")
    public ResponseEntity<String> addTask(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(taskService.addTask(taskDTO), HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable int id) {
        TaskResponseDTO taskDTO = taskService.getTaskById(id);

        if (taskDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable int id, @RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<TaskDTO>(taskService.updateTask(id, taskDTO), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        if (ex instanceof ParseException) {
            return new ResponseEntity<>("Invalid Date Format", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
