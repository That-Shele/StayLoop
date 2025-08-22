package org.esfe.stayloop.servicios.interfaces;

import org.esfe.stayloop.modelos.Imagen;
import java.util.List;
import java.util.Optional;

public interface IImagenService {
    List<Imagen> obtenerTodas();

    List<Imagen> buscarPorIdHotel(Integer idHotel);

    Optional<Imagen> obtenerUnaPorIdHotel(Integer idHotel);

    Optional<Imagen> obtenerPorId(Integer id);

    Imagen crearOEditar(Imagen imagen);

    void eliminarPorId(Integer id);


}