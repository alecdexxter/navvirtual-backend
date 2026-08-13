package com.navvirtual.backend.repository;

import com.navvirtual.backend.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByUsuarioId(Long usuarioId);
    Optional<Compra> findByCodigoBoleta(String codigoBoleta);
    Optional<Compra> findByMercadoPagoPaymentId(String paymentId);
}