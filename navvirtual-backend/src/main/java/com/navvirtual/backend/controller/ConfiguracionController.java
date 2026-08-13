package com.navvirtual.backend.controller;

import com.navvirtual.backend.entity.ConfiguracionSitio;
import com.navvirtual.backend.repository.ConfiguracionSitioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/configuracion")
@RequiredArgsConstructor
public class ConfiguracionController {

    private final ConfiguracionSitioRepository repository;

    @GetMapping
    public ResponseEntity<ConfiguracionSitio> obtener() {
        ConfiguracionSitio config = repository.findById(1L).orElseGet(() -> {
            ConfiguracionSitio nueva = new ConfiguracionSitio();
            nueva.setId(1L);
            return repository.save(nueva);
        });
        return ResponseEntity.ok(config);
    }

    @PutMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<ConfiguracionSitio> actualizar(@RequestBody Map<String, String> body) {
        ConfiguracionSitio config = repository.findById(1L).orElse(new ConfiguracionSitio(1L, null));
        config.setImagenPortadaUrl(body.get("imagenPortadaUrl"));
        repository.save(config);
        return ResponseEntity.ok(config);
    }
}