package com.example.todo.todos.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoRequest {
    @NotNull
    @NotEmpty
    private String name;

    private Boolean completed;

}
