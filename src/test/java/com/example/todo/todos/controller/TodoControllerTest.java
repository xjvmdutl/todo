package com.example.todo.todos.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.todo.todos.entity.Todo;
import com.example.todo.todos.request.TodoRequest;
import com.example.todo.todos.response.TodoResponse;
import com.example.todo.todos.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(TodoController.class)
@MockBean(JpaMetamodelMappingContext.class)
class TodoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TodoService todoService;

    TodoResponse todoResponse;

    @BeforeEach
    public void setup(){
        todoResponse = new TodoResponse(Todo.builder()
            .id(1L)
            .completed(null)
            .completedAt(LocalDate.now())
            .name("string")
            .build());
    }

    @Test
    public void 조회_테스트() throws Exception {
        //given
        Long todoId = 1L;

        //when
        when(todoService.getTodo(todoId)).thenReturn(todoResponse);

        ResultActions result = mockMvc.perform(
            get("/todos/{todoId}",todoId)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result
            .andExpect(status().isOk())
            .andExpect(handler().handlerType(TodoController.class))
            .andExpect(handler().methodName("getTodo"))
            .andExpect(jsonPath("$.id", is(todoResponse.getId().intValue())))
            .andExpect(jsonPath("$.completed", is(todoResponse.getCompleted())))
            .andExpect(jsonPath("$.name", is(todoResponse.getName())));
    }

    @Test
    public void 조회_실패_테스트() throws Exception {
        //given
        Long todoId = 1L;

        //when
        when(todoService.getTodo(todoId)).thenThrow(new IllegalArgumentException());

        ResultActions result = mockMvc.perform(
            get("/todos/{todoId}",todoId)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result
            .andExpect(status().is4xxClientError())
            .andExpect(handler().handlerType(TodoController.class))
            .andExpect(handler().methodName("getTodo"))
            .andExpect(jsonPath("$.status", is("404")))
            .andExpect(jsonPath("$.error", is("Not Found")));
    }

    @Test
    public void 생성_테스트() throws Exception {
        //given

        //when
        when(todoService.createTodo(any(TodoRequest.class))).thenReturn(todoResponse);

        ResultActions result = mockMvc.perform(
            post("/todos")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(new TodoRequest("string", null)))
        );

        //then
        result
            .andExpect(status().isCreated())
            .andExpect(handler().handlerType(TodoController.class))
            .andExpect(handler().methodName("createTodo"))
            .andExpect(jsonPath("$.id", is(todoResponse.getId().intValue())))
            .andExpect(jsonPath("$.completed", is(todoResponse.getCompleted())))
            .andExpect(jsonPath("$.name", is(todoResponse.getName())));
    }


    @Test
    public void 수정_테스트() throws Exception {
        //given
        Long todoId = 1L;

        //when
        when(todoService.updateTodo(anyLong(), any(TodoRequest.class))).thenReturn(todoResponse);

        ResultActions result = mockMvc.perform(
            put("/todos/{todoId}", todoId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new TodoRequest("string", null)))
        );

        //then
        result
            .andExpect(status().isOk())
            .andExpect(handler().handlerType(TodoController.class))
            .andExpect(handler().methodName("updateTodo"))
            .andExpect(jsonPath("$.id", is(todoResponse.getId().intValue())))
            .andExpect(jsonPath("$.completed", is(todoResponse.getCompleted())))
            .andExpect(jsonPath("$.name", is(todoResponse.getName())));
    }

    @Test
    public void 수정_실패_테스트() throws Exception {
        //given
        Long todoId = 1L;

        //when
        when(todoService.updateTodo(anyLong(), any(TodoRequest.class))).thenThrow(new IllegalArgumentException());

        ResultActions result = mockMvc.perform(
            put("/todos/{todoId}", todoId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new TodoRequest("string", null)))
        );

        //then
        result
            .andExpect(status().is4xxClientError())
            .andExpect(handler().handlerType(TodoController.class))
            .andExpect(handler().methodName("updateTodo"))
            .andExpect(jsonPath("$.status", is("404")))
            .andExpect(jsonPath("$.error", is("Not Found")));
    }

    @Test
    public void 삭제_테스트() throws Exception {
        //given
        Long todoId = 1L;

        //when

        ResultActions result = mockMvc.perform(
            delete("/todos/{todoId}", todoId)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result
            .andExpect(status().isNoContent())
            .andExpect(handler().handlerType(TodoController.class))
            .andExpect(handler().methodName("deleteTodo"))
        ;
    }

    @Test
    public void 삭제_실패_테스트() throws Exception {
        //given
        Long todoId = 1L;

        //when
        doThrow(new IllegalArgumentException()).when(todoService).deleteTodo(todoId);

        ResultActions result = mockMvc.perform(
            delete("/todos/{todoId}", todoId)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result
            .andExpect(status().is4xxClientError())
            .andExpect(handler().handlerType(TodoController.class))
            .andExpect(handler().methodName("deleteTodo"))
            .andExpect(jsonPath("$.status", is("404")))
            .andExpect(jsonPath("$.error", is("Not Found")));
    }
}