package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByIdRolEquals(Integer idRol);
}
