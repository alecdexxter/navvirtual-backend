package com.navvirtual.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "votos", uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "stand_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "stand_id", nullable = false)
    private Stand stand;
}