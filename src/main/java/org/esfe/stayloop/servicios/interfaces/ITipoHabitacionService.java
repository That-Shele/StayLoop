package org.esfe.stayloop.servicios.interfaces;

import org.esfe.stayloop.modelos.TipoHabitacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ITipoHabitacionService {
    List<TipoHabitacion> obtenerTodos();

    List<TipoHabitacion> buscarPorCantidadHabitacion(byte cantHab);
    List<TipoHabitacion> buscarPorCantidadPersona(byte cantPersonas);
    List<TipoHabitacion> buscarPorCosto(BigDecimal costo);

    TipoHabitacion buscarPorId(Integer id);

    TipoHabitacion crearOEditar(TipoHabitacion tipoHabitacion);

    void eliminarPorId(Integer id);
}