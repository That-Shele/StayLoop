package org.esfe.stayloop.servicios.implementaciones;

import org.esfe.stayloop.modelos.Rol;
import org.esfe.stayloop.repositorios.IRolRepository;
import org.esfe.stayloop.servicios.interfaces.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService implements IRolService {

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public List<Rol> obtenerTodos() {
        return rolRepository.findAll();
    }

    @Override
    public List<Rol> buscarPorNombre(String nombre) {
        return rolRepository
                .findByNombreContainingIgnoreCaseOrderByIdDesc(nombre);
    }

    @Override
    public Rol buscarPorId(Integer id) {
        return rolRepository.findById(id).get();
    }

    @Override
    public Rol crearOEditar(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void eliminarPorId(Integer id) {
        rolRepository.deleteById(id);
    }
}
