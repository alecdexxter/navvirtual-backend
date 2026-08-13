package com.navvirtual.backend.service;

import com.navvirtual.backend.dto.ComunicadoRequest;
import com.navvirtual.backend.dto.ComunicadoResponse;
import com.navvirtual.backend.entity.Comunicado;
import com.navvirtual.backend.entity.Evento;
import com.navvirtual.backend.entity.Usuario;
import com.navvirtual.backend.repository.ComunicadoRepository;
import com.navvirtual.backend.repository.EventoRepository;
import com.navvirtual.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComunicadoService {

    private final ComunicadoRepository comunicadoRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public ComunicadoResponse crear(ComunicadoRequest request, String emailGerente) {
        Usuario gerente = usuarioRepository.findByEmail(emailGerente)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new IllegalStateException("Evento no encontrado"));

        Comunicado comunicado = new Comunicado();
        comunicado.setGerente(gerente);
        comunicado.setEvento(evento);
        comunicado.setMensaje(request.getMensaje());
        comunicado.setFecha(LocalDateTime.now());

        comunicadoRepository.save(comunicado);
        return toResponse(comunicado);
    }

    public List<ComunicadoResponse> listarPorEvento(Long eventoId) {
        return comunicadoRepository.findByEventoIdOrderByFechaDesc(eventoId)
                .stream().map(this::toResponse).toList();
    }

    private ComunicadoResponse toResponse(Comunicado c) {
        return new ComunicadoResponse(c.getId(), c.getMensaje(),
                c.getGerente().getNombre() + " " + c.getGerente().getApellido(), c.getFecha());
    }
}