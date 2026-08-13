package com.navvirtual.backend.dto;

import com.navvirtual.backend.entity.Conferencia;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ConferenciaResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private Conferencia.Tipo tipo;
    private LocalDateTime horario;
}