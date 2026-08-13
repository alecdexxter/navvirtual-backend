package com.navvirtual.backend.dto;

import com.navvirtual.backend.entity.Compra;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CompraResponse {
    private Long id;
    private BigDecimal total;
    private Compra.Estado estado;
    private String codigoBoleta;
    private LocalDateTime fecha;
}