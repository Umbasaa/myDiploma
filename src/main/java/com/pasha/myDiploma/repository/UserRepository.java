package com.pasha.myDiploma.repository;


import com.pasha.myDiploma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository. Создаем интерфейс для пользователя в пакете repository и наследуем JpaRepository<User, Long>, указываем класс User и тип его id — Long.
 * Просто создав интерфейс и унаследовав JpaRepository можно выполнять стандартные запросы к БД
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
