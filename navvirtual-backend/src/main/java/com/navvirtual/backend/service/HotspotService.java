package com.navvirtual.backend.service;

import com.navvirtual.backend.dto.HotspotRequest;
import com.navvirtual.backend.dto.HotspotResponse;
import com.navvirtual.backend.entity.Hotspot;
import com.navvirtual.backend.entity.Panorama;
import com.navvirtual.backend.entity.Stand;
import com.navvirtual.backend.repository.HotspotRepository;
import com.navvirtual.backend.repository.PanoramaRepository;
import com.navvirtual.backend.repository.StandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotspotService {

    private final HotspotRepository hotspotRepository;
    private final PanoramaRepository panoramaRepository;
    private final StandRepository standRepository;

    public HotspotResponse crear(HotspotRequest request) {
        if (request.getTipo() == Hotspot.Tipo.NAVEGACION && request.getPanoramaDestinoId() == null) {
            throw new IllegalStateException("Un hotspot de NAVEGACION necesita panoramaDestinoId");
        }
        if (request.getTipo() == Hotspot.Tipo.STAND && request.getStandId() == null) {
            throw new IllegalStateException("Un hotspot de STAND necesita standId");
        }

        Panorama origen = panoramaRepository.findById(request.getPanoramaOrigenId())
                .orElseThrow(() -> new IllegalStateException("Panorama origen no encontrado"));

        Panorama destino = request.getPanoramaDestinoId() != null
                ? panoramaRepository.findById(request.getPanoramaDestinoId())
                .orElseThrow(() -> new IllegalStateException("Panorama destino no encontrado"))
                : null;

        Stand stand = request.getStandId() != null
                ? standRepository.findById(request.getStandId())
                .orElseThrow(() -> new IllegalStateException("Stand no encontrado"))
                : null;

        Hotspot hotspot = new Hotspot();
        hotspot.setPanoramaOrigen(origen);
        hotspot.setPanoramaDestino(destino);
        hotspot.setStand(stand);
        hotspot.setTipo(request.getTipo());
        hotspot.setYaw(request.getYaw());
        hotspot.setPitch(request.getPitch());

        hotspotRepository.save(hotspot);
        return toResponse(hotspot);
    }

    public List<HotspotResponse> listarPorPanorama(Long panoramaId) {
        return hotspotRepository.findByPanoramaOrigenId(panoramaId).stream().map(this::toResponse).toList();
    }

    private HotspotResponse toResponse(Hotspot h) {
        return new HotspotResponse(
                h.getId(), h.getPanoramaOrigen().getId(),
                h.getPanoramaDestino() != null ? h.getPanoramaDestino().getId() : null,
                h.getStand() != null ? h.getStand().getId() : null,
                h.getTipo(), h.getYaw(), h.getPitch()
        );
    }
    public HotspotResponse actualizar(Long id, HotspotRequest request) {
        Hotspot hotspot = hotspotRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Hotspot no encontrado"));

        if (request.getTipo() == Hotspot.Tipo.NAVEGACION && request.getPanoramaDestinoId() == null) {
            throw new IllegalStateException("Un hotspot de NAVEGACION necesita panoramaDestinoId");
        }
        if (request.getTipo() == Hotspot.Tipo.STAND && request.getStandId() == null) {
            throw new IllegalStateException("Un hotspot de STAND necesita standId");
        }

        Panorama origen = panoramaRepository.findById(request.getPanoramaOrigenId())
                .orElseThrow(() -> new IllegalStateException("Panorama origen no encontrado"));

        Panorama destino = request.getPanoramaDestinoId() != null
                ? panoramaRepository.findById(request.getPanoramaDestinoId())
                .orElseThrow(() -> new IllegalStateException("Panorama destino no encontrado"))
                : null;

        Stand stand = request.getStandId() != null
                ? standRepository.findById(request.getStandId())
                .orElseThrow(() -> new IllegalStateException("Stand no encontrado"))
                : null;

        hotspot.setPanoramaOrigen(origen);
        hotspot.setPanoramaDestino(destino);
        hotspot.setStand(stand);
        hotspot.setTipo(request.getTipo());
        hotspot.setYaw(request.getYaw());
        hotspot.setPitch(request.getPitch());

        hotspotRepository.save(hotspot);
        return toResponse(hotspot);
    }

    public void eliminar(Long id) {
        if (!hotspotRepository.existsById(id)) {
            throw new IllegalStateException("Hotspot no encontrado");
        }
        hotspotRepository.deleteById(id);
    }
}