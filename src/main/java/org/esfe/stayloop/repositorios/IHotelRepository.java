package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IHotelRepository extends JpaRepository<Hotel, Integer> {
    Page<Hotel> findByNombreContainingIgnoreCase(Pageable pageable);

    Page<Hotel> findByIdZona(Pageable pageable);
}
