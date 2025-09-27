package com.corp_website_api.backend.service;

import com.corp_website_api.backend.dto.ContactRequestResponse;
import com.corp_website_api.backend.dto.CreateContactRequest;
import com.corp_website_api.backend.entity.ContactRequest;
import com.corp_website_api.backend.entity.RequestStatus;
import com.corp_website_api.backend.exception.InvalidStatusException;
import com.corp_website_api.backend.exception.NotFoundException;
import com.corp_website_api.backend.repository.ContactRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Cервис слой для бизнес-логики
// Эта аннотация делает класс Spring-сервисом
// Регистрирует класс в Spring-контейнере, позволяет использовать @Autowired в других классах, чтобы получить экземпляр этого сервиса из контейнера
@Service
public class ContactRequestService {

    @Autowired // Автоматически внедряет репозиторий
    private ContactRequestRepository repository;

    // Конвертирует Entity в DTO
    private ContactRequestResponse convertToResponse(ContactRequest entity) {
        ContactRequestResponse ropResponse = new ContactRequestResponse();
        ropResponse.setId(entity.getId());
        ropResponse.setName(entity.getName());
        ropResponse.setEmail(entity.getEmail());
        ropResponse.setPhone(entity.getPhone());
        ropResponse.setMessage(entity.getMessage());
        ropResponse.setStatus(entity.getStatus());
        ropResponse.setCreatedAt(entity.getCreatedAt());
        return ropResponse;
    }

    public ContactRequestResponse createRequest(CreateContactRequest requestDto) {
        ContactRequest ropRequest = new ContactRequest();
        ropRequest.setName(requestDto.getName());
        ropRequest.setEmail(requestDto.getEmail());
        ropRequest.setPhone(requestDto.getPhone());
        ropRequest.setMessage(requestDto.getMessage());

        ContactRequest saved = repository.save(ropRequest);
        return convertToResponse(saved); // Возвращаем DTO, а не Entity
    }

    // Возвращает все заявки
    public List<ContactRequestResponse> getAllRequests() {
        return repository.findAll()
                .stream()
                .map(this::convertToResponse) // Конвертируем каждую entity в DTO
                .collect(Collectors.toList());
    }

    public Optional<ContactRequestResponse> getRequestById(Long id) {
        return repository.findById(id)
                .map(this::convertToResponse); // Конвертируем если найдено
    }

    // Обновление статуса заявки
    public ContactRequestResponse updateRequestStatus(Long id, String status) {
        ContactRequest ropRequest = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заявка не найдена"));

        // Преобразуем строку в enum
        RequestStatus newStatus;
        try {
            newStatus = RequestStatus.valueOf(status.toUpperCase()); // valueOf() - ищет константу enum с таким именем
        } catch (IllegalArgumentException e) { // Обработка исключения, которое бросает valueOf()
            throw new InvalidStatusException("Некорректный статус: " + status); // Бросаем более понятное исключение
        }

        ropRequest.setStatus(newStatus);
        ContactRequest ropUpdated = repository.save(ropRequest);
        return convertToResponse(ropUpdated);
    }

    // Поиск заявок по email
    public List<ContactRequestResponse> findByEmail(String email) {
        return repository.findByEmail(email)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList()); // collect - преобразует поток в коллекцию
    }

    // Поиск заявок по статусу
    public List<ContactRequestResponse> findByStatus(String status) {
        RequestStatus requestStatus;
        try {
            requestStatus = RequestStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException("Некорректный статус: " + status);
        }

        return repository.findByStatus(requestStatus)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Поиск заявок по email и статусу
    public List<ContactRequestResponse> findByEmailAndStatus(String email, String status) {
        RequestStatus requestStatus;
        try {
            requestStatus = RequestStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException("Некорректный статус: " + status);
        }

        // findByEmailAndStatus - метод репозитория автогенерируется Spring Data JPA на остове названия метода
        // Он разбирает название метода и понимает, что это метод find (поиска) с параметрами email и status
        // и строит один sql запрос к БД с использованием этих параметров
        return repository.findByEmailAndStatus(email, requestStatus)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Парсинг даты в LocalDateTime
    private LocalDateTime parseDateTime(String dateString) {
        try {
            // Пробуем разные форматы
            return LocalDateTime.parse(dateString); // ISO format: 2023-10-25T10:30:00
        } catch (DateTimeParseException e) {
            try {
                // Формат только даты: 2023-10-25 (начинаем с 00:00 этого дня)
                LocalDate localDate = LocalDate.parse(dateString);
                return localDate.atStartOfDay(); // Преобразуем в LocalDateTime с 00:00
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException("Некорректный формат даты. Используйте: YYYY-MM-DD или YYYY-MM-DDTHH:MM:SS");
            }
        }
    }

    // Поиск заявок по дате создания запроса после указанной даты
    public List<ContactRequestResponse> findByCreatedAtAfter(String dateString) {
        LocalDateTime date = parseDateTime(dateString);
        return repository.findByCreatedAtAfter(date)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
}
