package com.navvirtual.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Producto {

    public enum Categoria { STAND, CONFITERIA, ENTRADA }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private BigDecimal precio;

    private BigDecimal descuento; // porcentaje o monto, opcional

    private String imagenUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "stand_id")
    private Stand stand; // null si es de confitería general

    @ManyToOne
    @JoinColumn(name = "buffet_id")
    private Buffet buffet; // null si es categoría STAND

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Usuario vendedor; // empleado que lo carga

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento; // solo se usa si categoria = ENTRADA
}