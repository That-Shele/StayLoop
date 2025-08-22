package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "imagenes")
public class Imagen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull(message = "El hotel es obligatorio")
    @Column(name = "idHotel")
    private Integer idHotel;
    
    @NotNull(message = "La imagen es obligatoria")
    @Lob
    @Column(columnDefinition="LONGBLOB", nullable = false)
    private byte[] imagen;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idHotel", insertable = false, updatable = false)
    private Hotel hotel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull(message = "El hotel es obligatorio") Integer getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(@NotNull(message = "El hotel es obligatorio") Integer idHotel) {
        this.idHotel = idHotel;
    }

    @NotNull(message = "La imagen es obligatoria")
    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(@NotNull(message = "La imagen es obligatoria") byte[] imagen) {
        this.imagen = imagen;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
