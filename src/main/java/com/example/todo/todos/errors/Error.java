package com.example.todo.todos.errors;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class Error {
    private String status;
    private String error;

    public Error(String message, HttpStatus status) {
        this.error = message;
        this.status = converter(status);
    }

    private String converter(HttpStatus status) {
         return status.toString().split(" ")[0];
    }
}
