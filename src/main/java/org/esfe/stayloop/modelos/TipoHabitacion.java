package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tipos_habitacion")
public class TipoHabitacion {
    
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

    // Getters y Setters
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

    public Byte getCantpersonas() {
        return cantpersonas;
    }

    public void setCantpersonas(Byte cantpersonas) {
        this.cantpersonas = cantpersonas;
    }

    public Byte getCanthab() {
        return canthab;
    }

    public void setCanthab(Byte canthab) {
        this.canthab = canthab;
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
