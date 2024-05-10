package com.todo.service;

import com.todo.entity.Todo;
import com.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void testGetAllTodos() {
        List<Todo> todos = Arrays.asList(new Todo(1L, "Task 1", false), new Todo(2L, "Task 2", true));
        when(todoRepository.findAllTodos()).thenReturn(todos);

        List<Todo> result = todoService.getAllTodos();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(todoRepository).findAllTodos();
    }

    @Test
    void testCreateTodo_Success() {
        Todo todo = new Todo(null, "New Task", false);
        when(todoRepository.createTodo(todo)).thenReturn(1);

        boolean result = todoService.createTodo(todo);

        assertTrue(result);
        verify(todoRepository).createTodo(todo);
    }

    @Test
    void testCreateTodo_Failure() {
        Todo todo = new Todo(null, "New Task", false);
        when(todoRepository.createTodo(todo)).thenReturn(0);

        boolean result = todoService.createTodo(todo);

        assertFalse(result);
        verify(todoRepository).createTodo(todo);
    }

    @Test
    void testGetTodoById_Found() {
        Optional<Todo> todo = Optional.of(new Todo(1L, "Task 1", false));
        when(todoRepository.findTodoById(1L)).thenReturn(todo);

        Optional<Todo> result = todoService.getTodoById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(todoRepository).findTodoById(1L);
    }

    @Test
    void testGetTodoById_NotFound() {
        when(todoRepository.findTodoById(1L)).thenReturn(Optional.empty());

        Optional<Todo> result = todoService.getTodoById(1L);

        assertFalse(result.isPresent());
        verify(todoRepository).findTodoById(1L);
    }

    @Test
    void testUpdateTodo() {
        Todo todo = new Todo(null, "Task 1", true);
        when(todoRepository.updateTodo(any(Todo.class))).thenReturn(1);

        int result = todoService.updateTodo(1L, todo);

        assertEquals(1, result);
        verify(todoRepository).updateTodo(argThat(newTodo -> newTodo.getId() == 1L && newTodo.getDescription().equals("Task 1")));

    }

    @Test
    void testDeleteTodoById_Success() {
        when(todoRepository.deleteTodoById(1L)).thenReturn(1);

        int result = todoService.deleteTodoById(1L);

        assertEquals(1, result);
        verify(todoRepository).deleteTodoById(1L);
    }

    @Test
    void testDeleteTodoById_Failure() {
        when(todoRepository.deleteTodoById(1L)).thenReturn(0);

        int result = todoService.deleteTodoById(1L);

        assertEquals(0, result);
        verify(todoRepository).deleteTodoById(1L);
    }

}
