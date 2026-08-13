package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.PerfilRequest;
import com.navvirtual.backend.dto.PerfilResponse;
import com.navvirtual.backend.entity.Rol;
import com.navvirtual.backend.entity.Usuario;
import com.navvirtual.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<PerfilResponse> obtener(Authentication auth) {
        Usuario u = usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));
        return ResponseEntity.ok(toResponse(u));
    }

    @PutMapping
    public ResponseEntity<PerfilResponse> actualizar(@RequestBody PerfilRequest request, Authentication auth) {
        Usuario u = usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));
        if (request.getNombre() != null) u.setNombre(request.getNombre());
        if (request.getApellido() != null) u.setApellido(request.getApellido());
        if (request.getFotoPerfilUrl() != null) u.setFotoPerfilUrl(request.getFotoPerfilUrl());
        usuarioRepository.save(u);
        return ResponseEntity.ok(toResponse(u));
    }

    private PerfilResponse toResponse(Usuario u) {
        return new PerfilResponse(u.getId(), u.getNombre(), u.getApellido(), u.getEmail(), u.getFotoPerfilUrl(),
                u.getRoles().stream().map(Rol::getNombre).toList());
    }
}