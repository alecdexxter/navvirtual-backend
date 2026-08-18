package com.navvirtual.backend.controller;

import com.navvirtual.backend.dto.ProductoRequest;
import com.navvirtual.backend.dto.ProductoResponse;
import com.navvirtual.backend.entity.Producto;
import com.navvirtual.backend.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request, Authentication auth) {
        return ResponseEntity.ok(productoService.crear(request, auth.getName()));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponse>> listarPorCategoria(@PathVariable Producto.Categoria categoria) {
        return ResponseEntity.ok(productoService.listarPorCategoria(categoria));
    }

    @GetMapping("/stand/{standId}")
    public ResponseEntity<List<ProductoResponse>> listarPorStand(@PathVariable Long standId) {
        return ResponseEntity.ok(productoService.listarPorStand(standId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequest request, Authentication auth) {
        return ResponseEntity.ok(productoService.actualizar(id, request, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, Authentication auth) {
        productoService.eliminar(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buffet/{buffetId}")
    public ResponseEntity<List<ProductoResponse>> listarPorBuffet(@PathVariable Long buffetId) {
        return ResponseEntity.ok(productoService.listarPorBuffet(buffetId));
    }
}