package com.navvirtual.backend.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventoRequest {

    @NotBlank
    private String nombre;

    private String descripcion;

    @NotNull @Future
    private LocalDateTime fechaInicio;

    @NotNull @Future
    private LocalDateTime fechaFin;
}