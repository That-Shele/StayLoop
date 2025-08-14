package org.esfe.stayloop.servicios.implementaciones;

import org.esfe.stayloop.modelos.Zona;
import org.esfe.stayloop.repositorios.IZonaRepository;
import org.esfe.stayloop.servicios.interfaces.IZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZonaService implements IZonaService {

    @Autowired
    private IZonaRepository zonaRepository;


    @Override
    public List<Zona> obtenerTodos() {
        return zonaRepository.findAll();
    }

    @Override
    public Page<Zona> buscarPaginados(Pageable pageable, Integer zona) {
        return zonaRepository.findByNombreContainingIgnoreCaseOrderByIdDesc(zona, pageable);
    }

    @Override
    public Zona buscarPorId(Integer id) {
        return zonaRepository.findById(id).get();
    }

    @Override
    public Zona crearOEditar(Zona zona) {
        return zonaRepository.save(zona);
    }

    @Override
    public void eliminarPorId(Integer id) {
          zonaRepository.deleteById(id);
    }
}
