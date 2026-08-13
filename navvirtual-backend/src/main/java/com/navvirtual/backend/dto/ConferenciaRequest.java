package com.navvirtual.backend.dto;

import com.navvirtual.backend.entity.Conferencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConferenciaRequest {
    @NotNull private Long eventoId;
    @NotBlank private String titulo;
    private String descripcion;
    @NotNull private Conferencia.Tipo tipo;
    @NotNull private LocalDateTime horario;
}