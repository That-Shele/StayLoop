package org.esfe.stayloop.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "imagenes")
public class Imagenes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "id_hotel")
    private Integer idHotel;
    
    @Lob
    @Column(nullable = false)
    private byte[] imagen;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel", insertable = false, updatable = false)
    private Hotel hotel;

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

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
