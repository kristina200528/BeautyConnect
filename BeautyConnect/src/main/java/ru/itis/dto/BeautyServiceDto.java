package ru.itis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BeautyServiceDto {
    private Long id;
    private String name;
    private CategoryDto category;
}