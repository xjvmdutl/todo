package com.example.todo.todos.service;

import com.example.todo.todos.request.TodoRequest;
import com.example.todo.todos.response.TodoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {
    TodoResponse getTodo(Long todoId);

    TodoResponse createTodo(TodoRequest todoRequest);

    TodoResponse updateTodo(Long todoId, TodoRequest todoRequest);

    void deleteTodo(Long todoId);

    Page<TodoResponse> getTodo(Pageable pageable);
}
