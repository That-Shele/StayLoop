package org.esfe.stayloop.servicios.interfaces;

import org.esfe.stayloop.modelos.Hotel;
import org.esfe.stayloop.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUsuarioService {
    Page<Usuario> obtenerTodos(Pageable pageable);

    Page<Usuario> buscarPaginados(Pageable pageable, Integer idRol, String nombre, String email);

    Usuario buscarPorId(Integer id);

    Usuario crearOEditar(Usuario usuario);

    void eliminarPorId(Integer id);
}
