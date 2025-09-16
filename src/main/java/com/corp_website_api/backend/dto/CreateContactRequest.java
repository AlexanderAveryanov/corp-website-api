package com.corp_website_api.backend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO для создания новой заявки от клиента.
 * Содержит только данные, которые приходят из API.
 * Best Practices для DTO: Разделение на Input/Output:
 *   CreateContactRequest.java  // ← Для входящих данных (POST)
 *   ContactRequestResponse.java // ← Для исходящих данных (GET)
 */
public class CreateContactRequest {

    // Валидация для клиентских данных
    @NotBlank(message = "Имя обязательно")
    private String name;

    @NotBlank(message = "Email обязателен")
    private String email;

    // Поле может быть null - нет @NotBlank
    private String phone;

    @NotBlank(message = "Сообщение обязательно")
    private String message;

    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
