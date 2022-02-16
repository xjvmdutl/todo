package com.example.todo.todos.response;

import static org.springframework.beans.BeanUtils.copyProperties;

import com.example.todo.todos.entity.Todo;
import java.time.LocalDate;
import lombok.Data;

@Data
public class TodoResponse {
     private Long id;
     private String name;
     private Boolean completed;
     private LocalDate completedAt;
     private LocalDate createdAt;
     private LocalDate updatedAt;

     public TodoResponse(Todo source) {
          copyProperties(source, this);
     }
}
