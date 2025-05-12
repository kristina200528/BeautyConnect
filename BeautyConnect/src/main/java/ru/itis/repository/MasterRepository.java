package ru.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.entity.Master;

import java.util.List;
import java.util.Optional;

public interface MasterRepository extends JpaRepository<Master, Long> {
    Optional<Master> findByUserId(Long userId);

    @Query("SELECT m FROM Master m JOIN FETCH m.user")
    List<Master> findAllWithUser();

    @Query("SELECT m FROM Master m JOIN FETCH m.user WHERE m.id = :id")
    Optional<Master> findByIdWithUser(Long id);
}
