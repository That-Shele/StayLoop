package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "tipos_habitacion")
public class TipoHabitacion {

    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "idHotel")
    private Integer idHotel;

    @NotBlank(message = "Digite un nombre para el tipo de hospedaje")
    @Column(nullable = false)
    private String nombre;

    @Positive(message = "Ingrese una cantidad en enteros positivos")
    @Max(value = 30, message = "Máximo 30 personas por hospedaje")
    @Column(nullable = false)
    private Byte cantPersonas;

    @Max(value = 10, message = "Máximo 10 habitaciones por hospedaje")
    @Column(nullable = false)
    private Byte cantHab;

    @Positive(message = "Ingrese un costo en dígitos positivos")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idHotel", insertable = false, updatable = false)
    private Hotel hotel;
    
    @OneToMany(mappedBy = "tipoHabitacion", cascade = CascadeType.ALL)
    private List<Reserva> reservas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(Integer idHotel) {
        this.idHotel = idHotel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Byte getCantPersonas() {
        return cantPersonas;
    }

    public void setCantPersonas(Byte cantPersonas) {
        this.cantPersonas = cantPersonas;
    }

    public Byte getCantHab() {
        return cantHab;
    }

    public void setCantHab(Byte cantHab) {
        this.cantHab = cantHab;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
