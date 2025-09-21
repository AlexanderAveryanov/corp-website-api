package com.corp_website_api.backend.dto;

import jakarta.validation.constraints.NotBlank;

// Обновление статуса заявки
public class UpdateStatusRequest {

    @NotBlank(message = "Статус обязателен")
    private String status;


    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
