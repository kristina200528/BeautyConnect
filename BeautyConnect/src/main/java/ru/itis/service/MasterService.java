package ru.itis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dto.MasterProfileDto;
import ru.itis.entity.Appointment;
import ru.itis.entity.Master;
import ru.itis.entity.User;
import ru.itis.exception.MasterNotFoundException;
import ru.itis.mapper.MasterMapper;
import ru.itis.repository.MasterRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MasterService {
    private final MasterRepository masterRepository;
    private final MasterMapper masterMapper;

    public Master findMasterByUserId(Long userId) {
        return masterRepository.findByUserId(userId).orElseThrow(()->new MasterNotFoundException("Master not found"));
    }

    public MasterProfileDto getMasterById(Long id) {
        Master master = masterRepository.findByIdWithUser(id)
                .orElseThrow(() -> new IllegalArgumentException("Мастер не найден"));
        return masterMapper.toDto(master);
    }

    public List<Master> getAllMasters(){
        return masterRepository.findAllWithUser();
    }

    public boolean isMasterOwner(String username, Long masterId) {
        MasterProfileDto master=getMasterById(masterId);
        return master.getUsername().equals(username);
    }
    public Master findById(Long id){
        return masterRepository.findById(id).orElseThrow(()->new MasterNotFoundException("Master not found"));
    }

    public boolean isNotCurrentMaster(String username, Long masterId) {
        Master master = findById(masterId);
        return !master.getUser().getUsername().equalsIgnoreCase(username);
    }

}
