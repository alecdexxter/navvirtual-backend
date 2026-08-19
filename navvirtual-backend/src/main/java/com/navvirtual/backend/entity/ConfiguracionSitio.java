package com.navvirtual.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "configuracion_sitio")
@Getter @Setter @NoArgsConstructor
public class ConfiguracionSitio {

    @Id
    private Long id = 1L;

    @ElementCollection
    @CollectionTable(name = "configuracion_sitio_imagenes", joinColumns = @JoinColumn(name = "configuracion_id"))
    @Column(name = "url")
    @OrderColumn(name = "orden")
    private List<String> imagenesPortada = new ArrayList<>();
}