package com.navvirtual.backend.service;

import com.navvirtual.backend.dto.BuffetRequest;
import com.navvirtual.backend.dto.BuffetResponse;
import com.navvirtual.backend.entity.Buffet;
import com.navvirtual.backend.entity.Evento;
import com.navvirtual.backend.entity.Usuario;
import com.navvirtual.backend.repository.BuffetRepository;
import com.navvirtual.backend.repository.EventoRepository;
import com.navvirtual.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuffetService {

    private final BuffetRepository buffetRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public BuffetResponse crear(BuffetRequest request) {
        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new IllegalStateException("Evento no encontrado"));

        Buffet buffet = new Buffet();
        buffet.setEvento(evento);

        if (request.getPropietarioId() != null) {
            Usuario propietario = usuarioRepository.findById(request.getPropietarioId())
                    .orElseThrow(() -> new IllegalStateException("Propietario no encontrado"));
            buffet.setPropietario(propietario);
        }

        buffetRepository.save(buffet);
        return toResponse(buffet);
    }

    public BuffetResponse obtenerPorEvento(Long eventoId) {
        Buffet buffet = buffetRepository.findByEventoId(eventoId)
                .orElseThrow(() -> new IllegalStateException("Este evento no tiene buffet configurado"));
        return toResponse(buffet);
    }

    public List<BuffetResponse> listarMisBuffets(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        return buffetRepository.findAll().stream()
                .filter(b -> esDueno(b, usuario) || esEmpleado(b, usuario))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Buffet buscarBuffet(Long id) {
        return buffetRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Buffet no encontrado"));
    }

    public void verificarAccesoBuffet(Buffet buffet, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        boolean esSuperadmin = usuario != null && usuario.getRoles().stream()
                .anyMatch(r -> r.getNombre().equals("ROLE_SUPERADMIN"));

        if (!esSuperadmin && usuario != null && !esDueno(buffet, usuario) && !esEmpleado(buffet, usuario)) {
            throw new IllegalStateException("No tenés permiso sobre este buffet");
        }
    }

    public BuffetResponse asignarEmpleado(Long buffetId, Long usuarioId, String emailSolicitante) {
        Buffet buffet = buscarBuffet(buffetId);
        verificarEsDuenoOSuperadmin(buffet, emailSolicitante);

        Usuario empleado = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        buffet.getEmpleados().add(empleado);
        buffetRepository.save(buffet);
        return toResponse(buffet);
    }

    public BuffetResponse quitarEmpleado(Long buffetId, Long usuarioId, String emailSolicitante) {
        Buffet buffet = buscarBuffet(buffetId);
        verificarEsDuenoOSuperadmin(buffet, emailSolicitante);

        buffet.getEmpleados().removeIf(u -> u.getId().equals(usuarioId));
        buffetRepository.save(buffet);
        return toResponse(buffet);
    }

    private void verificarEsDuenoOSuperadmin(Buffet buffet, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        boolean esSuperadmin = usuario != null && usuario.getRoles().stream()
                .anyMatch(r -> r.getNombre().equals("ROLE_SUPERADMIN"));

        if (!esSuperadmin && (usuario == null || !esDueno(buffet, usuario))) {
            throw new IllegalStateException("Solo el dueño del buffet puede hacer esto");
        }
    }

    private boolean esDueno(Buffet buffet, Usuario usuario) {
        return buffet.getPropietario() != null && buffet.getPropietario().getId().equals(usuario.getId());
    }

    private boolean esEmpleado(Buffet buffet, Usuario usuario) {
        return buffet.getEmpleados().stream().anyMatch(u -> u.getId().equals(usuario.getId()));
    }

    private BuffetResponse toResponse(Buffet buffet) {
        var empleadosIds = buffet.getEmpleados().stream().map(Usuario::getId).collect(Collectors.toSet());
        return new BuffetResponse(buffet.getId(), buffet.getEvento().getId(),
                buffet.getPropietario() != null ? buffet.getPropietario().getId() : null,
                empleadosIds);
    }
}