package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.ComunicadoRequest;
import com.navvirtual.backend.dto.ComunicadoResponse;
import com.navvirtual.backend.service.ComunicadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunicados")
@RequiredArgsConstructor
public class ComunicadoController {

    private final ComunicadoService comunicadoService;

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<ComunicadoResponse> crear(@Valid @RequestBody ComunicadoRequest request, Authentication auth) {
        return ResponseEntity.ok(comunicadoService.crear(request, auth.getName()));
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<ComunicadoResponse>> listarPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(comunicadoService.listarPorEvento(eventoId));
    }
}