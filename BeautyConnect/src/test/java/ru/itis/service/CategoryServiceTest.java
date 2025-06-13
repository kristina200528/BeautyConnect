package ru.itis.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.itis.entity.Category;
import ru.itis.repository.CategoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    public CategoryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ReturnsCategories() {
        List<Category> categories = List.of(new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAll();

        assertEquals(2, result.size());
        verify(categoryRepository).findAll();
    }

    @Test
    void getByIds_ReturnsCategories() {
        List<Long> ids = List.of(1L, 2L);
        List<Category> categories = List.of(new Category(), new Category());
        when(categoryRepository.findAllById(ids)).thenReturn(categories);

        List<Category> result = categoryService.getByIds(ids);

        assertEquals(2, result.size());
        verify(categoryRepository).findAllById(ids);
    }
}

