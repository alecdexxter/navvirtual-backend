package com.navvirtual.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemCompraRequest {

    @NotNull
    private Long productoId;

    @NotNull @Positive
    private Integer cantidad;
}