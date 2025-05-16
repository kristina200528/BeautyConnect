package ru.itis.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itis.entity.BeautyService;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ServiceOfferingDto {

    private Long id;

    @NotNull(message = "Услуга не может быть пустой")
    private Long beautyServiceId;

    @NotNull(message = "Цена не может быть пустой")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена должна быть положительной")
    private BigDecimal price;

    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    private String description;

    private Integer durationMinutes;
}
