package com.corp_website_api.backend.service;

import com.corp_website_api.backend.dto.ContactRequestResponse;
import com.corp_website_api.backend.dto.CreateContactRequest;
import com.corp_website_api.backend.entity.ContactRequest;
import com.corp_website_api.backend.repository.ContactRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private ContactRequestResponse convertToResponse(ContactRequest entity) {
        ContactRequestResponse response = new ContactRequestResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setEmail(entity.getEmail());
        response.setPhone(entity.getPhone());
        response.setMessage(entity.getMessage());
        response.setStatus(entity.getStatus());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }

    public ContactRequestResponse createRequest(CreateContactRequest requestDto) {
        ContactRequest request = new ContactRequest();
        request.setName(requestDto.getName());
        request.setEmail(requestDto.getEmail());
        request.setPhone(requestDto.getPhone());
        request.setMessage(requestDto.getMessage());

        ContactRequest saved = repository.save(request);
        return convertToResponse(saved); // Возвращаем DTO, а не Entity
    }

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

//    public ContactRequest updateRequestStatus(Long id, String status) {
//        ContactRequest request = getRequestById(id);
//        request.setStatus(ContactRequest.RequestStatus.valueOf(status.toUpperCase()));
//    }
}
