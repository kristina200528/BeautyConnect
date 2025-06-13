package ru.itis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.itis.entity.User;
import ru.itis.enums.UserStatus;
import ru.itis.exception.UserNotFoundException;
import ru.itis.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByEmail_ReturnsOptionalUser() {
        User user = new User();
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail("test@test.com");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void blockUser_UserExists_ChangesStatus() {
        Long userId = 1L;
        User user = new User();
        user.setStatus(UserStatus.ACTIVE);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.blockUser(userId);

        assertEquals(UserStatus.BLOCKED, user.getStatus());
        verify(userRepository).save(user);
    }

    @Test
    void blockUser_UserNotFound_ThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.blockUser(1L));
    }
}

