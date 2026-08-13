package com.navvirtual.backend.dto;
import lombok.AllArgsConstructor; import lombok.Data; import java.util.List;

@Data @AllArgsConstructor
public class PerfilResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String fotoPerfilUrl;
    private List<String> roles;
}