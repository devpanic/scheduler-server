package com.devpanic.scheduler.service;

import com.devpanic.scheduler.dto.todo.CreateTodoDTO;
import com.devpanic.scheduler.dto.todo.ModifyTodoDTO;
import com.devpanic.scheduler.entity.Todo;
import com.devpanic.scheduler.entity.User;
import com.devpanic.scheduler.repository.TodoRepository;
import com.devpanic.scheduler.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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

    @Transactional
    public Todo updateTodo(ModifyTodoDTO modifyTodoDTO) {
        User user = userRepository.findByUsername(modifyTodoDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Todo todo = todoRepository.findById(modifyTodoDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Todo not found"));

        if (!todo.getUser().equals(user)) {
            throw new AccessDeniedException("Don't have permission");
        }

        todo.setTitle(modifyTodoDTO.getTitle());
        todo.setDescription(modifyTodoDTO.getDescription());
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo not found with id: " + id));

        todoRepository.delete(todo);
    }
}
