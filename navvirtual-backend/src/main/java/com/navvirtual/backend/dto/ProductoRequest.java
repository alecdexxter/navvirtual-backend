package com.navvirtual.backend.dto;

import com.navvirtual.backend.entity.Producto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoRequest {

    @NotBlank
    private String nombre;

    private String descripcion;

    @NotNull @Positive
    private BigDecimal precio;

    private BigDecimal descuento;
    private String imagenUrl;

    @NotNull
    private Producto.Categoria categoria; // "STAND" o "CONFITERIA"

    private Long standId; // null si es CONFITERIA

    private Long buffetId;

    private Long eventoId;
}