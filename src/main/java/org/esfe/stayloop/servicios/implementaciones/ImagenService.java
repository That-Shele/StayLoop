package org.esfe.stayloop.servicios.implementaciones;

import org.esfe.stayloop.modelos.Imagen;
import org.esfe.stayloop.repositorios.IImagenRepository;
import org.esfe.stayloop.servicios.interfaces.IImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenService implements IImagenService {

    @Autowired
    private IImagenRepository imagenRepository;

    @Override
    public List<Imagen> obtenerTodas() {
        return imagenRepository.findAll();
    }

    @Override
    public List<Imagen> buscarPorIdHotel(Integer idHotel) {
        return imagenRepository.findByIdHotel(idHotel);
    }

    @Override
    public Optional<Imagen> obtenerUnaPorIdHotel(Integer idHotel) {
        return imagenRepository.findFirstByIdHotel(idHotel);
    }

    @Override
    public Optional<Imagen> obtenerPorId(Integer id) {
        return imagenRepository.findById(id);
    }

    @Override
    public Imagen crearOEditar(Imagen imagen) {
        return imagenRepository.save(imagen);
    }

    @Override
    public void eliminarPorId(Integer id) {
        imagenRepository.deleteById(id);
    }


}