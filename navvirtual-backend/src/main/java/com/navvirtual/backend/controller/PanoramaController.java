package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.PanoramaRequest;
import com.navvirtual.backend.dto.PanoramaResponse;
import com.navvirtual.backend.service.PanoramaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/panoramas")
@RequiredArgsConstructor
public class PanoramaController {

    private final PanoramaService panoramaService;

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<PanoramaResponse> crear(@Valid @RequestBody PanoramaRequest request) {
        return ResponseEntity.ok(panoramaService.crear(request));
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<PanoramaResponse>> listarPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(panoramaService.listarPorEvento(eventoId));
    }

    @GetMapping("/evento/{eventoId}/inicio")
    public ResponseEntity<PanoramaResponse> puntoInicio(@PathVariable Long eventoId) {
        return ResponseEntity.ok(panoramaService.obtenerPuntoInicio(eventoId));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<PanoramaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody PanoramaRequest request) {
        return ResponseEntity.ok(panoramaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        panoramaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}