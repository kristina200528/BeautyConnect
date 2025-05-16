package ru.itis.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Category;
import ru.itis.repository.BeautyServiceRepository;
import ru.itis.repository.CategoryRepository;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(CategoryRepository categoryRepo, BeautyServiceRepository beautyServiceRepository) {
        return args -> {
            if (categoryRepo.count() > 0) return;

            Category hairCut = categoryRepo.save(new Category("Стрижки и укладки"));
            Category color = categoryRepo.save(new Category("Окрашивание и техники"));
            Category hairExtension = categoryRepo.save(new Category("Наращивание и уход за волосами"));
            Category beard = categoryRepo.save(new Category("Борода и усы"));
            Category nails = categoryRepo.save(new Category("Ногти и руки"));
            Category pedicure = categoryRepo.save(new Category("Педикюр и уход за ногами"));
            Category brows = categoryRepo.save(new Category("Брови и ресницы"));
            Category skinCare = categoryRepo.save(new Category("Уход за лицом"));
            Category spa = categoryRepo.save(new Category("Тело и SPA"));
            Category epilation = categoryRepo.save(new Category("Эпиляция"));
            Category makeup = categoryRepo.save(new Category("Макияж и татуаж"));

            if (beautyServiceRepository.count() == 0) {
                beautyServiceRepository.saveAll(List.of(
                        new BeautyService("Стрижка женская классическая", hairCut),
                        new BeautyService("Стрижка женская модельная", hairCut),
                        new BeautyService("Стрижка мужская классическая", hairCut),
                        new BeautyService("Стрижка мужская машинкой", hairCut),
                        new BeautyService("Детская стрижка", hairCut),
                        new BeautyService("Укладка феном", hairCut),
                        new BeautyService("Укладка с плойкой/утюжком", hairCut),
                        new BeautyService("Выпрямление волос (кератин)", hairCut),
                        new BeautyService("Ботокс для волос", hairCut),
                        new BeautyService("Лечение волос", hairCut),
                        new BeautyService("Нанопластика волос", hairCut),
                        new BeautyService("Ламинирование волос", hairCut),

                        // Окрашивание и техники
                        new BeautyService("Окрашивание корней", color),
                        new BeautyService("Полное окрашивание волос", color),
                        new BeautyService("Тонирование волос", color),
                        new BeautyService("Мелирование волос", color),
                        new BeautyService("AirTouch", color),

                        // Наращивание и уход за волосами
                        new BeautyService("Наращивание волос (капсульное)", hairExtension),
                        new BeautyService("Наращивание волос (ленточное)", hairExtension),
                        new BeautyService("Удлинение прядей микрокольцами", hairExtension),
                        new BeautyService("Экранирование волос", hairExtension),

                        // Борода и усы
                        new BeautyService("Коррекция бороды", beard),
                        new BeautyService("Стрижка бороды машинкой", beard),
                        new BeautyService("Укладка усов", beard),

                        // Ногти и руки
                        new BeautyService("Маникюр классический", nails),
                        new BeautyService("Маникюр аппаратный", nails),
                        new BeautyService("Маникюр комбинированный", nails),
                        new BeautyService("Покрытие гель-лак", nails),
                        new BeautyService("Наращивание ногтей гелем", nails),
                        new BeautyService("Коррекция ногтей", nails),
                        new BeautyService("Дизайн ногтей", nails),
                        new BeautyService("Снятие гель-лака/наращённых ногтей", nails),
                        new BeautyService("Spa-маникюр", nails),

                        // Педикюр и уход за ногами
                        new BeautyService("Педикюр классический", pedicure),
                        new BeautyService("Педикюр аппаратный", pedicure),
                        new BeautyService("Покрытие педикюр гель-лак", pedicure),
                        new BeautyService("Spa-педикюр", pedicure),
                        new BeautyService("Снятие покрытия педикюр", pedicure),

                        // Брови и ресницы
                        new BeautyService("Коррекция бровей пинцетом/воском", brows),
                        new BeautyService("Окрашивание бровей хной", brows),
                        new BeautyService("Ламинирование бровей", brows),
                        new BeautyService("Моделирование бровей воском", brows),
                        new BeautyService("Теневая растушёвка бровей", brows),
                        new BeautyService("Окрашивание ресниц краской", brows),
                        new BeautyService("Ламинирование ресниц", brows),
                        new BeautyService("Наращивание ресниц классика (1:1)", brows),
                        new BeautyService("Наращивание ресниц объём 2D–3D", brows),
                        new BeautyService("Лаш-лифтинг (биозавивка ресниц)", brows),

                        // Уход за лицом
                        new BeautyService("Чистка лица механическая", skinCare),
                        new BeautyService("Чистка лица ультразвуковая", skinCare),
                        new BeautyService("Чистка лица комбинированная", skinCare),
                        new BeautyService("Пилинг (AHA/BHA/ферментный)", skinCare),
                        new BeautyService("Микродермабразия", skinCare),
                        new BeautyService("Микронидлинг (дерма-роллер)", skinCare),
                        new BeautyService("LED-терапия лица", skinCare),
                        new BeautyService("Косметический массаж лица", skinCare),
                        new BeautyService("Увлажняющая маска", skinCare),
                        new BeautyService("Антивозрастная сыворотка", skinCare),
                        new BeautyService("Ботокс-маска", skinCare),

                        // Тело и SPA
                        new BeautyService("Классический массаж тела", spa),
                        new BeautyService("Антицеллюлитный массаж", spa),
                        new BeautyService("Тайский массаж", spa),
                        new BeautyService("Стоун-массаж (горячие камни)", spa),
                        new BeautyService("Аромамассаж", spa),
                        new BeautyService("Обёртывание глиной", spa),
                        new BeautyService("Обёртывание ламинарией", spa),
                        new BeautyService("Корректирующее обёртывание", spa),
                        new BeautyService("Скраб тела", spa),
                        new BeautyService("SPA-программа «Райское блаженство»", spa),

                        // Эпиляция
                        new BeautyService("Шугаринг ног полностью", epilation),
                        new BeautyService("Электроэпиляция", epilation),
                        new BeautyService("Восковая эпиляция ног", epilation),
                        new BeautyService("Восковая эпиляция подмышки", epilation),
                        new BeautyService("Восковая эпиляция рук", epilation),
                        new BeautyService("Лазерная эпиляция (мини-курс)", epilation),

                        // Макияж и татуаж
                        new BeautyService("Дневной макияж", makeup),
                        new BeautyService("Вечерний макияж", makeup),
                        new BeautyService("Свадебный макияж", makeup),
                        new BeautyService("Перманентный макияж бровей", makeup),
                        new BeautyService("Перманентный макияж губ", makeup),
                        new BeautyService("Перманентный макияж стрелок", makeup)
                ));
            }
        };
    }
}

