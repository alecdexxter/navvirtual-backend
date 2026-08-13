package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.VotoResponse;
import com.navvirtual.backend.service.VotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votos")
@RequiredArgsConstructor
public class VotoController {

    private final VotoService votoService;

    @PostMapping("/stand/{standId}")
    public ResponseEntity<VotoResponse> votar(@PathVariable Long standId, Authentication auth) {
        return ResponseEntity.ok(votoService.votar(standId, auth.getName()));
    }

    @GetMapping("/stand/{standId}")
    public ResponseEntity<VotoResponse> obtenerEstado(@PathVariable Long standId, Authentication auth) {
        String email = auth != null ? auth.getName() : null;
        return ResponseEntity.ok(votoService.obtenerEstado(standId, email));
    }
}