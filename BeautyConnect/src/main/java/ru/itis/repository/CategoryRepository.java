package ru.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}

