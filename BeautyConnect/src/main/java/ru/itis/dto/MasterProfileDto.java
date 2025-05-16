package ru.itis.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.itis.entity.Category;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
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

