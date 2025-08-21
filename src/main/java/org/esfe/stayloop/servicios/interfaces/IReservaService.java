package org.esfe.stayloop.servicios.interfaces;

import org.esfe.stayloop.modelos.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IReservaService {

    // Obtener todas las reservas
    List<Reserva> obtenerTodas();

    // Buscar con filtros y paginación
    Page<Reserva> buscarPaginados(
            LocalDateTime fechaInicioStart,
            LocalDateTime fechaInicioEnd,
            LocalDateTime fechaFinStart,
            LocalDateTime fechaFinEnd,
            Integer idUsuario,
            Integer idHotel,
            BigDecimal total,
            Pageable pageable
    );


    Reserva buscarPorId(Integer id);


    Reserva crearOEditar(Reserva reserva);


    void eliminarPorId(Integer id);
}