package org.esfe.stayloop.servicios.interfaces;

import org.esfe.stayloop.modelos.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

 public interface IHotelService {
     List<Hotel> obtenerTodos();

     Page<Hotel> buscarPaginados(Pageable pageable,Integer zona, String nombre);

     Hotel buscarPorDireccion(String direccion);

     Hotel buscarPorId(Integer id);

     Hotel crearOEditar(Hotel hotel);

     void eliminarPorId(Integer id);
}
