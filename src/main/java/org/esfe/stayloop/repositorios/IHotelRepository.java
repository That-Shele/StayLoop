package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IHotelRepository extends JpaRepository<Hotel, Integer> {
    List<Hotel> findByNombreContaining(String nombre);

    List<Hotel> findByIdZona(Integer idZona);
}
