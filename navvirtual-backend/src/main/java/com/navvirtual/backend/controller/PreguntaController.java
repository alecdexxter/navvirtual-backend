package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.*;
import com.navvirtual.backend.service.PreguntaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preguntas")
@RequiredArgsConstructor
public class PreguntaController {

    private final PreguntaService preguntaService;

    @PostMapping
    public ResponseEntity<PreguntaResponse> crear(@Valid @RequestBody PreguntaRequest request, Authentication auth) {
        return ResponseEntity.ok(preguntaService.crear(request, auth.getName()));
    }

    @GetMapping("/stand/{standId}")
    public ResponseEntity<List<PreguntaResponse>> listarPorStand(@PathVariable Long standId) {
        return ResponseEntity.ok(preguntaService.listarPorStand(standId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, Authentication auth) {
        preguntaService.eliminar(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{preguntaId}/responder")
    public ResponseEntity<RespuestaResultado> responder(@PathVariable Long preguntaId, @Valid @RequestBody RespuestaRequest request) {
        return ResponseEntity.ok(preguntaService.responder(preguntaId, request));
    }
}