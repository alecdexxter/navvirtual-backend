package com.navvirtual.backend.repository;

import com.navvirtual.backend.entity.Hotspot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HotspotRepository extends JpaRepository<Hotspot, Long> {
    List<Hotspot> findByPanoramaOrigenId(Long panoramaId);
}