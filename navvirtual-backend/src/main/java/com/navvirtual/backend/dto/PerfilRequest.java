package com.navvirtual.backend.dto;
import lombok.Data;

@Data
public class PerfilRequest {
    private String nombre;
    private String apellido;
    private String fotoPerfilUrl;
}