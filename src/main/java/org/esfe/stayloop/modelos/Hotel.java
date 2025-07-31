package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "El nombre es requerido")
    private String nombre;

    private String descripcion;
    
    @Column(nullable = false)
    @NotBlank(message = "La direccion es requerida")
    private String direccion;
    
    @Column(name = "id_zona")
    private Integer idZona;
    
    @ManyToOne
    @JoinColumn(name = "id_zona", insertable = false, updatable = false)
    private Zona zona;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<TipoHabitacion> tiposHabitacion;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Reserva> reservas;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Imagen> imagenes;

}
