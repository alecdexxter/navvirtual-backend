package com.navvirtual.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "buffets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Buffet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private Usuario propietario;

    @ManyToMany
    @JoinTable(
            name = "buffet_empleados",
            joinColumns = @JoinColumn(name = "buffet_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> empleados = new HashSet<>();
}