package com.corp_website_api.backend.exception;

// Кастомное исключение - "не найдено"
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
