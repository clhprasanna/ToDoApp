package com.todo.repository;

import com.todo.entity.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private TodoRepository todoRepository;

    @Test
    void testFindAllTodos() {
        List<Todo> expectedTodos = Arrays.asList(
                Todo.builder().id(1L).description("Task 1").completed(true).build(),
                Todo.builder().id(2L).description("Task 2").completed(false).build()
        );
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expectedTodos);

        List<Todo> result = todoRepository.findAllTodos();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Task 1", result.get(0).getDescription());
    }

    @Test
    void testFindTodoById_Found() {
        Todo expectedTodo = Todo.builder().id(1L).description("Task 1").completed(true).build();
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class))).thenReturn(expectedTodo);

        Optional<Todo> result = todoRepository.findTodoById(1L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Task 1", result.get().getDescription());
    }

    @Test
    void testFindTodoById_NotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        Optional<Todo> result = todoRepository.findTodoById(888L);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void testCreateTodo() {
        Todo newTodo = Todo.builder().description("Task 1").completed(false).build();
        when(jdbcTemplate.update(anyString(), anyString(), anyBoolean())).thenReturn(1);

        int result = todoRepository.createTodo(newTodo);

        Assertions.assertEquals(1, result);
    }

    @Test
    void testUpdateTodo() {
        Todo updatedTodo = Todo.builder().id(1L).description("Task 1").completed(true).build();
        when(jdbcTemplate.update(anyString(), anyString(), anyBoolean(), anyLong())).thenReturn(1);

        int result = todoRepository.updateTodo(updatedTodo);

        Assertions.assertEquals(1, result);
    }

    @Test
    void testDeleteTodoById() {
        when(jdbcTemplate.update(anyString(), anyLong())).thenReturn(1);

        int result = todoRepository.deleteTodoById(1L);

        Assertions.assertEquals(1, result);
    }


}
