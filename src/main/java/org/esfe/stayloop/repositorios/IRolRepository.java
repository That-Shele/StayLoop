package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRolRepository extends JpaRepository<Rol, Integer> {
    List<Rol> findByNombreContainingIgnoreCaseOrderByIdDesc(String nombre);
}
