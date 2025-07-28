package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "hoteles")
public class Hotel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String descripcion;
    
    @Column(nullable = false)
    private String direccion;
    
    @Column(name = "id_zona")
    private Integer idZona;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zona", insertable = false, updatable = false)
    private Zona zona;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<TipoHabitacion> tiposHabitacion;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Reserva> reservas;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Imagenes> imagenes;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public List<TipoHabitacion> getTiposHabitacion() {
        return tiposHabitacion;
    }

    public void setTiposHabitacion(List<TipoHabitacion> tiposHabitacion) {
        this.tiposHabitacion = tiposHabitacion;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Imagenes> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagenes> imagenes) {
        this.imagenes = imagenes;
    }
}
