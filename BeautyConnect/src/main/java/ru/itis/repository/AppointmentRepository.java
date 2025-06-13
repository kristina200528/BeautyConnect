package ru.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.entity.Appointment;
import ru.itis.entity.BeautyService;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment> findAllByMasterId(Long id);

    @Query("SELECT a FROM Appointment a " +
            "JOIN FETCH a.client " +
            "JOIN FETCH a.service " +
            "WHERE a.master.id = :masterId")
    List<Appointment> findAllByMasterIdWithClientAndService(@Param("masterId") Long masterId);


    @Query("SELECT a FROM Appointment a " +
            "JOIN FETCH a.master m " +
            "JOIN FETCH m.user " +
            "JOIN FETCH a.service s " +
            "WHERE a.client.id = :clientId")
    List<Appointment> findAllByClientIdWithMasterAndService(@Param("clientId") Long clientId);


    //Находит услуги, которые за последние 30 дней чаще всего бронировались другими клиентами, кроме самого клиента
    @Query(value = """
    SELECT bs.*
    FROM beauty_services bs
    WHERE bs.id IN (
        SELECT a.service_id
        FROM appointments a
        WHERE a.date_time >= CURRENT_DATE - INTERVAL '30 days'
        GROUP BY a.service_id
        HAVING COUNT(*) = (
            SELECT MAX(cnt)
            FROM (
                SELECT COUNT(*) AS cnt
                FROM appointments
                WHERE date_time >= CURRENT_DATE - INTERVAL '30 days'
                GROUP BY service_id
            ) AS counts
        )
    )
    """, nativeQuery = true)
    List<BeautyService> findMostPopularServicesLast30Days();


}
