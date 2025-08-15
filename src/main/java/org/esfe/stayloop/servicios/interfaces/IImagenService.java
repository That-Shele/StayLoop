package org.esfe.stayloop.servicios.interfaces;

import org.esfe.stayloop.modelos.Imagen;
import java.util.List;

public interface IImagenService {
    List<Imagen> obtenerTodas();

    List<Imagen> buscarPorIdHotel(Integer idHotel);

    Imagen crearOEditar(Imagen imagen);

    void eliminarPorId(Integer id);


}