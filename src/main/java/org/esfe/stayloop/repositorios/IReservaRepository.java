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
    List<Reserva> findByIdUsuario(Integer idUsuario);
    List<Reserva> findByTotalGreaterThanEqual(BigDecimal total);
    List<Reserva> findByIdUsuarioAndIdHotel(Integer idUsuario, Integer idHotel);
    List<Reserva> findByIdHotel(Integer idHotel);
}
