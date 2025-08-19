package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    Page<Usuario> findByIdRolOrNombreContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderByIdDesc(
            Integer idRol,
            String nombre,
            String email,
            Pageable pageable
    );

}
