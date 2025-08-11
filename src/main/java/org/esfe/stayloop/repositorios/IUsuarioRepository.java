package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByIdRolEquals(Integer idRol);
    List<Usuario> findByNombre(String nombre);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByPassword(String password);

}
