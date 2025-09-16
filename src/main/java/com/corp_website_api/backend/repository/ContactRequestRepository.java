package com.corp_website_api.backend.repository;

import com.corp_website_api.backend.entity.ContactRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Помечает интерфейс как репозиторий
// Интерфейс наследуем от JpaRepository и передаем как параметры тип сущности и тип первичного ключа
public interface ContactRequestRepository extends JpaRepository<ContactRequest, Long> {
    // Spring Data JPA автоматически создаст CRUD методы
}
