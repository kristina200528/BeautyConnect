package ru.itis.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dictonary.UserRole;
import ru.itis.entity.Category;
import ru.itis.entity.Master;
import ru.itis.entity.User;
import ru.itis.exception.UserAlreadyExistException;
import ru.itis.mapper.UserMapper;
import ru.itis.repository.MasterRepository;
import ru.itis.repository.UserRepository;
import ru.itis.dto.RegistrationForm;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final MasterRepository masterRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CategoryService categoryService;

    @Transactional
    public void createUser(RegistrationForm form) {
        userRepository.findByUsername(form.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistException("Пользователь с таким именем пользователя уже существует");
                });

        userRepository.findByEmail(form.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistException("Пользователь с таким email уже существует");
                });
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        User user = userMapper.mapToUser(form, encodedPassword);
        User savedUser = userRepository.save(user);

        if (form.getRole() == UserRole.MASTER) {
            List<Category> categories = categoryService.getByIds(form.getSpecializationsId());
            Master master = userMapper.mapToMaster(form, savedUser,categories);
            masterRepository.save(master);
        }

    }

}
