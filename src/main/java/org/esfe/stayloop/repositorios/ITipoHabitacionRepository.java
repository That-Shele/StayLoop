package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.TipoHabitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ITipoHabitacionRepository extends JpaRepository<TipoHabitacion, Integer> {
    List<TipoHabitacion> findByCantHabGreaterThanEqual (byte cantHab);
    List<TipoHabitacion> findByCantPersonasGreaterThanEqual(Byte cantPersonas);
    List<TipoHabitacion> findByCostoGreaterThan(BigDecimal costo);
    List<TipoHabitacion> findByIdHotel(Integer idHotel);

}
