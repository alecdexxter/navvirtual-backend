package com.navvirtual.backend.repository;

import com.navvirtual.backend.entity.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    List<Pregunta> findByStandId(Long standId);
}