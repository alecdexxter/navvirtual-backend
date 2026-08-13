package com.navvirtual.backend.repository;

import com.navvirtual.backend.entity.Buffet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BuffetRepository extends JpaRepository<Buffet, Long> {
    Optional<Buffet> findByEventoId(Long eventoId);
}