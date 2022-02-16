package com.example.todo.todos.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UPGRADE_REQUIRED;

import com.example.todo.todos.request.TodoRequest;
import com.example.todo.todos.response.TodoResponse;
import com.example.todo.todos.service.TodoService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/")
    public ResponseEntity<Page<TodoResponse>> getTodo(
        @RequestParam(name = "limit") final int limit,
        @RequestParam(name = "skip") final int skip
    ){
        Pageable pageable = PageRequest.of(skip, limit);
        return new ResponseEntity(todoService.getTodo(pageable), OK);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(
        @PathVariable(name = "todoId") Long todoId
    ){
        return new ResponseEntity(todoService.getTodo(todoId), OK);
    }



    @PostMapping("")
    public ResponseEntity<TodoResponse> createTodo(
        @RequestBody @Valid TodoRequest todoRequest
    ){
        return new ResponseEntity(todoService.createTodo(todoRequest), CREATED);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoResponse> updateTodo(
        @PathVariable(name = "todoId") Long todoId,
        @RequestBody @Valid TodoRequest todoRequest
    ){
        return new ResponseEntity(todoService.updateTodo(todoId, todoRequest), OK);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<TodoResponse> deleteTodo(
        @PathVariable(name = "todoId") Long todoId
    ){
        todoService.deleteTodo(todoId);
        return new ResponseEntity(NO_CONTENT);
    }
}
