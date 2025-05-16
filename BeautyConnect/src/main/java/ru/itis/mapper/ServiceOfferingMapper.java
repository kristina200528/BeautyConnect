package ru.itis.mapper;

import org.mapstruct.*;
import ru.itis.dto.ServiceOfferingDto;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Master;
import ru.itis.entity.ServiceOffering;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceOfferingMapper {

    @Mapping(source = "beautyService.id", target = "beautyServiceId")
    ServiceOfferingDto mapToDto(ServiceOffering serviceOffering);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "master", source = "master")
    @Mapping(target = "beautyService", source = "beautyService")
    ServiceOffering mapToEntity(ServiceOfferingDto dto, Master master, BeautyService beautyService);

    List<ServiceOfferingDto> mapToDtoList(List<ServiceOffering> serviceOfferings);
}
