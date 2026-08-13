package com.navvirtual.backend.repository;

import com.navvirtual.backend.entity.Panorama;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PanoramaRepository extends JpaRepository<Panorama, Long> {
    List<Panorama> findByEventoId(Long eventoId);
    Optional<Panorama> findByEventoIdAndEsPuntoInicioTrue(Long eventoId);
}