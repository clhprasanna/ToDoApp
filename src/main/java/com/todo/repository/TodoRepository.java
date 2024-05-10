package com.todo.repository;

import com.todo.entity.Todo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Todo> rowMapper = (rs, rowNum) -> Todo.builder()
            .id(rs.getLong("id"))
            .description(rs.getString("description"))
            .completed(rs.getBoolean("completed"))
            .build();

    public List<Todo> findAllTodos() {
        return jdbcTemplate.query("SELECT * FROM todo", rowMapper);
    }

    public Optional<Todo> findTodoById(Long id) {
        try {
            Todo todo = jdbcTemplate.queryForObject("SELECT * FROM todo WHERE id = " + id, rowMapper);
            return Optional.ofNullable(todo);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    public int createTodo(Todo todo) {
        return jdbcTemplate.update("INSERT INTO todo (description, completed) VALUES (?, ?)",
                todo.getDescription(), todo.isCompleted());

    }

    public int updateTodo(Todo todo) {
        return jdbcTemplate.update("UPDATE todo SET description = ?, completed = ? WHERE id = ?",
                todo.getDescription(), todo.isCompleted(), todo.getId());
    }

    public int deleteTodoById(Long id) {
        return jdbcTemplate.update("DELETE FROM todo WHERE id = ?", id);
    }
}

