package ru.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.entity.ServiceOffering;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {

    List<ServiceOffering> findByMasterId(Long masterId);
    Optional<ServiceOffering> findByIdAndMasterId(Long serviceId, Long masterId);

}
