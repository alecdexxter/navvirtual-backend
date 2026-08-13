package com.navvirtual.backend.repository;

import com.navvirtual.backend.entity.Comunicado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComunicadoRepository extends JpaRepository<Comunicado, Long> {
    List<Comunicado> findByEventoIdOrderByFechaDesc(Long eventoId);
}