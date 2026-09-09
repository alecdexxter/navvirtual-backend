package com.navvirtual.backend.repository;

import com.navvirtual.backend.entity.Stand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandRepository extends JpaRepository<Stand, Long> {

    List<Stand> findByEventoId(Long eventoId);

    List<Stand> findByPropietarioId(Long propietarioId);

    @Query("SELECT DISTINCT s FROM Stand s LEFT JOIN FETCH s.empleados e WHERE s.propietario.id = :usuarioId OR e.id = :usuarioId")
    List<Stand> findByPropietarioIdOrEmpleadoId(@Param("usuarioId") Long usuarioId);
}