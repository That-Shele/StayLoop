package org.esfe.stayloop.servicios.implementaciones;

import org.esfe.stayloop.modelos.Hotel;
import org.esfe.stayloop.repositorios.IHotelRepository;
import org.esfe.stayloop.servicios.interfaces.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService implements IHotelService {

    @Autowired
    private IHotelRepository hotelRepository;

    @Override
    public List<Hotel> obtenerTodos() {
        return hotelRepository.findAll();
    }

    @Override
    public Page<Hotel> buscarPaginados(Pageable pageable, Integer zona, String nombre) {
        return hotelRepository.findByIdZonaAndNombreContainingIgnoreCaseOrderByIdDesc(zona, nombre, pageable);
    }

    @Override
    public Hotel buscarPorId(Integer id) {
        return hotelRepository.findById(id).get();
    }

    @Override
    public Hotel crearOEditar(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public void eliminarPorId(Integer id) {
        hotelRepository.deleteById(id);
    }
}
