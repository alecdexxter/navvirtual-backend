package com.navvirtual.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RespuestaRequest {
    @NotNull private Long opcionId;
}