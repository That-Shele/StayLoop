package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IReservaRepository extends JpaRepository<Reserva, Integer> {
    Page<Reserva> findByFechaFinBefore(LocalDateTime fechaFin, Pageable pageable);
    Page<Reserva> findByFechaInicioBefore(LocalDateTime fechaInicio, Pageable pageable);
    Page<Reserva> findByFechaFinAfter(LocalDateTime fechaFin, Pageable pageable);
    Page<Reserva> findByFechaInicioAfter(LocalDateTime fechaInicio, Pageable pageable);
    Page<Reserva> findByIdUsuarioOrderByIdUsuarioDesc(Integer idUsuario, Pageable pageable);
    Page<Reserva> findByTotalGreaterThan(BigDecimal total, Pageable pageable);
    Page<Reserva> findByIdUsuarioAndIdHotelOrderByIdHotelDesc(Integer idUsuario, Integer idHotel, Pageable pageable);
    Page<Reserva> findByIdHotelOrderByIdHotelDesc(Integer idHotel, Pageable pageable);
}
