package org.esfe.stayloop.servicios.interfaces;

import org.esfe.stayloop.modelos.Rol;

import java.util.List;

public interface IRolService {
    List<Rol> obtenerTodos();

    List<Rol> buscarPorNombre(String nombre);

    Rol buscarPorId(Integer id);

    Rol crearOEditar(Rol rol    );

    void eliminarPorId(Integer id);
}
