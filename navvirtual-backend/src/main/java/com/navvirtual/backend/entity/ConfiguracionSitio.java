package com.navvirtual.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "configuracion_sitio")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ConfiguracionSitio {

    @Id
    private Long id = 1L; // fila única

    private String imagenPortadaUrl;
}