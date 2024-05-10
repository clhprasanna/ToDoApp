package com.todo.service;

import com.todo.entity.Todo;
import com.todo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAllTodos();
    }

    public boolean createTodo(Todo todo) {
        int rowsAffected = todoRepository.createTodo(todo);
        return rowsAffected > 0;
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findTodoById(id);
    }

    public int updateTodo(Long id, Todo todo) {
        Todo todoToUpdate = todo.toBuilder().id(id).build();
        return todoRepository.updateTodo(todoToUpdate);
    }

    public int deleteTodoById(Long id) {
        return todoRepository.deleteTodoById(id);
    }


}
