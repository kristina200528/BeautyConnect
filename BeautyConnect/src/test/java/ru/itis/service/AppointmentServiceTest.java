package ru.itis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.itis.dto.AppointmentDto;
import ru.itis.dto.AppointmentForm;
import ru.itis.entity.*;
import ru.itis.enums.AppointmentStatus;
import ru.itis.exception.AppointmentNotFoundException;
import ru.itis.exception.UserNotFoundException;
import ru.itis.mapper.AppointmentMapper;
import ru.itis.repository.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private MasterService masterService;

    @Mock
    private ServiceOfferingService serviceOfferingService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAppointment_Success() {
        Long masterId = 1L;
        Long userId = 2L;
        AppointmentForm form = new AppointmentForm();
        form.setServiceId(3L);

        Master master = new Master();
        User user = new User();
        ServiceOffering serviceOffering = new ServiceOffering();
        BeautyService beautyService = new BeautyService();
        serviceOffering.setBeautyService(beautyService);

        Appointment appointment = new Appointment();

        when(masterService.findById(masterId)).thenReturn(master);
        when(userService.findById(userId)).thenReturn(Optional.of(user));
        when(serviceOfferingService.findById(form.getServiceId())).thenReturn(serviceOffering);
        when(appointmentMapper.mapToEntity(form, beautyService, user, master)).thenReturn(appointment);

        appointmentService.createAppointment(masterId, form, userId);

        verify(appointmentRepository).save(appointment);
    }

    @Test
    void createAppointment_UserNotFound_ThrowsException() {
        Long masterId = 1L;
        Long userId = 2L;
        AppointmentForm form = new AppointmentForm();
        form.setServiceId(3L);

        when(masterService.findById(masterId)).thenReturn(new Master());
        when(userService.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> appointmentService.createAppointment(masterId, form, userId));
    }

    @Test
    void getAppointmentsForMaster_ReturnsList() {
        Long masterId = 1L;
        List<Appointment> appointments = List.of(new Appointment(), new Appointment());

        when(appointmentRepository.findAllByMasterIdWithClientAndService(masterId)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsForMaster(masterId);

        assertEquals(2, result.size());
        verify(appointmentRepository).findAllByMasterIdWithClientAndService(masterId);
    }

    @Test
    void getAppointmentsForClient_ReturnsDtos() {
        Long clientId = 1L;

        User client = new User();
        client.setUsername("client");

        User masterUser = new User();
        masterUser.setFirstName("TestFirstName");
        masterUser.setLastName("TestLastName");

        Master master = new Master();
        master.setUser(masterUser);

        BeautyService service = new BeautyService();
        service.setName("ServiceName");

        Appointment appointment = new Appointment();
        appointment.setId(5L);
        appointment.setMaster(master);
        appointment.setService(service);
        appointment.setDateTime(LocalDateTime.now());
        appointment.setStatus(AppointmentStatus.CONFIRMED);

        List<Appointment> appointments = List.of(appointment);

        when(appointmentRepository.findAllByClientIdWithMasterAndService(clientId)).thenReturn(appointments);

        List<AppointmentDto> dtos = appointmentService.getAppointmentsForClient(clientId);

        assertEquals(1, dtos.size());
        assertEquals("TestFirstName", dtos.get(0).getMasterFirstName());
        assertEquals("TestLastName", dtos.get(0).getMasterLastName());
        assertEquals("ServiceName", dtos.get(0).getServiceName());
        assertEquals(appointment.getId(), dtos.get(0).getId());
    }

    @Test
    void cancelAppointment_Success() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setStatus(AppointmentStatus.CONFIRMED);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        boolean result = appointmentService.cancelAppointment(appointmentId);

        assertTrue(result);
        assertEquals(AppointmentStatus.CANCELLED, appointment.getStatus());
        verify(appointmentRepository).save(appointment);
    }

    @Test
    void cancelAppointment_AlreadyCancelled_ReturnsFalse() {
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setStatus(AppointmentStatus.CANCELLED);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        boolean result = appointmentService.cancelAppointment(appointmentId);

        assertFalse(result);
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void cancelAppointment_NotFound_ThrowsException() {
        Long appointmentId = 1L;

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.cancelAppointment(appointmentId));
    }

    @Test
    void isUserOwnerAppointment_ReturnsTrue() {
        Long appointmentId = 1L;
        String username = "user";

        User user = new User();
        user.setUsername(username);

        Appointment appointment = new Appointment();
        appointment.setClient(user);

        when(appointmentRepository.getReferenceById(appointmentId)).thenReturn(appointment);

        boolean result = appointmentService.isUserOwnerAppointment(username, appointmentId);

        assertTrue(result);
    }

    @Test
    void isUserOwnerAppointment_ReturnsFalse() {
        Long appointmentId = 1L;
        String username = "user";

        User user = new User();
        user.setUsername("otherUser");

        Appointment appointment = new Appointment();
        appointment.setClient(user);

        when(appointmentRepository.getReferenceById(appointmentId)).thenReturn(appointment);

        boolean result = appointmentService.isUserOwnerAppointment(username, appointmentId);

        assertFalse(result);
    }

    @Test
    void isMasterOwnerAppointment_ReturnsTrue() {
        Long appointmentId = 1L;
        String username = "master";

        User user = new User();
        user.setUsername(username);

        Master master = new Master();
        master.setUser(user);

        Appointment appointment = new Appointment();
        appointment.setMaster(master);

        when(appointmentRepository.getReferenceById(appointmentId)).thenReturn(appointment);

        boolean result = appointmentService.isMasterOwnerAppointment(username, appointmentId);

        assertTrue(result);
    }

    @Test
    void isMasterOwnerAppointment_ReturnsFalse() {
        Long appointmentId = 1L;
        String username = "master";

        User user = new User();
        user.setUsername("otherMaster");

        Master master = new Master();
        master.setUser(user);

        Appointment appointment = new Appointment();
        appointment.setMaster(master);

        when(appointmentRepository.getReferenceById(appointmentId)).thenReturn(appointment);

        boolean result = appointmentService.isMasterOwnerAppointment(username, appointmentId);

        assertFalse(result);
    }

    @Test
    void getMostPopularServices_ReturnsList() {
        List<BeautyService> services = List.of(new BeautyService(), new BeautyService());

        when(appointmentRepository.findMostPopularServicesLast30Days()).thenReturn(services);

        List<BeautyService> result = appointmentService.getMostPopularServices();

        assertEquals(2, result.size());
    }
}
