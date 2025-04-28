package ru.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.entity.Master;

import java.util.Optional;

public interface MasterRepository extends JpaRepository<Master, Long> {
    Optional<Master> findByUserId(Long userId);
}
