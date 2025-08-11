package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Zona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IZonaRepository extends JpaRepository<Zona, Integer> {
    List<Zona> findByNombreContainingIgnoreCase(String nombre);
}
