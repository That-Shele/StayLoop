package org.esfe.stayloop.servicios.implementaciones;

import org.esfe.stayloop.modelos.Usuario;
import org.esfe.stayloop.repositorios.IUsuarioRepository;
import org.esfe.stayloop.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Page<Usuario> buscarPaginados( String nombre, String email, Integer idRol,  Pageable pageable) {
        return usuarioRepository
                .findPaginated(nombre, email, idRol,  pageable);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return Optional.ofNullable(usuarioRepository.findByEmail(email));
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
