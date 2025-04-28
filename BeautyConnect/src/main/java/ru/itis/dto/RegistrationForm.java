package ru.itis.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.itis.dictonary.UserRole;


@Data
@Accessors(chain = true)
public class RegistrationForm {

    @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
    @NotBlank(message = "Имя не может быть пустым")
    private String firstName;

    @Size(min = 2, max = 50, message = "Фамилия должна содержать от 2 до 50 символов")
    @NotBlank(message = "Фамилия не может быть пустой")
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Имя пользователя может содержать только буквы, цифры и подчеркивания")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @Min(16)
    private Integer age;

    @Email(message = "Некорректный формат email")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @Size(min = 8, max = 20, message = "Пароль должен содержать от 8 до 20 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @NotBlank
    private String confirmPassword;
    private UserRole role;

    private String specialization;
    private Integer experience;
    private String location;
    private String contacts;

}