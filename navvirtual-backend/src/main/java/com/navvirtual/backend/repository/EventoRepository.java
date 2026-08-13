package com.navvirtual.backend.repository;

import com.navvirtual.backend.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByFechaFinAfter(LocalDateTime ahora); // eventos en curso o próximos
}