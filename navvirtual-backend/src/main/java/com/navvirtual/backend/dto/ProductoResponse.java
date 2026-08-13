package com.navvirtual.backend.dto;

import com.navvirtual.backend.entity.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private BigDecimal descuento;
    private String imagenUrl;
    private Producto.Categoria categoria;
    private Long standId;
    private Long vendedorId;
    private Long buffetId;
    private long eventoId;
}