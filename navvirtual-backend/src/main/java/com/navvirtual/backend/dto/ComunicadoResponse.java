package com.navvirtual.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ComunicadoResponse {
    private Long id;
    private String mensaje;
    private String gerenteNombre;
    private LocalDateTime fecha;
}