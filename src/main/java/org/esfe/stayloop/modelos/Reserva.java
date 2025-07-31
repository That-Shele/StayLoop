package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull(message = "El usuario es obligatorio")
    @Column(name = "idUsuario")
    private Integer idUsuario;
    
    @NotNull(message = "El hotel es obligatorio")
    @Column(name = "idHotel")
    private Integer idHotel;
    
    @NotNull(message = "El tipo de habitación es obligatorio")
    @Column(name = "idTipoHabitacion")
    private Integer idTipoHabitacion;
    
    @NotNull(message = "La fecha de realización es obligatoria")
    @PastOrPresent(message = "La fecha de realización no puede ser futura")
    @Column(nullable = false)
    private LocalDateTime fechaRealizado;
    
    @NotNull(message = "El total es obligatorio")
    @Positive(message = "El total debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    @Future(message = "La fecha de inicio debe ser futura")
    @Column(nullable = false)
    private LocalDateTime fechaInicio;
    
    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser futura")
    @Column(nullable = false)
    private LocalDateTime fechaFin;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", insertable = false, updatable = false)
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idHotel", insertable = false, updatable = false)
    private Hotel hotel;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTipoHabitacion", insertable = false, updatable = false)
    private TipoHabitacion tipoHabitacion;

}
