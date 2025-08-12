package org.esfe.stayloop.servicios.interfaces;

import org.esfe.stayloop.modelos.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IHotelService {
    List<Hotel> obtenerTodos();

    Page<Hotel> buscarNombrePaginados(Pageable pageable, String nombre);

    Page<Hotel> buscarPorZonaPaginados(Pageable pageable, Integer idZona);

    Hotel buscarPorId(Integer id);

    Hotel crearOEditar(Hotel hotel);

    void eliminarPorId(Integer id);
}
