package ru.itis.enums;

public enum AppointmentStatus {
    CREATED,    // Запись создана, ожидает подтверждения
    CONFIRMED,  // Запись подтверждена мастером
    CANCELLED,  // Запись отменена
    COMPLETED   // Запись выполнена
}

