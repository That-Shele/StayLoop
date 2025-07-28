package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "zonas")
public class Zona {

    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String descripcion;
    
    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL)
    private List<Hotel> hoteles;

}
