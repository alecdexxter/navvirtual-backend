package com.navvirtual.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conferencias")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Conferencia {

    public enum Tipo { CHARLA, MUESTRA_ARTISTICA, MUESTRA_PRODUCTO, OTRO }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo;

    @Column(nullable = false)
    private LocalDateTime horario;
}