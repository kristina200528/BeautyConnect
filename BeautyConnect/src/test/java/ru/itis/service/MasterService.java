package ru.itis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.itis.dto.MasterProfileDto;
import ru.itis.entity.Master;
import ru.itis.exception.MasterNotFoundException;
import ru.itis.mapper.MasterMapper;
import ru.itis.repository.MasterRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MasterServiceTest {

    @Mock
    private MasterRepository masterRepository;

    @Mock
    private MasterMapper masterMapper;

    @InjectMocks
    private MasterService masterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findMasterByUserId_Found() {
        Master master = new Master();
        when(masterRepository.findByUserId(1L)).thenReturn(Optional.of(master));

        Master result = masterService.findMasterByUserId(1L);

        assertEquals(master, result);
    }

    @Test
    void findMasterByUserId_NotFound() {
        when(masterRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(MasterNotFoundException.class, () -> masterService.findMasterByUserId(1L));
    }

    @Test
    void getMasterById_Found() {
        Master master = new Master();
        MasterProfileDto dto = new MasterProfileDto();
        when(masterRepository.findByIdWithUser(1L)).thenReturn(Optional.of(master));
        when(masterMapper.toDto(master)).thenReturn(dto);

        MasterProfileDto result = masterService.getMasterById(1L);

        assertEquals(dto, result);
    }

    @Test
    void getMasterById_NotFound() {
        when(masterRepository.findByIdWithUser(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> masterService.getMasterById(1L));
    }

    @Test
    void isMasterOwner_ReturnsTrue() {
        MasterProfileDto dto = new MasterProfileDto();
        dto.setUsername("testUser");
        MasterService spyService = spy(masterService);
        doReturn(dto).when(spyService).getMasterById(1L);

        boolean result = spyService.isMasterOwner("testUser", 1L);

        assertTrue(result);
    }
}

