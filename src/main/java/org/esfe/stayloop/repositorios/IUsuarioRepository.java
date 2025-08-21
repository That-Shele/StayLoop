package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE " +
            "(:nombre IS NULL OR u.nombre LIKE %:nombre%) AND " +
            "(:email IS NULL OR u.email LIKE %:email%) AND " +
            "(:rolId IS NULL OR u.idRol = :rolId) ")
    Page<Usuario> findPaginated(
            @Param("nombre") String nombre,
            @Param("email") String email,
            @Param("rolId") Integer rolId,
            Pageable pageable
    );



}
