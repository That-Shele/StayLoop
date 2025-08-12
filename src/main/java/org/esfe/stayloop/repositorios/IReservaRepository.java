package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByFechaFinBefore(LocalDateTime fechaFin);
    List<Reserva> findByFechaInicioBefore(LocalDateTime fechaInicio);
    List<Reserva> findByFechaFinAfter(LocalDateTime fechaFin);
    List<Reserva> findByFechaInicioAfter(LocalDateTime fechaInicio);
    List<Reserva> findByIdUsuarioOrderByIdUsuarioDesc(Integer idUsuario);
    List<Reserva> findByTotalGreaterThan(BigDecimal total);
    List<Reserva> findByIdUsuarioAndIdHotelOrderByIdHotelDesc(Integer idUsuario, Integer idHotel);
    List<Reserva> findByIdHotelOrderByIdHotelDesc(Integer idHotel);
}
