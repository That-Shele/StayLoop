package org.esfe.stayloop.servicios.interfaces;

import org.esfe.stayloop.modelos.Hotel;
import org.esfe.stayloop.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Page<Usuario> obtenerTodos(Pageable pageable);

    Page<Usuario> buscarPaginados( String nombre, String email, Integer idRol, Pageable pageable);

    Optional<Usuario> buscarPorEmail(String email);

    Optional<Usuario> buscarPorId(Integer id);

    Usuario crearOEditar(Usuario usuario);

    void eliminarPorId(Integer id);
}
