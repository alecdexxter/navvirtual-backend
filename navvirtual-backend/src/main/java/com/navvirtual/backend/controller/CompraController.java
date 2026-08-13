package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.CompraRequest;
import com.navvirtual.backend.dto.CompraResponse;
import com.navvirtual.backend.service.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<CompraResponse> procesar(@Valid @RequestBody CompraRequest request, Authentication auth) {
        return ResponseEntity.ok(compraService.procesarCompra(request, auth.getName()));
    }

    @GetMapping("/mis-compras")
    public ResponseEntity<List<CompraResponse>> misCompras(Authentication auth) {
        return ResponseEntity.ok(compraService.listarMisCompras(auth.getName()));
    }

    @GetMapping("/boleta/{codigo}")
    public ResponseEntity<CompraResponse> obtenerPorBoleta(@PathVariable String codigo) {
        return ResponseEntity.ok(compraService.obtenerPorCodigoBoleta(codigo));
    }
}