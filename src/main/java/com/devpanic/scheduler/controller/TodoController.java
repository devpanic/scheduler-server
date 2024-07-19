package com.devpanic.scheduler.controller;

import com.devpanic.scheduler.dto.todo.CreateTodoDTO;
import com.devpanic.scheduler.entity.Todo;
import com.devpanic.scheduler.service.TodoService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public List<Todo> getAll() {
        return todoService.findAll();
    }

    @PostMapping
    public ResponseEntity createTodo(@RequestBody CreateTodoDTO createTodoDTO) {
        try {
            Todo createdTodo = todoService.createTodo(createTodoDTO);
            return ResponseEntity
                    .created(new URI("/api/todos/" + createdTodo.getId()))
                    .body(createdTodo);
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
