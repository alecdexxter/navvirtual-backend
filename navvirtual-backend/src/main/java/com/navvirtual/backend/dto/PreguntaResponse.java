package com.navvirtual.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PreguntaResponse {
    private Long id;
    private String texto;
    private List<OpcionResponse> opciones;
}