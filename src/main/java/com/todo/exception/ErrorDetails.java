package com.todo.exception;

import lombok.Builder;

@Builder
public class ErrorDetails {
    private String errorCode;
    private String errorDescription;
}
