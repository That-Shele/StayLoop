package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(name = "idHotel")
    private Integer idHotel;

    @Column(name = "idTipoHabitacion")
    private Integer idTipoHabitacion;

    @Column(nullable = false)
    private LocalDateTime fechaRealizado;
    

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull(message = "El usuario es obligatorio") Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(@NotNull(message = "El usuario es obligatorio") Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public @NotNull(message = "El hotel es obligatorio") Integer getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(@NotNull(message = "El hotel es obligatorio") Integer idHotel) {
        this.idHotel = idHotel;
    }

    public @NotNull(message = "El tipo de habitación es obligatorio") Integer getIdTipoHabitacion() {
        return idTipoHabitacion;
    }

    public void setIdTipoHabitacion(@NotNull(message = "El tipo de habitación es obligatorio") Integer idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
    }

    public @NotNull(message = "La fecha de realización es obligatoria") @PastOrPresent(message = "La fecha de realización no puede ser futura") LocalDateTime getFechaRealizado() {
        return fechaRealizado;
    }

    public void setFechaRealizado(@NotNull(message = "La fecha de realización es obligatoria")  LocalDateTime fechaRealizado) {
        this.fechaRealizado = fechaRealizado;
    }

    public @NotNull(message = "El total es obligatorio") @Positive(message = "El total debe ser mayor a 0") BigDecimal getTotal() {
        return total;
    }

    public void setTotal(@NotNull(message = "El total es obligatorio") @Positive(message = "El total debe ser mayor a 0") BigDecimal total) {
        this.total = total;
    }

    public @NotNull(message = "La fecha de inicio es obligatoria") @Future(message = "La fecha de inicio debe ser futura") LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(@NotNull(message = "La fecha de inicio es obligatoria") @Future(message = "La fecha de inicio debe ser futura") LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public @NotNull(message = "La fecha de fin es obligatoria") @Future(message = "La fecha de fin debe ser futura") LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(@NotNull(message = "La fecha de fin es obligatoria") @Future(message = "La fecha de fin debe ser futura") LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }
}
