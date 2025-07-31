package org.esfe.stayloop.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "imagenes")
public class Imagen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull(message = "El hotel es obligatorio")
    @Column(name = "id_hotel")
    private Integer idHotel;
    
    @NotNull(message = "La imagen es obligatoria")
    @Lob
    @Column(nullable = false)
    private byte[] imagen;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel", insertable = false, updatable = false)
    private Hotel hotel;
}
