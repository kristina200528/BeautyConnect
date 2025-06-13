package ru.itis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.itis.dto.ServiceOfferingDto;
import ru.itis.entity.BeautyService;
import ru.itis.entity.Master;
import ru.itis.entity.ServiceOffering;
import ru.itis.exception.UserNotFoundException;
import ru.itis.mapper.ServiceOfferingMapper;
import ru.itis.repository.BeautyServiceRepository;
import ru.itis.repository.MasterRepository;
import ru.itis.repository.ServiceOfferingRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceOfferingServiceTest {

    @Mock
    private ServiceOfferingRepository serviceOfferingRepository;
    @Mock
    private MasterRepository masterRepository;
    @Mock
    private ServiceOfferingMapper serviceOfferingMapper;
    @Mock
    private BeautyServiceRepository beautyServiceRepository;

    @InjectMocks
    private ServiceOfferingService serviceOfferingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getServicesByMasterId_MasterExists_ReturnsDtos() {
        Long masterId = 1L;
        when(masterRepository.existsById(masterId)).thenReturn(true);

        List<ServiceOffering> offerings = List.of(new ServiceOffering());
        when(serviceOfferingRepository.findByMasterId(masterId)).thenReturn(offerings);

        List<ServiceOfferingDto> dtos = List.of(new ServiceOfferingDto());
        when(serviceOfferingMapper.mapToDtoList(offerings)).thenReturn(dtos);

        List<ServiceOfferingDto> result = serviceOfferingService.getServicesByMasterId(masterId);

        assertEquals(dtos, result);
        verify(serviceOfferingRepository).findByMasterId(masterId);
    }

    @Test
    void getServicesByMasterId_MasterNotFound_Throws() {
        when(masterRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> serviceOfferingService.getServicesByMasterId(1L));
    }

    @Test
    void addService_Success() {
        Long masterId = 1L;
        ServiceOfferingDto dto = new ServiceOfferingDto();
        dto.setBeautyServiceId(2L);

        Master master = new Master();
        BeautyService beautyService = new BeautyService();
        ServiceOffering newService = new ServiceOffering();
        ServiceOffering savedService = new ServiceOffering();
        ServiceOfferingDto savedDto = new ServiceOfferingDto();

        when(masterRepository.findById(masterId)).thenReturn(Optional.of(master));
        when(beautyServiceRepository.findById(dto.getBeautyServiceId())).thenReturn(Optional.of(beautyService));
        when(serviceOfferingMapper.mapToEntity(dto, master, beautyService)).thenReturn(newService);
        when(serviceOfferingRepository.save(newService)).thenReturn(savedService);
        when(serviceOfferingMapper.mapToDto(savedService)).thenReturn(savedDto);

        ServiceOfferingDto result = serviceOfferingService.addService(masterId, dto, "username");

        assertEquals(savedDto, result);
    }
}
