package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IReservaRepository extends JpaRepository<Reserva, Integer> {
    @Query("SELECT r FROM Reserva r WHERE " +
            "(:idUsuario IS NULL OR r.idUsuario = :idUsuario) AND " +
            "(:idHotel IS NULL OR r.idHotel = :idHotel) AND " +
            "(:total IS NULL OR r.total > :total) " +
            "ORDER BY r.id DESC")
    Page<Reserva> findPaginated(
            Integer idUsuario,
            Integer idHotel,
            BigDecimal total,
            Pageable pageable
    );

    @Query("SELECT r FROM Reserva r JOIN TipoHabitacion th ON r.idTipoHabitacion = th.id JOIN Hotel h ON r.idHotel = h.id WHERE h.idUsuario = :idUsuario " +
    "AND r.fechaRealizado >= :fechaRealizado")
    List<Reserva> findAllReservasByPropietarioDelHotel(
            @Param("idUsuario") Integer idUsuario,
            @Param("fechaRealizado") LocalDateTime fechaRealizado);

    List<Reserva> findByIdHotel(Integer idHotel);
}
