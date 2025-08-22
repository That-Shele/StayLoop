package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

 public interface IHotelRepository extends JpaRepository<Hotel, Integer> {

     @Query("SELECT h FROM Hotel h WHERE " +
             "(:idZona IS NULL OR h.idZona = :idZona) AND " +
             "(:nombre IS NULL OR LOWER(h.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
             "ORDER BY h.id DESC")
     Page<Hotel> findPaginated(
             @Param("idZona") Integer idZona,
             @Param("nombre") String nombre,
             Pageable pageable
     );

     Hotel findByDireccion (String direccion);

}
