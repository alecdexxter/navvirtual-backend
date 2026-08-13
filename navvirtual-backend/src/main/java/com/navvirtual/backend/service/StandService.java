package com.navvirtual.backend.service;

import com.navvirtual.backend.dto.StandRequest;
import com.navvirtual.backend.dto.StandResponse;
import com.navvirtual.backend.entity.Evento;
import com.navvirtual.backend.entity.Stand;
import com.navvirtual.backend.entity.Usuario;
import com.navvirtual.backend.repository.EventoRepository;
import com.navvirtual.backend.repository.StandRepository;
import com.navvirtual.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StandService {

    private final StandRepository standRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    // SUPERADMIN crea el "cascarón" del stand y le asigna un dueño
    public StandResponse crear(StandRequest request) {
        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new IllegalStateException("Evento no encontrado"));

        Stand stand = new Stand();
        stand.setNombre(request.getNombre());
        stand.setDescripcion(request.getDescripcion());
        stand.setImagenPortadaUrl(request.getImagenPortadaUrl());
        stand.setVideoUrl(request.getVideoUrl());
        stand.setEvento(evento);

        if (request.getPropietarioId() != null) {
            Usuario propietario = usuarioRepository.findById(request.getPropietarioId())
                    .orElseThrow(() -> new IllegalStateException("Propietario no encontrado"));
            stand.setPropietario(propietario);
        }

        standRepository.save(stand);
        return toResponse(stand);
    }
    public List<StandResponse> listarPorEvento(Long eventoId) {
        return standRepository.findByEventoId(eventoId).stream().map(this::toResponse).toList();
    }

    public List<StandResponse> listarMisStands(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));


        List<Stand> propios = standRepository.findByPropietarioId(usuario.getId());
        List<Stand> comoEmpleado = standRepository.findAll().stream()
                .filter(s -> s.getEmpleados().stream().anyMatch(u -> u.getId().equals(usuario.getId())))
                .toList();

        java.util.Set<Long> idsVistos = new java.util.HashSet<>();
        List<Stand> todos = new java.util.ArrayList<>();
        for (Stand s : propios) if (idsVistos.add(s.getId())) todos.add(s);
        for (Stand s : comoEmpleado) if (idsVistos.add(s.getId())) todos.add(s);

        return todos.stream().map(this::toResponse).toList();
    }

    public StandResponse obtenerPorId(Long id) {
        return toResponse(buscarStand(id));
    }

    // Dueño, empleado asignado, o superadmin pueden editar el contenido
    public StandResponse actualizar(Long id, StandRequest request, String email) {
        Stand stand = buscarStand(id);
        verificarAccesoStand(stand, email);

        stand.setNombre(request.getNombre());
        stand.setDescripcion(request.getDescripcion());
        stand.setImagenPortadaUrl(request.getImagenPortadaUrl());
        stand.setVideoUrl(request.getVideoUrl());

        standRepository.save(stand);
        return toResponse(stand);
    }
    //asignar propietario
    public StandResponse asignarPropietario(Long standId, Long propietarioId) {
        Stand stand = buscarStand(standId);
        Usuario propietario = usuarioRepository.findById(propietarioId)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        stand.setPropietario(propietario);
        standRepository.save(stand);
        return toResponse(stand);
    }

    // Solo el dueño (o superadmin) puede asignar/quitar empleados
    public StandResponse asignarEmpleado(Long standId, Long usuarioId, String emailSolicitante) {
        Stand stand = buscarStand(standId);
        verificarEsDuenoOSuperadmin(stand, emailSolicitante);

        Usuario empleado = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        stand.getEmpleados().add(empleado);
        standRepository.save(stand);
        return toResponse(stand);
    }

    public StandResponse quitarEmpleado(Long standId, Long usuarioId, String emailSolicitante) {
        Stand stand = buscarStand(standId);
        verificarEsDuenoOSuperadmin(stand, emailSolicitante);

        stand.getEmpleados().removeIf(u -> u.getId().equals(usuarioId));
        standRepository.save(stand);
        return toResponse(stand);
    }

    // ---- Métodos de acceso, reutilizables desde ProductoService y PreguntaService ----

    public Stand buscarStand(Long id) {
        return standRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Stand no encontrado"));
    }

    public void verificarAccesoStand(Stand stand, String email) {
        boolean esSuperadmin = tieneRol(email, "ROLE_SUPERADMIN");
        boolean esDueno = stand.getPropietario() != null && stand.getPropietario().getEmail().equals(email);
        boolean esEmpleado = stand.getEmpleados().stream().anyMatch(u -> u.getEmail().equals(email));

        if (!esSuperadmin && !esDueno && !esEmpleado) {
            throw new IllegalStateException("No tenés permiso sobre este stand");
        }
    }

    private void verificarEsDuenoOSuperadmin(Stand stand, String email) {
        boolean esSuperadmin = tieneRol(email, "ROLE_SUPERADMIN");
        boolean esDueno = stand.getPropietario() != null && stand.getPropietario().getEmail().equals(email);

        if (!esSuperadmin && !esDueno) {
            throw new IllegalStateException("Solo el dueño del stand puede hacer esto");
        }
    }

    private boolean tieneRol(String email, String nombreRol) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario == null) return false;
        return usuario.getRoles().stream().anyMatch(r -> r.getNombre().equals(nombreRol));
    }

    private StandResponse toResponse(Stand stand) {
        Set<Long> empleadosIds = stand.getEmpleados().stream().map(Usuario::getId).collect(java.util.stream.Collectors.toSet());
        return new StandResponse(
                stand.getId(), stand.getNombre(), stand.getDescripcion(),
                stand.getImagenPortadaUrl(), stand.getVideoUrl(),
                stand.getEvento().getId(),
                stand.getPropietario() != null ? stand.getPropietario().getId() : null,
                empleadosIds
        );
    }
}