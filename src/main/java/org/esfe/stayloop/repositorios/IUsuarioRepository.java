package org.esfe.stayloop.repositorios;

import org.esfe.stayloop.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
}
