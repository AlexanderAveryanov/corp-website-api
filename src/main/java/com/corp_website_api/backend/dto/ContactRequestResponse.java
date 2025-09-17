package com.corp_website_api.backend.dto;

import com.corp_website_api.backend.entity.RequestStatus;

import java.time.LocalDateTime;

// DTO для ответов, чтобы не возвращать сущности напрямую
public class ContactRequestResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String message;
    private RequestStatus status;
    private LocalDateTime createdAt;

    public ContactRequestResponse(Long id, String name, String email, String phone,
                                  String message, RequestStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Пустой конструктор (для Spring)
    public ContactRequestResponse() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}

    public RequestStatus getStatus() {return status;}
    public void setStatus(RequestStatus status) {this.status = status;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}
