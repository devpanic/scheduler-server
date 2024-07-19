package com.devpanic.scheduler.service;

import com.devpanic.scheduler.dto.todo.CreateTodoDTO;
import com.devpanic.scheduler.entity.Todo;
import com.devpanic.scheduler.entity.User;
import com.devpanic.scheduler.repository.TodoRepository;
import com.devpanic.scheduler.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }

    public Todo createTodo(CreateTodoDTO createTodoDTO) {
        User user = userRepository.findByUsername(createTodoDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Todo todo = Todo.builder()
                .title(createTodoDTO.getTitle())
                .description(createTodoDTO.getDescription())
                .user(user)
                .build();

        return todoRepository.save(todo);
    }
}
