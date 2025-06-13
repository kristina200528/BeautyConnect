package ru.itis.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.AbstractBeautyConnectTest;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Category;
import ru.itis.repository.BeautyServiceRepository;
import ru.itis.repository.CategoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BeautyServiceServiceTest extends AbstractBeautyConnectTest {

    @Autowired
    private BeautyServiceService beautyServiceService;

    @Autowired
    private BeautyServiceRepository beautyServiceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void cleanUp() {
        beautyServiceRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void getAllBeautyService_ReturnAll() {
        Category category = new Category();
        category.setName("TestCategory");
        categoryRepository.save(category);

        BeautyService service1 = new BeautyService();
        service1.setName("TestBeautyService1");
        service1.setCategory(category);
        beautyServiceRepository.save(service1);

        BeautyService service2 = new BeautyService();
        service2.setName("TestBeautyService2");
        service2.setCategory(category);
        beautyServiceRepository.save(service2);

        List<BeautyService> services = beautyServiceService.getAllBeautyService();

        assertNotNull(services);
        assertEquals(2, services.size());
    }

    @Test
    @Transactional
    void getById_ReturnCorrectService() {
        Category category = new Category();
        category.setName("TestCategory");
        categoryRepository.save(category);

        BeautyService service = new BeautyService();
        service.setName("TestBeautyService");
        service.setCategory(category);
        beautyServiceRepository.save(service);

        BeautyService found = beautyServiceService.getById(service.getId());

        assertNotNull(found);
        assertEquals(service.getName(), found.getName());
    }

    @Test
    void getAllByCategories_ReturnServicesInCategories() {
        Category cat1 = new Category();
        cat1.setName("TestCategory1");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setName("TestCategory2");
        categoryRepository.save(cat2);

        BeautyService service1 = new BeautyService();
        service1.setName("TestBeautyService1");
        service1.setCategory(cat1);
        beautyServiceRepository.save(service1);

        BeautyService service2 = new BeautyService();
        service2.setName("TestBeautyService2");
        service2.setCategory(cat2);
        beautyServiceRepository.save(service2);

        List<BeautyService> services = beautyServiceService.getAllByCategories(List.of(cat1));

        assertNotNull(services);
        assertEquals(1, services.size());
        assertEquals("TestBeautyService1", services.get(0).getName());

        // Проверка с пустым списком категорий
        List<BeautyService> emptyResult = beautyServiceService.getAllByCategories(List.of());
        assertTrue(emptyResult.isEmpty());
    }
}
