package ru.itis.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AppointmentForm {
    @NotNull(message = "Дата и время записи обязательны")
    @Future(message = "Дата и время должны быть в будущем")
    private LocalDateTime dateTime;

    private Long serviceId;

    public String getDateTimeFormatted() {
        if (dateTime == null) return "";
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }
}
