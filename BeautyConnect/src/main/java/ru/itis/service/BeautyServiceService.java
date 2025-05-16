package ru.itis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Category;
import ru.itis.repository.BeautyServiceRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeautyServiceService {
    private final BeautyServiceRepository beautyServiceRepository;

    public List<BeautyService> getAllBeautyService() {
        return beautyServiceRepository.findAll();
    }

    public BeautyService getById(Long id) {
        return beautyServiceRepository.getReferenceById(id);
    }
    public List<BeautyService> getAllByCategories(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return Collections.emptyList();
        }
        return beautyServiceRepository.findAllByCategoryIn(categories);
    }

}
