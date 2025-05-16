package ru.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Category;

import java.util.List;

public interface BeautyServiceRepository extends JpaRepository<BeautyService, Long> {
    List<BeautyService> findByCategory(Category category);

    List<BeautyService> findAllByCategoryIn(List<Category> categories);


}

