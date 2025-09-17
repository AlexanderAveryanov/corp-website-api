package com.corp_website_api.backend.service;

import com.corp_website_api.backend.entity.ContactRequest;
import com.corp_website_api.backend.repository.ContactRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Cервис слой для бизнес-логики
// Эта аннотация делает класс Spring-сервисом
// Регистрирует класс в Spring-контейнере, позволяет использовать @Autowired в других классах
@Service
public class ContactRequestService {

    @Autowired // Автоматически внедряет репозиторий
    private ContactRequestRepository repository;

    public ContactRequest createRequest(ContactRequest request) {
        return repository.save(request);
    }

    public List<ContactRequest> getAllRequests() {
        return repository.findAll();
    }

    public Optional<ContactRequest> getRequestById(Long id) {
        return repository.findById(id);
    }

//    public ContactRequest updateRequestStatus(Long id, String status) {
//        ContactRequest request = getRequestById(id);
//        request.setStatus(ContactRequest.RequestStatus.valueOf(status.toUpperCase()));
//    }
}
