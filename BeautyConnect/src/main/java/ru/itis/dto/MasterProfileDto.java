package ru.itis.dto;

import jakarta.persistence.Column;
import lombok.*;
import ru.itis.entity.Category;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MasterProfileDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;

    //private String specialization;
    private List<Category> specialization;
    private Integer experience;
    private String location;
    private String contacts;
}

