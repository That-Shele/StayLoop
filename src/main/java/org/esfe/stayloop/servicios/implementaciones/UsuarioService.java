package org.esfe.stayloop.servicios.implementaciones;

import org.esfe.stayloop.modelos.Hotel;
import org.esfe.stayloop.modelos.Usuario;
import org.esfe.stayloop.repositorios.IUsuarioRepository;
import org.esfe.stayloop.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Page<Usuario> buscarPaginados(Pageable pageable, Integer idRol, String nombre, String email) {
        return usuarioRepository
                .findByIdRolAndNombreContainingIgnoreCaseAndEmailContainingIgnoreCaseOrderByIdDesc(idRol, nombre, email, pageable);
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).get();
    }

    @Override
    public Usuario crearOEditar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarPorId(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
