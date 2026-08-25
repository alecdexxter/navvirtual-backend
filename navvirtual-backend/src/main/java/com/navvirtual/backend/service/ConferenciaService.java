package com.navvirtual.backend.service;

import com.navvirtual.backend.dto.ConferenciaRequest;
import com.navvirtual.backend.dto.ConferenciaResponse;
import com.navvirtual.backend.entity.Conferencia;
import com.navvirtual.backend.entity.Evento;
import com.navvirtual.backend.repository.ConferenciaRepository;
import com.navvirtual.backend.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConferenciaService {

    private final ConferenciaRepository conferenciaRepository;
    private final EventoRepository eventoRepository;

    public ConferenciaResponse crear(ConferenciaRequest request) {
        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new IllegalStateException("Evento no encontrado"));

        Conferencia conferencia = new Conferencia();
        conferencia.setEvento(evento);
        conferencia.setTitulo(request.getTitulo());
        conferencia.setDescripcion(request.getDescripcion());
        conferencia.setTipo(request.getTipo());
        conferencia.setHorario(request.getHorario());

        conferenciaRepository.save(conferencia);
        return toResponse(conferencia);
    }

    public List<ConferenciaResponse> listarPorEvento(Long eventoId) {
        return conferenciaRepository.findByEventoIdOrderByHorarioAsc(eventoId)
                .stream().map(this::toResponse).toList();
    }
    public void eliminar(Long id) {
        if (!conferenciaRepository.existsById(id)) {
            throw new IllegalStateException("Conferencia no encontrada");
        }
        conferenciaRepository.deleteById(id);
    }

    private ConferenciaResponse toResponse(Conferencia c) {
        return new ConferenciaResponse(c.getId(), c.getTitulo(), c.getDescripcion(), c.getTipo(), c.getHorario());
    }
}