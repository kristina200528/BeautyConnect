package ru.itis.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.itis.dto.MasterProfileDto;
import ru.itis.entity.Master;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface MasterMapper {

    @Mappings({
            @Mapping(source = "user.id", target = "id"),
            @Mapping(source = "user.firstName", target = "firstName"),
            @Mapping(source = "user.username", target = "username"),
            @Mapping(source = "user.lastName", target = "lastName"),
            @Mapping(source = "user.email", target = "email"),
            @Mapping(source = "user.age", target = "age"),
            @Mapping(source = "specialization", target = "specialization"),
            @Mapping(source = "experience", target = "experience"),
            @Mapping(source = "location", target = "location"),
            @Mapping(source = "contacts", target = "contacts")
    })
    MasterProfileDto toDto(Master master);
}
