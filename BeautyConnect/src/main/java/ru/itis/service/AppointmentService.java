package ru.itis.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dto.AppointmentDto;
import ru.itis.dto.AppointmentForm;
import ru.itis.entity.*;
import ru.itis.enums.AppointmentStatus;
import ru.itis.exception.AppointmentNotFoundException;
import ru.itis.exception.MasterNotFoundException;
import ru.itis.exception.ServiceOfferingNotFoundException;
import ru.itis.exception.UserNotFoundException;
import ru.itis.mapper.AppointmentMapper;
import ru.itis.repository.AppointmentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final MasterService masterService;
    private final ServiceOfferingService serviceOfferingService;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserService userService;

    @Transactional
    public void createAppointment(Long masterId, AppointmentForm appointmentForm, Long userId) {
        Master master = masterService.findById(masterId);
        User client = userService.findById(userId).orElseThrow(()->new UserNotFoundException("User nor found"));
        ServiceOffering service = serviceOfferingService.findById(appointmentForm.getServiceId());
        Appointment appointment = appointmentMapper.mapToEntity(appointmentForm, service.getBeautyService(), client, master);
        save(appointment);
    }

    public List<Appointment> getAppointmentsForMaster(Long masterId) {
        return appointmentRepository.findAllByMasterIdWithClientAndService(masterId);
    }

    public List<AppointmentDto> getAppointmentsForClient(Long clientId) {
        List<Appointment> appointments = appointmentRepository.findAllByClientIdWithMasterAndService(clientId);
        return appointments.stream().map(appointment -> AppointmentDto.builder()
                .id(appointment.getId())
                .masterFirstName(appointment.getMaster().getUser().getFirstName())
                .masterLastName(appointment.getMaster().getUser().getLastName())
                .serviceName(appointment.getService().getName())
                .dateTime(appointment.getDateTime())
                .status(appointment.getStatus().name())
                .build()
        ).toList();
    }

    public Appointment findById(Long id) {
        return appointmentRepository.getReferenceById(id);
    }

    @Transactional
    public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public boolean cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            return false;
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
        return true;
    }

    public boolean isUserOwnerAppointment(String username, Long appointmentId) {
        Appointment appointment = findById(appointmentId);
        return appointment.getClient().getUsername().equalsIgnoreCase(username);
    }

    public boolean isMasterOwnerAppointment(String username, Long appointmentId) {
        Appointment appointment = findById(appointmentId);
        return appointment.getMaster().getUser().getUsername().equalsIgnoreCase(username);
    }

    public List<BeautyService> getMostPopularServices() {
        return appointmentRepository.findMostPopularServicesLast30Days();
    }


}

