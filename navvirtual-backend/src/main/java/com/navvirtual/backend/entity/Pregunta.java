package com.navvirtual.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "preguntas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stand_id", nullable = false)
    private Stand stand;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpcionRespuesta> opciones = new ArrayList<>();
}