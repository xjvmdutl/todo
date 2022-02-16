package com.example.todo.todos.entity;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.example.todo.todos.entity.util.BaseTimeEntity;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class Todo extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Boolean completed;

    private LocalDate completedAt;

    public void updateName(String name) {
        if(getName() != name)
            this.name = name;
    }


    public void updateCompleted(Boolean completed) {
        if (getCompleted() != completed)
            this.completed = completed;
    }
}
