package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservaRepository extends JpaRepository<Reserva, Integer> {
}
