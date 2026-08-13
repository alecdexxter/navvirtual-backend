package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.ConferenciaRequest;
import com.navvirtual.backend.dto.ConferenciaResponse;
import com.navvirtual.backend.service.ConferenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conferencias")
@RequiredArgsConstructor
public class ConferenciaController {

    private final ConferenciaService conferenciaService;

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<ConferenciaResponse> crear(@Valid @RequestBody ConferenciaRequest request) {
        return ResponseEntity.ok(conferenciaService.crear(request));
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<ConferenciaResponse>> listarPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(conferenciaService.listarPorEvento(eventoId));
    }
}