package com.corp_website_api.backend.controller;

import com.corp_website_api.backend.dto.ContactRequestResponse;
import com.corp_website_api.backend.dto.CreateContactRequest;
import com.corp_website_api.backend.entity.ContactRequest;
import com.corp_website_api.backend.service.ContactRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// = @Controller + @ResponseBody
// Помечаем класс как контроллер, все методы возвращают данные (не HTML страницы)
// Автоматически преобразует объекты в JSON
@RestController
@RequestMapping("api/contact-requests") // Задает базовый путь для всех методов контроллера
public class ContactRequestController {

    @Autowired
    private ContactRequestService service;

    // Создание новой заявки
    @PostMapping // обработка POST запросов
    public ResponseEntity<ContactRequestResponse> createRequest(
            @Valid // включение валидации DTO (ДО передачи в сервис, возвращает 400 при ошибке)
            @RequestBody // тело запроса → объект Java
            CreateContactRequest requestDto) {

        ContactRequestResponse response = service.createRequest(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Получение всех заявок
    @GetMapping
    public List<ContactRequestResponse> getAllRequests() {
        return service.getAllRequests();
    }

    // Получение заявки по id
    @GetMapping("/{id}") // путь с переменной
    public ResponseEntity<ContactRequestResponse> getRequest(@PathVariable Long id) { // @PathVariable извлечение id из URL
        return service.getRequestById(id)
                .map(ResponseEntity::ok) // 200 OK если найден
                .orElse(ResponseEntity.notFound().build()); // 404 если не найден
    }
}
