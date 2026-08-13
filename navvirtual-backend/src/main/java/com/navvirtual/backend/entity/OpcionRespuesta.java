package com.navvirtual.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "opciones_respuesta")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OpcionRespuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pregunta_id", nullable = false)
    private Pregunta pregunta;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private boolean esCorrecta;
}