package com.navvirtual.backend.service;

import com.navvirtual.backend.entity.Rol;
import com.navvirtual.backend.entity.Usuario;
import com.navvirtual.backend.repository.RolRepository;
import com.navvirtual.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RolService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @Transactional
    public void asignar(Long usuarioId, String nombreRol) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        Rol rol = rolRepository.findByNombre(nombreRol)
                .orElseGet(() -> rolRepository.findByNombre("ROLE_" + nombreRol)
                        .orElseThrow(() -> new IllegalStateException("Rol no encontrado: " + nombreRol)));

        usuario.getRoles().add(rol);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void quitar(Long usuarioId, String nombreRol) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        usuario.getRoles().removeIf(r -> r.getNombre().equalsIgnoreCase(nombreRol) || r.getNombre().equalsIgnoreCase("ROLE_" + nombreRol));
        usuarioRepository.save(usuario);
    }
}