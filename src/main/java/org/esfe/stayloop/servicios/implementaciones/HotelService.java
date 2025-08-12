package org.esfe.stayloop.servicios.implementaciones;

import org.esfe.stayloop.modelos.Hotel;
import org.esfe.stayloop.repositorios.IHotelRepository;
import org.esfe.stayloop.servicios.interfaces.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class HotelService implements IHotelService {

    @Autowired
    private IHotelRepository hotelRepository;

    @Override
    public List<Hotel> obtenerTodos() {
        return hotelRepository.findAll();
    }

    @Override
    public Page<Hotel> buscarNombrePaginados(Pageable pageable, String nombre) {
        return hotelRepository.findByNombreContainingIgnoreCase(nombre, pageable);
    }

    @Override
    public Page<Hotel> buscarPorZonaPaginados(Pageable pageable, Integer idZona) {
        return hotelRepository.findByIdZona(idZona, pageable);
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
