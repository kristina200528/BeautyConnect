package ru.itis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.itis.dto.RegistrationForm;
import ru.itis.entity.Category;
import ru.itis.entity.Master;
import ru.itis.entity.User;
import ru.itis.enums.UserRole;
import ru.itis.exception.UserAlreadyExistException;
import ru.itis.mapper.UserMapper;
import ru.itis.repository.MasterRepository;
import ru.itis.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MasterRepository masterRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_SuccessfulUserCreation() {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("testusername");
        form.setEmail("test@test.com");
        form.setPassword("testpassword");
        form.setRole(UserRole.CLIENT);

        when(userRepository.findByUsername("testusername")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("testpassword")).thenReturn("encodedTestPassword");
        User user = new User();
        when(userMapper.mapToUser(form, "encodedTestPassword")).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        assertDoesNotThrow(() -> registrationService.createUser(form));

        verify(userRepository).save(user);
        verify(masterRepository, never()).save(any());
    }

    @Test
    void createUser_ThrowsWhenUsernameExists() {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("existinguser");
        form.setEmail("test@test.com");

        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(new User()));

        UserAlreadyExistException exception = assertThrows(UserAlreadyExistException.class, () -> registrationService.createUser(form));
        assertEquals("Пользователь с таким именем пользователя уже существует", exception.getMessage());
    }

    @Test
    void createUser_ThrowsWhenEmailExists() {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("testusername");
        form.setEmail("existing@example.com");

        when(userRepository.findByUsername("testusername")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(new User()));

        UserAlreadyExistException exception = assertThrows(UserAlreadyExistException.class, () -> registrationService.createUser(form));
        assertEquals("Пользователь с таким email уже существует", exception.getMessage());
    }

    @Test
    void createUser_WithRoleMaster_CreatesMaster() {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("testusername");
        form.setEmail("test@test.com");
        form.setPassword("testpassword");
        form.setRole(UserRole.MASTER);
        form.setSpecializationsId(List.of(1L, 2L));

        when(userRepository.findByUsername("testusername")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("testpassword")).thenReturn("encodedTestPassword");
        User user = new User();
        when(userMapper.mapToUser(form, "encodedTestPassword")).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        List<Category> categories = List.of(new Category(), new Category());
        when(categoryService.getByIds(form.getSpecializationsId())).thenReturn(categories);

        Master master = new Master();
        when(userMapper.mapToMaster(form, user, categories)).thenReturn(master);

        registrationService.createUser(form);

        verify(masterRepository).save(master);
    }
}

