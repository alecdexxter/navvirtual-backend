package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.UsuarioResumen;
import com.navvirtual.backend.entity.Usuario;
import com.navvirtual.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping("/buscar")
    public ResponseEntity<UsuarioResumen> buscarPorEmail(@RequestParam String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("No existe un usuario con ese email"));
        return ResponseEntity.ok(new UsuarioResumen(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getEmail()));
    }
}