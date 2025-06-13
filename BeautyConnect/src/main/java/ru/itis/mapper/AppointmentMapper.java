package ru.itis.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.dto.AppointmentForm;
import ru.itis.entity.Appointment;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Master;
import ru.itis.entity.User;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", source = "client")
    @Mapping(target = "master", source = "master")
    @Mapping(target = "service", source = "service")
    @Mapping(target = "status", constant = "CREATED")
    Appointment mapToEntity(AppointmentForm form, BeautyService service, User client, Master master);

    @Mapping(target = "serviceId", source = "service.id")
    AppointmentForm mapToForm(Appointment appointment);
}

