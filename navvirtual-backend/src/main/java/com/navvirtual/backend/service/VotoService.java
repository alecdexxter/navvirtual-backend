package com.navvirtual.backend.service;

import com.navvirtual.backend.dto.VotoResponse;
import com.navvirtual.backend.entity.Stand;
import com.navvirtual.backend.entity.Usuario;
import com.navvirtual.backend.entity.Voto;
import com.navvirtual.backend.repository.StandRepository;
import com.navvirtual.backend.repository.UsuarioRepository;
import com.navvirtual.backend.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;
    private final StandRepository standRepository;
    private final UsuarioRepository usuarioRepository;

    public VotoResponse votar(Long standId, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        Stand stand = standRepository.findById(standId)
                .orElseThrow(() -> new IllegalStateException("Stand no encontrado"));

        if (votoRepository.findByUsuarioIdAndStandId(usuario.getId(), standId).isPresent()) {
            throw new IllegalStateException("Ya votaste por este stand");
        }

        Voto voto = new Voto();
        voto.setUsuario(usuario);
        voto.setStand(stand);
        votoRepository.save(voto);

        return obtenerEstado(standId, email);
    }

    public VotoResponse obtenerEstado(Long standId, String email) {
        long total = votoRepository.countByStandId(standId);
        boolean yaVoto = false;

        if (email != null) {
            Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
            if (usuario != null) {
                yaVoto = votoRepository.findByUsuarioIdAndStandId(usuario.getId(), standId).isPresent();
            }
        }

        return new VotoResponse(standId, total, yaVoto);
    }
}