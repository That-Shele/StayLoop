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
            Integer idUsuario,
            Integer idHotel,
            BigDecimal total,
            Pageable pageable
    );

    List<Reserva> buscarPorIdHotel(Integer idHotel);

    List<Reserva> buscarSegunPropietario(Integer idUsuario);


    Reserva buscarPorId(Integer id);


    Reserva crearOEditar(Reserva reserva);


    void eliminarPorId(Integer id);
}