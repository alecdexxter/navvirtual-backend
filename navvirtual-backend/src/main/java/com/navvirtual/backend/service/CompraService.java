package com.navvirtual.backend.service;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.navvirtual.backend.dto.CompraRequest;
import com.navvirtual.backend.dto.CompraResponse;
import com.navvirtual.backend.dto.ItemCompraRequest;
import com.navvirtual.backend.entity.*;
import com.navvirtual.backend.repository.CompraRepository;
import com.navvirtual.backend.repository.ProductoRepository;
import com.navvirtual.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    public CompraResponse procesarCompra(CompraRequest request, String emailComprador) {
        Usuario usuario = usuarioRepository.findByEmail(emailComprador)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        // 1. Armar la compra en estado PENDIENTE y calcular el total real
        //    (el total SIEMPRE se calcula en el backend con el precio de la base,
        //     nunca confiés en un monto que venga del frontend)
        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setEstado(Compra.Estado.PENDIENTE);
        compra.setFecha(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;
        List<DetalleCompra> detalles = new java.util.ArrayList<>();

        for (ItemCompraRequest item : request.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new IllegalStateException("Producto no encontrado: " + item.getProductoId()));

            DetalleCompra detalle = new DetalleCompra();
            detalle.setCompra(compra);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalles.add(detalle);

            total = total.add(producto.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
        }

        compra.setTotal(total);
        compra.setDetalles(detalles);
        compraRepository.save(compra); // guardamos como PENDIENTE primero

        // 2. Llamar a Mercado Pago para procesar el pago
        try {
            PaymentClient client = new PaymentClient();

            PaymentCreateRequest paymentRequest = PaymentCreateRequest.builder()
                    .transactionAmount(total)
                    .token(request.getToken())
                    .description("Compra #" + compra.getId() + " - Navegación Virtual")
                    .installments(request.getInstallments() != null ? request.getInstallments() : 1)
                    .paymentMethodId(request.getPaymentMethodId())
                    .issuerId(request.getIssuerId())
                    .payer(PaymentPayerRequest.builder()
                            .email(request.getPayerEmail())
                            .build())
                    .build();

            Payment payment = client.create(paymentRequest);

            compra.setMercadoPagoPaymentId(payment.getId().toString());

            switch (payment.getStatus()) {
                case "approved" -> {
                    compra.setEstado(Compra.Estado.PAGADA);
                    compra.setCodigoBoleta(generarCodigoBoleta());
                    notificarVentaAVendedores(detalles, compra);
                }
                case "rejected" -> compra.setEstado(Compra.Estado.RECHAZADA);
                default -> compra.setEstado(Compra.Estado.PENDIENTE); // in_process, pending, etc.
            }

        } catch (MPApiException e) {
            compra.setEstado(Compra.Estado.RECHAZADA);
            compraRepository.save(compra);
            throw new IllegalStateException("Error al procesar el pago: " + e.getApiResponse().getContent());
        } catch (MPException e) {
            compra.setEstado(Compra.Estado.RECHAZADA);
            compraRepository.save(compra);
            throw new IllegalStateException("Error de conexión con Mercado Pago");
        }

        compraRepository.save(compra);
        return toResponse(compra);
    }

    public List<CompraResponse> listarMisCompras(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));
        return compraRepository.findByUsuarioId(usuario.getId())
                .stream().map(this::toResponse).toList();
    }

    public CompraResponse obtenerPorCodigoBoleta(String codigo) {
        Compra compra = compraRepository.findByCodigoBoleta(codigo)
                .orElseThrow(() -> new IllegalStateException("Boleta no encontrada"));
        return toResponse(compra);
    }

    private String generarCodigoBoleta() {
        return "NV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private CompraResponse toResponse(Compra compra) {
        return new CompraResponse(compra.getId(), compra.getTotal(), compra.getEstado(),
                compra.getCodigoBoleta(), compra.getFecha());
    }

    private void notificarVentaAVendedores(List<DetalleCompra> detalles, Compra compra) {
        // Agrupamos por vendedor para mandar un solo email aunque compre varios productos del mismo stand
        Map<Usuario, List<DetalleCompra>> porVendedor = detalles.stream()
                .collect(Collectors.groupingBy(d -> d.getProducto().getVendedor()));

        porVendedor.forEach((vendedor, items) -> {
            StringBuilder cuerpo = new StringBuilder();
            cuerpo.append("¡Tenés una venta nueva!\n\n");
            cuerpo.append("Boleta: ").append(compra.getCodigoBoleta()).append("\n\n");
            cuerpo.append("Productos:\n");
            items.forEach(d -> cuerpo.append("- ")
                    .append(d.getProducto().getNombre())
                    .append(" x").append(d.getCantidad())
                    .append(" ($").append(d.getPrecioUnitario()).append(" c/u)\n"));

            emailService.enviar(vendedor.getEmail(), "Nueva venta en Navegación Virtual", cuerpo.toString());
        });
    }
}