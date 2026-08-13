package com.navvirtual.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ComunicadoRequest {
    @NotNull private Long eventoId;
    @NotBlank private String mensaje;
}