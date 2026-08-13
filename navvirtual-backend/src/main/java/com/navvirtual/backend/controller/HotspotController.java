package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.HotspotRequest;
import com.navvirtual.backend.dto.HotspotResponse;
import com.navvirtual.backend.service.HotspotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotspots")
@RequiredArgsConstructor
public class HotspotController {

    private final HotspotService hotspotService;

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<HotspotResponse> crear(@Valid @RequestBody HotspotRequest request) {
        return ResponseEntity.ok(hotspotService.crear(request));
    }

    @GetMapping("/panorama/{panoramaId}")
    public ResponseEntity<List<HotspotResponse>> listarPorPanorama(@PathVariable Long panoramaId) {
        return ResponseEntity.ok(hotspotService.listarPorPanorama(panoramaId));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<HotspotResponse> actualizar(@PathVariable Long id, @Valid @RequestBody HotspotRequest request) {
        return ResponseEntity.ok(hotspotService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        hotspotService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}