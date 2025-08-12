package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IReservaRepository extends JpaRepository<Reserva, Integer> {
    Page<Reserva> findByFechaInicioBetweenAndFechaFinBetweenAndIdUsuarioAndIdHotelAndTotalGreaterThanOrderByIdDesc(
            LocalDateTime fechaInicioStart,
            LocalDateTime fechaInicioEnd,
            LocalDateTime fechaFinStart,
            LocalDateTime fechaFinEnd,
            Integer idUsuario,
            Integer idHotel,
            BigDecimal total,
            Pageable pageable
    );
}
