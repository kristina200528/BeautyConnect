package ru.itis.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.dto.RegistrationForm;
import ru.itis.entity.Category;
import ru.itis.entity.Master;
import ru.itis.entity.User;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role")
    @Mapping(target = "status", constant = "ACTIVE")
    User mapToUser(RegistrationForm form, String hashPassword);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "specialization", source = "specializations")
    @Mapping(target = "experience", source = "form.experience")
    @Mapping(target = "location", source = "form.location")
    @Mapping(target = "contacts", source = "form.contacts")
    Master mapToMaster(RegistrationForm form, User user, List<Category> specializations);
}
