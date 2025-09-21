package com.corp_website_api.backend.exception;

// Некорректный статус
public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String message) {
        super(message);
    }
}
