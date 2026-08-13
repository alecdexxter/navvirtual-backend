package com.navvirtual.backend.service;

import com.navvirtual.backend.dto.*;
import com.navvirtual.backend.entity.OpcionRespuesta;
import com.navvirtual.backend.entity.Pregunta;
import com.navvirtual.backend.entity.Stand;
import com.navvirtual.backend.repository.OpcionRespuestaRepository;
import com.navvirtual.backend.repository.PreguntaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreguntaService {

    private final PreguntaRepository preguntaRepository;
    private final OpcionRespuestaRepository opcionRepository;
    private final StandService standService;

    public PreguntaResponse crear(PreguntaRequest request, String email) {
        Stand stand = standService.buscarStand(request.getStandId());
        standService.verificarAccesoStand(stand, email);

        boolean hayCorrecta = request.getOpciones().stream().anyMatch(OpcionRequest::isEsCorrecta);
        if (!hayCorrecta) {
            throw new IllegalStateException("La pregunta necesita al menos una opción correcta");
        }

        Pregunta pregunta = new Pregunta();
        pregunta.setStand(stand);
        pregunta.setTexto(request.getTexto());

        List<OpcionRespuesta> opciones = request.getOpciones().stream().map(o -> {
            OpcionRespuesta opcion = new OpcionRespuesta();
            opcion.setPregunta(pregunta);
            opcion.setTexto(o.getTexto());
            opcion.setEsCorrecta(o.isEsCorrecta());
            return opcion;
        }).toList();

        pregunta.setOpciones(opciones);
        preguntaRepository.save(pregunta);

        return toResponse(pregunta);
    }

    public List<PreguntaResponse> listarPorStand(Long standId) {
        return preguntaRepository.findByStandId(standId).stream().map(this::toResponse).toList();
    }

    public void eliminar(Long preguntaId, String email) {
        Pregunta pregunta = preguntaRepository.findById(preguntaId)
                .orElseThrow(() -> new IllegalStateException("Pregunta no encontrada"));
        standService.verificarAccesoStand(pregunta.getStand(), email);
        preguntaRepository.delete(pregunta);
    }

    public RespuestaResultado responder(Long preguntaId, RespuestaRequest request) {
        OpcionRespuesta opcion = opcionRepository.findById(request.getOpcionId())
                .orElseThrow(() -> new IllegalStateException("Opción no encontrada"));

        if (!opcion.getPregunta().getId().equals(preguntaId)) {
            throw new IllegalStateException("La opción no pertenece a esta pregunta");
        }

        if (opcion.isEsCorrecta()) {
            return new RespuestaResultado(true, "¡Correcto!");
        }
        return new RespuestaResultado(false, "Incorrecto, ¡seguí intentando!");
    }

    private PreguntaResponse toResponse(Pregunta p) {
        List<OpcionResponse> opciones = p.getOpciones().stream()
                .map(o -> new OpcionResponse(o.getId(), o.getTexto()))
                .toList();
        return new PreguntaResponse(p.getId(), p.getTexto(), opciones);
    }
}