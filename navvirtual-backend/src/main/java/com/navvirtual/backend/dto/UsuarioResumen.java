package com.navvirtual.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioResumen {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
}