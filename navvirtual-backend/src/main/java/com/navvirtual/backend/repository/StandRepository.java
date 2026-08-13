package com.navvirtual.backend.repository;

import com.navvirtual.backend.entity.Stand;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StandRepository extends JpaRepository<Stand, Long> {
    List<Stand> findByEventoId(Long eventoId);
    List<Stand> findByPropietarioId(Long propietarioId); // reemplaza al método viejo
}