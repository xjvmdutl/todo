package com.example.todo.todos.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.example.todo.todos.entity.Todo;
import com.example.todo.todos.repository.TodoRepository;
import com.example.todo.todos.request.TodoRequest;
import com.example.todo.todos.response.TodoResponse;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TODO Test")
class TodoServiceImplTest {
    TodoRepository todoRepository = mock(TodoRepository.class);

    TodoService todoService = new TodoServiceImpl(todoRepository);

    Todo todo;

    @BeforeEach
    public void setUp(){
        todo = Todo.builder()
            .id(1L)
            .completed(null)
            .completedAt(LocalDate.now())
            .name("string")
            .build();
    }

    @Test
    public void 조회_테스트(){
        //given

        //when
        when(todoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));
        TodoResponse findTodo = todoService.getTodo(1L);
        //then
        assertThat(findTodo.getId()).isEqualTo(todo.getId());
        assertThat(findTodo.getCompleted()).isEqualTo(todo.getCompleted());
        assertThat(findTodo.getName()).isEqualTo(todo.getName());
    }

    @Test
    public void 조회_실패_테스트(){
        //given

        //when
        when(todoRepository.findById(todo.getId())).thenThrow(new IllegalArgumentException("찾을 수 없는 ID 입니다"));
        //then
        assertThrows(
            IllegalArgumentException.class,
            ()->{
                todoService.getTodo(1L);
            }
        );
    }

    @Test
    public void 생성_테스트(){
        //given
        TodoRequest request = new TodoRequest("string", null);

        //when
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        TodoResponse saveTodo = todoService.createTodo(request);

        //then
        assertThat(saveTodo.getId()).isEqualTo(todo.getId());
        assertThat(saveTodo.getCompleted()).isEqualTo(request.getCompleted());
        assertThat(saveTodo.getName()).isEqualTo(request.getName());
    }

    @Test
    public void 수정_테스트(){
        //given
        TodoRequest request = new TodoRequest("string", null);

        //when
        when(todoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));
        TodoResponse saveTodo = todoService.updateTodo(todo.getId(), request);

        //then
        assertThat(saveTodo.getId()).isEqualTo(todo.getId());
        assertThat(saveTodo.getCompleted()).isEqualTo(request.getCompleted());
        assertThat(saveTodo.getName()).isEqualTo(request.getName());
    }


    @Test
    public void 삭제_테스트(){
        //given

        //when
        todoService.deleteTodo(todo.getId());

        //then
        then(todoRepository).should(times(1)).deleteById(todo.getId());
    }

    @Test
    public void 삭제_실패_테스트(){
        //given

        //when
        doThrow(new IllegalArgumentException()).when(todoRepository).deleteById(todo.getId());

        //then
        assertThrows(
            IllegalArgumentException.class,
            ()->{
                todoService.deleteTodo(1L);
            }
        );
    }
}