package com.todo.entity;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class Todo {
    private final Long id;
    private final String description;
    private final boolean completed;
}