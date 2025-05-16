package ru.itis.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.entity.Category;
import ru.itis.repository.CategoryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public List<Category> getByIds(List<Long> ids) {
        return categoryRepository.findAllById(ids);
    }

}
