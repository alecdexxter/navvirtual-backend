package com.navvirtual.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespuestaResultado {
    private boolean correcta;
    private String mensaje;
}