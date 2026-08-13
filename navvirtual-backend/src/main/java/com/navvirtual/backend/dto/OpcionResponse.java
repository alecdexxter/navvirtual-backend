package com.navvirtual.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpcionResponse {
    private Long id;
    private String texto;
    // OJO: esCorrecta NO se expone acá a propósito — no querés que el frontend
    // sepa cuál es la respuesta correcta antes de que el usuario conteste
}