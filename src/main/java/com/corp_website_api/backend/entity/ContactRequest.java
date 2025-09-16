package com.corp_website_api.backend.entity;

import jakarta.persistence.*; // JPA аннотации для работы с БД
import jakarta.validation.constraints.*; // Валидация данных

import java.time.LocalDateTime;

/**
 * Сущность "Заявка от клиента" - отображает таблицу в базе данных.
 * Каждый объект этого класса = одна строка в таблице contact_requests.
 * Best Practices: имена полей в java писать camelCase, но в БД будет автоматически в нижнем регистре (snake_case)
 */
@Entity // Указывает, что это сущность JPA
@Table(name = "contact_requests") // Указывает на связь с таблицей в БД
public class ContactRequest {

    @Id // Первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкремент
    private Long id;

    // Все валидации происходят до сохранения в БД
    @NotBlank(message = "Имя обязательно") // Не пустая строка
    private String name;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный email") // Проверка на корректность email
    private String email;

    private String phone;

    @NotBlank(message = "Сообщение обязательно")
    @Column(length = 1000) // Ограничение длины в БД
    private String message;

    @Enumerated(EnumType.STRING)  // Сохраняет Enum в виде строки
    private RequestStatus status;

    private LocalDateTime createdAt;

    // Конструкторы
    public ContactRequest() {
        this.createdAt = LocalDateTime.now();
        this.status = RequestStatus.NEW; // Дефолтный статус
    }

    // Геттеры и сеттеры (можно сгенерировать автоматически в IDEA)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
