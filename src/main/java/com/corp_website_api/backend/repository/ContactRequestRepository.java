package com.corp_website_api.backend.repository;

import com.corp_website_api.backend.entity.ContactRequest;
import com.corp_website_api.backend.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository // Помечает интерфейс как репозиторий
// Интерфейс наследуем от JpaRepository и передаем как параметры тип сущности и тип первичного ключа
// Регистрирует класс в Spring-контейнере, позволяет использовать @Autowired в других классах, чтобы получить экземпляр этого сервиса из контейнера
public interface ContactRequestRepository extends JpaRepository<ContactRequest, Long> {
    // Spring Data JPA автоматически создаст CRUD методы

    List<ContactRequest> findByEmail(String email); // Поиск по email

    List<ContactRequest> findByStatus(RequestStatus status); // Поиск по статусу status); // Поиск по статусу

    List<ContactRequest> findByEmailAndStatus(String email, RequestStatus status); // Поиск по email и статусу

    List<ContactRequest> findByCreatedAtAfter(LocalDateTime date); // Поиск по дате создания запроса после указанной даты
}
