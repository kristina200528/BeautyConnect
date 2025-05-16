package ru.itis.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dictonary.UserStatus;
import ru.itis.entity.User;
import ru.itis.exception.UserAlreadyExistException;
import ru.itis.exception.UserNotFoundException;
import ru.itis.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByOrderByIdAsc();
    }

    @Transactional
    public void blockUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        User user = userOptional.get();
        user.setStatus(UserStatus.BLOCKED);
        userRepository.save(user);
    }

    @Transactional
    public void unblockUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        User user = userOptional.get();
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }
}
