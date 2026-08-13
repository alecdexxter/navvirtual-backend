package com.navvirtual.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AsignarRolRequest {
    @NotNull
    private Long usuarioId;

    @NotBlank
    private String nombreRol; // ej: "ROLE_DUENIO_STAND"
}