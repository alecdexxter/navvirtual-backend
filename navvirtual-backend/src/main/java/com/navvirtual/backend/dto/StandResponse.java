package com.navvirtual.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
public class StandResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagenPortadaUrl;
    private String videoUrl;
    private Long eventoId;
    private Long propietarioId;
    private Set<Long> empleadosIds;
}