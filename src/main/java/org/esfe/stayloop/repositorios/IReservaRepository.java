package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByFechaFinBefore(LocalDateTime fechaFin);

}
