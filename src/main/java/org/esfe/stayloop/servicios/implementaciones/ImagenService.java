package org.esfe.stayloop.servicios.implementaciones;

import org.esfe.stayloop.modelos.Imagen;
import org.esfe.stayloop.repositorios.IImagenRepository;
import org.esfe.stayloop.servicios.interfaces.IImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Imagen crearOEditar(Imagen imagen) {
        return imagenRepository.save(imagen);
    }

    @Override
    public void eliminarPorId(Integer id) {
        imagenRepository.deleteById(id);
    }


}