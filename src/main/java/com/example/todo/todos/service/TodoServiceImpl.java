package com.example.todo.todos.service;

import com.example.todo.todos.entity.Todo;
import com.example.todo.todos.repository.TodoRepository;
import com.example.todo.todos.request.TodoRequest;
import com.example.todo.todos.response.TodoResponse;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    @Override
    @Transactional(readOnly = true)
    public TodoResponse getTodo(Long todoId) {
        Todo todo = getById(todoId);
        return new TodoResponse(todo);
    }

    @Override
    public TodoResponse createTodo(TodoRequest todoRequest) {
        Todo saveTodo = todoRepository.save(
            Todo.builder().name(todoRequest.getName()).completed(todoRequest.getCompleted())
                .build());
        return new TodoResponse(saveTodo);
    }

    @Override
    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    @Override
    public Page<TodoResponse> getTodo(Pageable pageable) {
        return todoRepository.findAll(pageable).map(TodoResponse::new);
    }

    @Override
    public TodoResponse updateTodo(Long todoId, TodoRequest todoRequest) {
        Todo todo = getById(todoId);
        todo.updateName(todoRequest.getName());
        todo.updateCompleted(todo.getCompleted());
        return new TodoResponse(todo);
    }

    private Todo getById(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(()-> new IllegalArgumentException("찾을 수 없는 ID 입니다"));
    }
}
