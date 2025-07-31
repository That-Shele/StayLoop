package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "tipos_habitacion")
public class TipoHabitacion {

    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "id_hotel")
    private Integer idHotel;
    
    @Column(nullable = false)
    private Byte cantpersonas;
    
    @Column(nullable = false)
    private Byte canthab;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel", insertable = false, updatable = false)
    private Hotel hotel;
    
    @OneToMany(mappedBy = "tipoHabitacion", cascade = CascadeType.ALL)
    private List<Reserva> reservas;

}
