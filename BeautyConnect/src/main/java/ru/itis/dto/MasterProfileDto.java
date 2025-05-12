package ru.itis.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MasterProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;

    private String specialization;
    private Integer experience;
    private String location;
    private String contacts;
}

