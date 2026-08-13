package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.BuffetRequest;
import com.navvirtual.backend.dto.BuffetResponse;
import com.navvirtual.backend.service.BuffetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buffets")
@RequiredArgsConstructor
public class BuffetController {

    private final BuffetService buffetService;

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<BuffetResponse> crear(@Valid @RequestBody BuffetRequest request) {
        return ResponseEntity.ok(buffetService.crear(request));
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<BuffetResponse> obtenerPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(buffetService.obtenerPorEvento(eventoId));
    }

    @GetMapping("/mis-buffets")
    public ResponseEntity<List<BuffetResponse>> misBuffets(Authentication auth) {
        return ResponseEntity.ok(buffetService.listarMisBuffets(auth.getName()));
    }

    @PostMapping("/{id}/empleados/{usuarioId}")
    public ResponseEntity<BuffetResponse> asignarEmpleado(@PathVariable Long id, @PathVariable Long usuarioId, Authentication auth) {
        return ResponseEntity.ok(buffetService.asignarEmpleado(id, usuarioId, auth.getName()));
    }

    @DeleteMapping("/{id}/empleados/{usuarioId}")
    public ResponseEntity<BuffetResponse> quitarEmpleado(@PathVariable Long id, @PathVariable Long usuarioId, Authentication auth) {
        return ResponseEntity.ok(buffetService.quitarEmpleado(id, usuarioId, auth.getName()));
    }
}