package com.navvirtual.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stands")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Stand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String imagenPortadaUrl;
    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private Usuario propietario;

    @ManyToMany
    @JoinTable(
            name = "stand_empleados",
            joinColumns = @JoinColumn(name = "stand_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> empleados = new HashSet<>();
}