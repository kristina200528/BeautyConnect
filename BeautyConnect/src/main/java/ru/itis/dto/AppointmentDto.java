package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AppointmentDto {
    private Long id;
    private String masterFirstName;
    private String masterLastName;
    private String serviceName;
    private LocalDateTime dateTime;
    private String status;
}
