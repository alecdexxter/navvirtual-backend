package com.navvirtual.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hotspots")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Hotspot {

    public enum Tipo { NAVEGACION, STAND, TIENDA, ESCENARIO }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "panorama_origen_id", nullable = false)
    private Panorama panoramaOrigen;

    @ManyToOne
    @JoinColumn(name = "panorama_destino_id")
    private Panorama panoramaDestino; // si es tipo NAVEGACION

    @ManyToOne
    @JoinColumn(name = "stand_id")
    private Stand stand; // si es tipo STAND

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo;

    @Column(nullable = false)
    private Double yaw; // posición horizontal en el panorama (grados)

    @Column(nullable = false)
    private Double pitch; // posición vertical
}