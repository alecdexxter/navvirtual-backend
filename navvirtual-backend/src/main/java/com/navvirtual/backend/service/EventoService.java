package com.navvirtual.backend.service;

import com.navvirtual.backend.dto.EventoRequest;
import com.navvirtual.backend.dto.EventoResponse;
import com.navvirtual.backend.entity.Evento;
import com.navvirtual.backend.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoResponse crear(EventoRequest request) {
        Evento evento = new Evento();
        evento.setNombre(request.getNombre());
        evento.setDescripcion(request.getDescripcion());
        evento.setFechaInicio(request.getFechaInicio());
        evento.setFechaFin(request.getFechaFin());
        eventoRepository.save(evento);
        return toResponse(evento);
    }

    public List<EventoResponse> listarVigentesOProximos() {
        return eventoRepository.findByFechaFinAfter(LocalDateTime.now())
                .stream().map(this::toResponse).toList();
    }
    public List<EventoResponse> listarTodos() {
        return eventoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public EventoResponse obtenerPorId(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Evento no encontrado"));
        return toResponse(evento);
    }

    public EventoResponse actualizar(Long id, EventoRequest request) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Evento no encontrado"));
        evento.setNombre(request.getNombre());
        evento.setDescripcion(request.getDescripcion());
        evento.setFechaInicio(request.getFechaInicio());
        evento.setFechaFin(request.getFechaFin());
        eventoRepository.save(evento);
        return toResponse(evento);
    }

    public void eliminar(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new IllegalStateException("Evento no encontrado");
        }
        eventoRepository.deleteById(id);
    }

    private EventoResponse toResponse(Evento evento) {
        return new EventoResponse(evento.getId(), evento.getNombre(), evento.getDescripcion(),
                evento.getFechaInicio(), evento.getFechaFin());
    }
}