package com.corp_website_api.backend.config;

import com.corp_website_api.backend.dto.ErrorResponse;
import com.corp_website_api.backend.exception.InvalidStatusException;
import com.corp_website_api.backend.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Глобальный обработчик исключений для всего приложения.
 * Перехватывает исключения и возвращает красивые JSON-ответы вместо stack traces.
 */
@RestControllerAdvice // Делает класс глобальным обработчиком для всех контроллеров
public class GlobalExceptionHandler {

    /**
     * Обрабатывает ошибки валидации (@Valid аннотации).
     * Когда клиент отправляет некорректные данные, этот метод перехватывает ошибку
     * и возвращает понятный JSON с перечнем ошибок.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class) // Ловит конкретный тип исключения
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        // Создаем мапу для ошибок вида { "fieldName": "error message" }
        Map<String, String> errors = new HashMap<>();

        // Преобразуем сложный объект ошибок в простой Map
        ex.getBindingResult() // Возвращает контейнер с информацией обо всех ошибках валидации
                .getFieldErrors() // Возвращает список ошибок конкретных полей
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        // Возвращаем HTTP 400 с JSONом ошибок
        // Здесь badRequest() - означает, что надо вернуть HTTP 400
        // body() - передаем ошибки в теле ответа
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); // возвращаем 404 + текст ошибки
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidStatus(InvalidStatusException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), "INVALID_STATUS");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}