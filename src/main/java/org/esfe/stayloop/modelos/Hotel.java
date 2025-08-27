package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "hoteles")
public class Hotel {

    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del hotel es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "Digite una descripción")
    @Column(nullable = false, length = 21844)
    private String descripcion;

    @NotBlank(message = "Digite la dirección de su hotel")
    @Column(nullable = false)
    private String direccion;

    @Positive(message = "Seleccione una zona")
    @Column(name = "idZona", nullable = false)
    private Integer idZona;

    @Column(name = "idUsuario", nullable = false)
    private Integer idUsuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idZona", insertable = false, updatable = false)
    private Zona zona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", insertable = false, updatable = false)
    private  Usuario usuario;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<TipoHabitacion> tiposHabitacion = new ArrayList<>();
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Reserva> reservas;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Imagen> imagenes;

    @Transient
    private List<Integer> idsTiposHabitacionParaEliminar;

    @Transient
    private List<Integer> idsImagenesParaEliminar;


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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public List<Integer> getIdsTiposHabitacionParaEliminar() {
        return idsTiposHabitacionParaEliminar;
    }

    public void setIdsTiposHabitacionParaEliminar(List<Integer> idsTiposHabitacionParaEliminar) {
        this.idsTiposHabitacionParaEliminar = idsTiposHabitacionParaEliminar;
    }

    public List<Integer> getIdsImagenesParaEliminar() {
        return idsImagenesParaEliminar;
    }

    public void setIdsImagenesParaEliminar(List<Integer> idsImagenesParaEliminar) {
        this.idsImagenesParaEliminar = idsImagenesParaEliminar;
    }
}
