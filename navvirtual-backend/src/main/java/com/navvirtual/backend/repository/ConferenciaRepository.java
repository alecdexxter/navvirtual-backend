package com.navvirtual.backend.repository;

import com.navvirtual.backend.entity.Conferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConferenciaRepository extends JpaRepository<Conferencia, Long> {
    List<Conferencia> findByEventoIdOrderByHorarioAsc(Long eventoId);
}