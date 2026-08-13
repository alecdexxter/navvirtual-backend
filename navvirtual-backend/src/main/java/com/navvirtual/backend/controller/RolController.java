package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.AsignarRolRequest;
import com.navvirtual.backend.service.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @PostMapping("/asignar")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> asignar(@Valid @RequestBody AsignarRolRequest request) {
        rolService.asignar(request.getUsuarioId(), request.getNombreRol());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/quitar")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Void> quitar(@Valid @RequestBody AsignarRolRequest request) {
        rolService.quitar(request.getUsuarioId(), request.getNombreRol());
        return ResponseEntity.ok().build();
    }
}