package com.corp_website_api.backend.dto;

public class ErrorResponse {
    private String message;
    private String errorCode;

    /**
     * DTO для стандартизированных ошибок API.
     * Используется для возврата структурированных ошибок клиенту.
     *
     * @param message   Сообщение для пользователя
     * @param errorCode Код для автоматической обработки
     */
    public ErrorResponse(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
}
