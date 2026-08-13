package com.navvirtual.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PanoramaResponse {
    private Long id;
    private String nombre;
    private String imagenUrl;
    private Long eventoId;
    private boolean esPuntoInicio;
}