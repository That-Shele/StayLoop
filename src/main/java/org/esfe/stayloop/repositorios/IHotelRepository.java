package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHotelRepository extends JpaRepository<Hotel, Integer> {
}
