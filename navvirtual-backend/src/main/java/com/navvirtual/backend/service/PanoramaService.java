package com.navvirtual.backend.service;

import com.navvirtual.backend.dto.PanoramaRequest;
import com.navvirtual.backend.dto.PanoramaResponse;
import com.navvirtual.backend.entity.Evento;
import com.navvirtual.backend.entity.Panorama;
import com.navvirtual.backend.repository.EventoRepository;
import com.navvirtual.backend.repository.PanoramaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PanoramaService {

    private final PanoramaRepository panoramaRepository;
    private final EventoRepository eventoRepository;

    public PanoramaResponse crear(PanoramaRequest request) {
        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new IllegalStateException("Evento no encontrado"));

        if (request.isEsPuntoInicio()) {
            desmarcarOtrosPuntosInicio(request.getEventoId());   // <-- agregar
        }

        Panorama panorama = new Panorama();
        panorama.setNombre(request.getNombre());
        panorama.setImagenUrl(request.getImagenUrl());
        panorama.setEvento(evento);
        panorama.setEsPuntoInicio(request.isEsPuntoInicio());

        panoramaRepository.save(panorama);
        return toResponse(panorama);
    }

    public List<PanoramaResponse> listarPorEvento(Long eventoId) {
        return panoramaRepository.findByEventoId(eventoId).stream().map(this::toResponse).toList();
    }

    public PanoramaResponse obtenerPuntoInicio(Long eventoId) {
        Panorama panorama = panoramaRepository.findByEventoIdAndEsPuntoInicioTrue(eventoId)
                .orElseThrow(() -> new IllegalStateException("El evento no tiene punto de inicio configurado"));
        return toResponse(panorama);
    }

    private PanoramaResponse toResponse(Panorama p) {
        return new PanoramaResponse(p.getId(), p.getNombre(), p.getImagenUrl(), p.getEvento().getId(), p.isEsPuntoInicio());
    }
    public PanoramaResponse actualizar(Long id, PanoramaRequest request) {
        Panorama panorama = panoramaRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Panorama no encontrado"));

        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new IllegalStateException("Evento no encontrado"));
        if (request.isEsPuntoInicio()) {
            desmarcarOtrosPuntosInicio(request.getEventoId());   // <-- agregar
        }

        panorama.setNombre(request.getNombre());
        panorama.setImagenUrl(request.getImagenUrl());
        panorama.setEvento(evento);
        panorama.setEsPuntoInicio(request.isEsPuntoInicio());

        panoramaRepository.save(panorama);
        return toResponse(panorama);
    }

    public void eliminar(Long id) {
        if (!panoramaRepository.existsById(id)) {
            throw new IllegalStateException("Panorama no encontrado");
        }
        panoramaRepository.deleteById(id);
    }
    private void desmarcarOtrosPuntosInicio(Long eventoId) {
        List<Panorama> panoramas = panoramaRepository.findByEventoId(eventoId);
        panoramas.forEach(p -> {
            if (p.isEsPuntoInicio()) {
                p.setEsPuntoInicio(false);
                panoramaRepository.save(p);
            }
        });
    }
}