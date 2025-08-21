package org.esfe.stayloop.servicios.interfaces;

import org.esfe.stayloop.modelos.Zona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IZonaService {
    List<Zona> obtenerTodos();

    Page<Zona> buscarPaginados(Pageable pageable, String nombre);

    Zona buscarPorId(Integer id);

    Zona crearOEditar(Zona zona);

    void eliminarPorId(Integer id);
}