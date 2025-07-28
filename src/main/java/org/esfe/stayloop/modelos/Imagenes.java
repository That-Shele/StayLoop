package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "imagenes")
public class Imagenes {

    // Getters y Setters
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

}
