package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.StandRequest;
import com.navvirtual.backend.dto.StandResponse;
import com.navvirtual.backend.service.StandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stands")
@RequiredArgsConstructor
public class StandController {

    private final StandService standService;

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<StandResponse> crear(@Valid @RequestBody StandRequest request) {
        return ResponseEntity.ok(standService.crear(request));
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<StandResponse>> listarPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(standService.listarPorEvento(eventoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(standService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandResponse> actualizar(@PathVariable Long id, @Valid @RequestBody StandRequest request, Authentication auth) {
        return ResponseEntity.ok(standService.actualizar(id, request, auth.getName()));
    }

    @PutMapping("/{id}/propietario/{usuarioId}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<StandResponse> asignarPropietario(@PathVariable Long id, @PathVariable Long usuarioId) {
        return ResponseEntity.ok(standService.asignarPropietario(id, usuarioId));
    }

    @GetMapping("/mis-stands")
    public ResponseEntity<List<StandResponse>> misStands(Authentication auth) {
        return ResponseEntity.ok(standService.listarMisStands(auth.getName()));
    }

    @PostMapping("/{id}/empleados/{usuarioId}")
    public ResponseEntity<StandResponse> asignarEmpleado(@PathVariable Long id, @PathVariable Long usuarioId, Authentication auth) {
        return ResponseEntity.ok(standService.asignarEmpleado(id, usuarioId, auth.getName()));
    }

    @DeleteMapping("/{id}/empleados/{usuarioId}")
    public ResponseEntity<StandResponse> quitarEmpleado(@PathVariable Long id, @PathVariable Long usuarioId, Authentication auth) {
        return ResponseEntity.ok(standService.quitarEmpleado(id, usuarioId, auth.getName()));
    }
}