package com.task.taskmanager.controller;

import com.task.taskmanager.dto.NotesDTO;
import com.task.taskmanager.service.NotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class NotesController {

    private NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping("/notes/{id}/addNote")
    public ResponseEntity<String> addNote(@PathVariable int id, @RequestBody NotesDTO noteDTO) {
        return new ResponseEntity<>(notesService.addNotesToTask(id, noteDTO), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        if (ex instanceof IllegalArgumentException) {
            return new ResponseEntity<>("Task Not Found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
