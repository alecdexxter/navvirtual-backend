package com.navvirtual.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StandRequest {

    @NotBlank
    private String nombre;

    private String descripcion;
    private String imagenPortadaUrl;
    private String videoUrl;

    @NotNull
    private Long eventoId;

    private Long propietarioId; // opcional — solo lo usa el SUPERADMIN al crear el stand
}