package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Zona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IZonaRepository extends JpaRepository<Zona, Integer> {
    Page<Zona> findByNombreContainsIgnoreCaseOrderByIdDesc(String nombre, Pageable pageable);
}
