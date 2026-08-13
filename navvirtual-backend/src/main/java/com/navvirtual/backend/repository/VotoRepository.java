package com.navvirtual.backend.repository;

import com.navvirtual.backend.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    List<Voto> findByStandId(Long standId);
    Optional<Voto> findByUsuarioIdAndStandId(Long usuarioId, Long standId);
    long countByStandId(Long standId); // para ranking de favoritos
}