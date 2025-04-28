package ru.itis.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dictonary.UserRole;
import ru.itis.entity.Master;
import ru.itis.entity.User;
import ru.itis.exception.UserAlreadyExistException;
import ru.itis.mapper.UserMapper;
import ru.itis.repository.MasterRepository;
import ru.itis.repository.UserRepository;
import ru.itis.dto.RegistrationForm;


@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final MasterRepository masterRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(RegistrationForm form) {
        userRepository.findByUsername(form.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistException("User already exist");
                });

        userRepository.findByEmail(form.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistException("User already exist");
                });
        String encodedPassword = passwordEncoder.encode(form.getPassword());

        User user = userMapper.mapToUser(form, encodedPassword);
        User savedUser = userRepository.save(user);

        if (form.getRole() == UserRole.MASTER) {
            Master master = userMapper.mapToMaster(form, savedUser);
            masterRepository.save(master);
        }

    }

}
