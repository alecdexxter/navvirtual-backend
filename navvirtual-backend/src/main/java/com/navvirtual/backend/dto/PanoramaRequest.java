package com.navvirtual.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PanoramaRequest {
    @NotBlank private String nombre;
    @NotBlank private String imagenUrl;
    @NotNull private Long eventoId;
    private boolean esPuntoInicio;
}