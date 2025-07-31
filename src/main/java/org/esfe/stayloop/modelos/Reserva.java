package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "reservas")
public class Reserva {

    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    @Column(name = "id_hotel")
    private Integer idHotel;
    
    @Column(name = "id_tipo_habitacion")
    private Integer idTipoHabitacion;
    
    @Column(nullable = false)
    private LocalDateTime fechaRealizado;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    
    @Column(nullable = false)
    private LocalDateTime fechaInicio;
    
    @Column(nullable = false)
    private LocalDateTime fechaFin;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "id_hotel", insertable = false, updatable = false)
    private Hotel hotel;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_habitacion", insertable = false, updatable = false)
    private TipoHabitacion tipoHabitacion;

}
