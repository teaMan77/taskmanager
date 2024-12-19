package com.task.taskmanager.service;

import com.task.taskmanager.dto.NotesDTO;
import com.task.taskmanager.dto.TaskDTO;
import com.task.taskmanager.entity.Notes;
import com.task.taskmanager.entity.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotesService {
    private TaskService taskService;
    private int notesId = 1;

    public NotesService(TaskService taskService) {
        this.taskService = taskService;
    }

    public String addNotesToTask(int id, NotesDTO notesDTO) {
        List<Task> tasks = taskService.getTasks();

        Task task = tasks.stream().filter(t ->
                t.getId() == id).findFirst().orElseThrow(() ->
                new IllegalArgumentException("Task not found"));

        Notes notes = new Notes();
        notes.setTitle(notesDTO.getTitle());
        notes.setContent(notesDTO.getContent());
        notes.setId(id);
        notesId++;

        List<Notes> notesList = new ArrayList<>();
        notesList.add(notes);

        task.setNotesList(notesList);

        return "Note added successfully";
    }
}
