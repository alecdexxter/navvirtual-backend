package com.navvirtual.backend.service;

import com.navvirtual.backend.dto.ProductoRequest;
import com.navvirtual.backend.dto.ProductoResponse;
import com.navvirtual.backend.entity.*;
import com.navvirtual.backend.repository.EventoRepository;
import com.navvirtual.backend.repository.ProductoRepository;
import com.navvirtual.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final StandService standService;
    private final BuffetService buffetService;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public ProductoResponse crear(ProductoRequest request, String email) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setDescuento(request.getDescuento());
        producto.setImagenUrl(request.getImagenUrl());
        producto.setCategoria(request.getCategoria());

        if (request.getCategoria() == Producto.Categoria.STAND) {
            if (request.getStandId() == null) throw new IllegalStateException("Falta standId");
            Stand stand = standService.buscarStand(request.getStandId());
            standService.verificarAccesoStand(stand, email);
            producto.setStand(stand);
        } else if (request.getCategoria() == Producto.Categoria.CONFITERIA) {
            if (request.getBuffetId() == null) throw new IllegalStateException("Falta buffetId");
            Buffet buffet = buffetService.buscarBuffet(request.getBuffetId());
            buffetService.verificarAccesoBuffet(buffet, email);
            producto.setBuffet(buffet);
        } else if (request.getCategoria() == Producto.Categoria.ENTRADA) {
            if (request.getEventoId() == null) throw new IllegalStateException("Falta eventoId");
            Usuario solicitante = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));
            boolean esSuperadmin = solicitante.getRoles().stream().anyMatch(r -> r.getNombre().equals("ROLE_SUPERADMIN"));
            if (!esSuperadmin) throw new IllegalStateException("Solo el superadmin puede crear entradas");

            Evento evento = eventoRepository.findById(request.getEventoId())
                    .orElseThrow(() -> new IllegalStateException("Evento no encontrado"));
            producto.setEvento(evento);
        }

        Usuario vendedor = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));
        producto.setVendedor(vendedor);

        productoRepository.save(producto);
        return toResponse(producto);
    }

    public List<ProductoResponse> listarPorCategoria(Producto.Categoria categoria) {
        return productoRepository.findByCategoria(categoria).stream().map(this::toResponse).toList();
    }

    public List<ProductoResponse> listarPorStand(Long standId) {
        return productoRepository.findByStandId(standId).stream().map(this::toResponse).toList();
    }

    public ProductoResponse obtenerPorId(Long id) {
        return toResponse(buscarProducto(id));
    }

    public ProductoResponse actualizar(Long id, ProductoRequest request, String email) {
        Producto producto = buscarProducto(id);
        verificarAccesoProducto(producto, email);

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setDescuento(request.getDescuento());
        producto.setImagenUrl(request.getImagenUrl());

        productoRepository.save(producto);
        return toResponse(producto);
    }

    public void eliminar(Long id, String email) {
        Producto producto = buscarProducto(id);
        verificarAccesoProducto(producto, email);
        productoRepository.delete(producto);
    }

    private Producto buscarProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Producto no encontrado"));
    }

    private void verificarAccesoProducto(Producto producto, String email) {
        if (producto.getStand() != null) {
            standService.verificarAccesoStand(producto.getStand(), email);
        } else if (producto.getBuffet() != null) {
            buffetService.verificarAccesoBuffet(producto.getBuffet(), email);
        }
    }

    private ProductoResponse toResponse(Producto producto) {
        return new ProductoResponse(
                producto.getId(), producto.getNombre(), producto.getDescripcion(),
                producto.getPrecio(), producto.getDescuento(), producto.getImagenUrl(),
                producto.getCategoria(),
                producto.getStand() != null ? producto.getStand().getId() : null,
                producto.getBuffet() != null ? producto.getBuffet().getId() : null,
                producto.getVendedor().getId(),
                producto.getEvento() != null ? producto.getEvento().getId() : null
        );
    }
}