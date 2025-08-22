package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IImagenRepository extends JpaRepository<Imagen, Integer> {
    List<Imagen> findByIdHotel(Integer idHotel);

    Optional<Imagen> findFirstByIdHotel(Integer idHotel);
    Optional<Imagen> findById(Integer id);
}
