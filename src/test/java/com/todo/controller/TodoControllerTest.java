package com.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.entity.Todo;
import com.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TodoControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }

    @Test
    public void testGetAllTodos() throws Exception {
        when(todoService.getAllTodos()).thenReturn(Arrays.asList(new Todo(1L, "Task 1", false), new Todo(2L, "Task 2", true)));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    public void testGetTodoById_Success() throws Exception {
        when(todoService.getTodoById(1L)).thenReturn(Optional.of(new Todo(1L, "Task 1", false)));

        mockMvc.perform(get("/api/todos/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("Task 1"));
    }

    @Test
    public void testGetTodoById_NotFound() throws Exception {
        when(todoService.getTodoById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/todos/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateTodo_Success() throws Exception {
        Todo todo = new Todo(null, "Task 1", false);
        when(todoService.createTodo(any(Todo.class))).thenReturn(true);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateTodo_Failure() throws Exception {
        Todo todo = new Todo(null, "Task 1", false);
        when(todoService.createTodo(any(Todo.class))).thenReturn(false);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testUpdateTodo_Success() throws Exception {
        Todo todo = new Todo(1L, "Task 1", true);
        when(todoService.updateTodo(anyLong(), any(Todo.class))).thenReturn(1);

        mockMvc.perform(patch("/api/todos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTodo_NotFound() throws Exception {
        Todo todo = new Todo(1L, "Task 1", true);
        when(todoService.updateTodo(anyLong(), any(Todo.class))).thenReturn(0);

        mockMvc.perform(patch("/api/todos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTodo_Success() throws Exception {
        when(todoService.deleteTodoById(anyLong())).thenReturn(1);

        mockMvc.perform(delete("/api/todos/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTodo_NotFound() throws Exception {
        when(todoService.deleteTodoById(anyLong())).thenReturn(0);

        mockMvc.perform(delete("/api/todos/{id}", 1L))
                .andExpect(status().isNotFound());
    }


}
