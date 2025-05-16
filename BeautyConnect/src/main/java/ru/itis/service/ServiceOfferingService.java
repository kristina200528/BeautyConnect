package ru.itis.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dto.ServiceOfferingDto;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Master;
import ru.itis.entity.ServiceOffering;
import ru.itis.exception.BeautyServiceNotFoundException;
import ru.itis.exception.UserNotFoundException;
import ru.itis.mapper.ServiceOfferingMapper;
import ru.itis.repository.BeautyServiceRepository;
import ru.itis.repository.MasterRepository;
import ru.itis.repository.ServiceOfferingRepository;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final MasterRepository masterRepository;
    private final ServiceOfferingMapper serviceOfferingMapper;
    private final BeautyServiceRepository beautyServiceRepository;

    @Transactional
    public List<ServiceOfferingDto> getServicesByMasterId(Long masterId) {
        if (!masterRepository.existsById(masterId)) {
            throw new UserNotFoundException("Master not found with id: " + masterId);
        }
        List<ServiceOffering> services = serviceOfferingRepository.findByMasterId(masterId);
        return serviceOfferingMapper.mapToDtoList(services);
    }

    @Transactional
    public ServiceOfferingDto addService(Long masterId, ServiceOfferingDto serviceOfferingDto, String currentUsername) throws AccessDeniedException {
        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new UserNotFoundException("Master not found with id: " + masterId));
        checkMasterOwnership(master, currentUsername);

        BeautyService beautyService = beautyServiceRepository.findById(serviceOfferingDto.getBeautyServiceId())
                .orElseThrow(() -> new BeautyServiceNotFoundException("BeautyService not found"));

        ServiceOffering newService = serviceOfferingMapper.mapToEntity(serviceOfferingDto, master, beautyService);

        ServiceOffering savedService = serviceOfferingRepository.save(newService);
        return serviceOfferingMapper.mapToDto(savedService);
    }

    private void checkMasterOwnership(Master master, String currentUsername) throws AccessDeniedException {
        if (!master.getUser().getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("User does not have permission to modify this master profile.");
        }
    }
}
