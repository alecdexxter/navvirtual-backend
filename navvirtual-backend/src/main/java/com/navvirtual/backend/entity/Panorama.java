package com.navvirtual.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "panoramas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Panorama {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre; // ej: "Entrada principal", "Pasillo stands salud"

    @Column(nullable = false)
    private String imagenUrl; // imagen equirectangular 360

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    private boolean esPuntoInicio;
}