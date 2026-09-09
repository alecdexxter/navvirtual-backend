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

    @PostMapping("/imagenes")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<ConfiguracionSitio> agregarImagen(@RequestBody Map<String, String> body) {
        ConfiguracionSitio config = repository.findById(1L).orElseGet(() -> {
            ConfiguracionSitio nueva = new ConfiguracionSitio();
            nueva.setId(1L);
            return nueva;
        });
        config.getImagenesPortada().add(body.get("url"));
        repository.save(config);
        return ResponseEntity.ok(config);
    }

    @DeleteMapping("/imagenes")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<ConfiguracionSitio> quitarImagen(@RequestParam String url) {
        ConfiguracionSitio config = repository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("Configuración no encontrada"));
        config.getImagenesPortada().remove(url);
        repository.save(config);
        return ResponseEntity.ok(config);
    }

    @PutMapping("/evento-destacado")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<ConfiguracionSitio> actualizarEventoDestacado(@RequestBody Map<String, Long> body) {
        ConfiguracionSitio config = repository.findById(1L).orElseGet(() -> {
            ConfiguracionSitio nueva = new ConfiguracionSitio();
            nueva.setId(1L);
            return nueva;
        });
        config.setEventoDestacadoId(body.get("eventoId"));
        repository.save(config);
        return ResponseEntity.ok(config);
    }
}