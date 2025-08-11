package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IImagenRepository extends JpaRepository<Imagen, Integer> {
    List<Imagen> findByIdHotel(Integer idHotel);
}
