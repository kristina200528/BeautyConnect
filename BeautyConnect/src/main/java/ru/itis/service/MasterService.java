package ru.itis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.entity.Master;
import ru.itis.repository.MasterRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MasterService {
    private final MasterRepository masterRepository;
    public Optional<Master> findMasterByUserId(Long userId) {
        return masterRepository.findByUserId(userId);
    }

}
