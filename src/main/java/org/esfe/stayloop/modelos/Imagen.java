package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "imagen")
public class Imagen {

    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "id_hotel")
    private Integer idHotel;
    
    @Lob
    @Column(nullable = false)
    private byte[] imagen;
    
    @ManyToOne
    @JoinColumn(name = "id_hotel", insertable = false, updatable = false)
    private Hotel hotel;

}
