package com.navvirtual.backend.repository;

import com.navvirtual.backend.entity.OpcionRespuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OpcionRespuestaRepository extends JpaRepository<OpcionRespuesta, Long> {
    List<OpcionRespuesta> findByPreguntaId(Long preguntaId);
}