package com.task.taskmanager.service;
import com.task.taskmanager.dto.TaskDTO;
import com.task.taskmanager.dto.TaskResponseDTO;
import com.task.taskmanager.entity.Notes;
import com.task.taskmanager.entity.Task;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    List<Task> tasks = new ArrayList<Task>();
    private int taskId = 1;

    @Autowired
    private ModelMapper modelMapper;

    public String addTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskId);
        task.setDescription(taskDTO.getDescription());
        task.setTitle(taskDTO.getTitle());
        task.setDeadline(taskDTO.getDeadline());
        task.setCompleted(taskDTO.isCompleted());
        tasks.add(task);
        taskId++;
        return "Task added successfully";
    }

    public List<TaskDTO> getAllTasks() {
         return tasks.stream().map(task -> modelMapper.map(task, TaskDTO.class)).toList();
    }

    public TaskResponseDTO getTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                TaskResponseDTO taskDTO = modelMapper.map(task, TaskResponseDTO.class);
                taskDTO.setNotes(task.getNotesList());
                return taskDTO;
            }
        }
        return null;
    }

    public TaskDTO updateTask(int id, TaskDTO taskDTO) {
        if (taskDTO == null) {
            throw new IllegalArgumentException("TaskDTO cannot be null");
        }
        if (taskDTO.getDeadline() == null) {
            throw new IllegalArgumentException("Deadline cannot be null");
        }
        if (taskDTO.getDescription() == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (taskDTO.getTitle() == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }

        Task task = tasks.stream().filter(t ->t.getId() == id).findFirst().orElse(null);
        if (task == null) {
            throw new IllegalArgumentException("Task not found");
        }
        task.setDescription(taskDTO.getDescription());
        task.setTitle(taskDTO.getTitle());
        task.setDeadline(taskDTO.getDeadline());
        task.setCompleted(taskDTO.isCompleted());
        return modelMapper.map(task, TaskDTO.class);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
