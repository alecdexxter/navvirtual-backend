package com.navvirtual.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BuffetRequest {
    @NotNull
    private Long eventoId;

    private Long propietarioId;
}