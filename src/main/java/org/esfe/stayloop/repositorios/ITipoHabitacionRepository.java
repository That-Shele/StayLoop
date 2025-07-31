package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITipoHabitacionRepository extends JpaRepository<TipoHabitacion, Integer> {
    List<TipoHabitacion> findByCantHabGreaterThanEqual(byte cantHab);
}
