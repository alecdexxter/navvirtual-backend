package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.EventoRequest;
import com.navvirtual.backend.dto.EventoResponse;
import com.navvirtual.backend.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<EventoResponse> crear(@Valid @RequestBody EventoRequest request) {
        return ResponseEntity.ok(eventoService.crear(request));
    }

    @GetMapping("/publicos/vigentes")
    public ResponseEntity<List<EventoResponse>> listarVigentes() {
        return ResponseEntity.ok(eventoService.listarVigentesOProximos());
    }

    @GetMapping("/todos")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<List<EventoResponse>> listarTodos() {
        return ResponseEntity.ok(eventoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<EventoResponse> actualizar(@PathVariable Long id, @Valid @RequestBody EventoRequest request) {
        return ResponseEntity.ok(eventoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eventoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}