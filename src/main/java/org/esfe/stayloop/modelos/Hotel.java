package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "hoteles")
public class Hotel {

    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String descripcion;
    
    @Column(nullable = false)
    private String direccion;
    
    @Column(name = "idZona")
    private Integer idZona;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idZona", insertable = false, updatable = false)
    private Zona zona;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<TipoHabitacion> tiposHabitacion;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Reserva> reservas;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Imagen> imagenes;

}
