package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IReservaRepository extends JpaRepository<Reserva, Integer> {
    Page<Reserva> findByFechaFinBefore(Pageable pageable);
    Page<Reserva> findByFechaInicioBefore(Pageable pageable);
    Page<Reserva> findByFechaFinAfter(Pageable pageable);
    Page<Reserva> findByFechaInicioAfter(Pageable pageable);
    Page<Reserva> findByOrderByIdUsuarioDesc(Pageable pageable);
    Page<Reserva> findByTotalGreaterThanEqual(Pageable pageable);
    Page<Reserva> findByIdUsuarioAndIdHotelOrderByIdUsuarioDesc(Pageable pageable);
    Page<Reserva> findByOrderByIdHotelDesc(Pageable pageable);
}
