package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
