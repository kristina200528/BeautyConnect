package ru.itis.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dto.ServiceOfferingDto;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Master;
import ru.itis.entity.ServiceOffering;
import ru.itis.exception.BeautyServiceNotFoundException;
import ru.itis.exception.ServiceOfferingNotFoundException;
import ru.itis.exception.UserNotFoundException;
import ru.itis.mapper.ServiceOfferingMapper;
import ru.itis.repository.BeautyServiceRepository;
import ru.itis.repository.MasterRepository;
import ru.itis.repository.ServiceOfferingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final MasterRepository masterRepository;
    private final ServiceOfferingMapper serviceOfferingMapper;
    private final BeautyServiceRepository beautyServiceRepository;

    //read
    @Transactional
    public List<ServiceOfferingDto> getServicesByMasterId(Long masterId) {
        if (!masterRepository.existsById(masterId)) {
            throw new UserNotFoundException("Master not found with id: " + masterId);
        }
        List<ServiceOffering> services = serviceOfferingRepository.findByMasterId(masterId);
        return serviceOfferingMapper.mapToDtoList(services);
    }

    //save
    @Transactional
    public ServiceOfferingDto addService(Long masterId, ServiceOfferingDto serviceOfferingDto, String currentUsername) {
        Master master = masterRepository.findById(masterId).orElseThrow(() -> new UserNotFoundException("Master not found with id: " + masterId));
        BeautyService beautyService = beautyServiceRepository.findById(serviceOfferingDto.getBeautyServiceId()).orElseThrow(() -> new BeautyServiceNotFoundException("BeautyService not found"));
        ServiceOffering newService = serviceOfferingMapper.mapToEntity(serviceOfferingDto, master, beautyService);
        ServiceOffering savedService = serviceOfferingRepository.save(newService);
        return serviceOfferingMapper.mapToDto(savedService);
    }

    //read
    public ServiceOffering findById(Long id) {
        return serviceOfferingRepository.findById(id).orElseThrow(() -> new ServiceOfferingNotFoundException("Service offering not found"));
    }

    //update
    @Transactional
    public ServiceOffering updateService(Long id, ServiceOfferingDto dto) {
        ServiceOffering existingService = serviceOfferingRepository.findById(id)
                .orElseThrow(() -> new ServiceOfferingNotFoundException("Service offering not found"));

        BeautyService beautyService = beautyServiceRepository.findById(dto.getBeautyServiceId())
                .orElseThrow(() -> new BeautyServiceNotFoundException("BeautyService not found"));

        existingService.setBeautyService(beautyService);
        existingService.setPrice(dto.getPrice());
        existingService.setDescription(dto.getDescription());
        existingService.setDurationMinutes(dto.getDurationMinutes());

        return serviceOfferingRepository.save(existingService);
    }

    //delete
    @Transactional
    public void deleteService(Long id) {
        if (!serviceOfferingRepository.existsById(id)) {
            throw new ServiceOfferingNotFoundException("Service offering not found");
        }
        serviceOfferingRepository.deleteById(id);
    }

    //read
    @Transactional
    public ServiceOfferingDto getServiceByIdAndMasterId(Long serviceId, Long masterId) {
        ServiceOffering service = serviceOfferingRepository.findByIdAndMasterId(serviceId, masterId).orElseThrow(() -> new ServiceOfferingNotFoundException("Service offering not found with id " + serviceId + " and masterId " + masterId));
        return serviceOfferingMapper.mapToDto(service);
    }


}
