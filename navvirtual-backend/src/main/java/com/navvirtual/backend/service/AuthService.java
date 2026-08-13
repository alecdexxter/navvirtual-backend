package com.navvirtual.backend.service;

import com.navvirtual.backend.dto.AuthResponse;
import com.navvirtual.backend.dto.LoginRequest;
import com.navvirtual.backend.dto.RegistroRequest;
import com.navvirtual.backend.entity.Rol;
import com.navvirtual.backend.entity.Usuario;
import com.navvirtual.backend.repository.RolRepository;
import com.navvirtual.backend.repository.UsuarioRepository;
import com.navvirtual.backend.security.JwtService;
import com.navvirtual.backend.security.UsuarioDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse registrar(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        Rol rolUsuario = rolRepository.findByNombre("ROLE_USUARIO")
                .orElseThrow(() -> new IllegalStateException("Rol ROLE_USUARIO no existe. Sembralo primero."));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolUsuario);

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRoles(roles);

        // Guardamos y recuperamos la entidad para asegurar el ID autogenerado
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        UsuarioDetailsImpl userDetails = new UsuarioDetailsImpl(usuarioGuardado);
        String token = jwtService.generarToken(userDetails);

        // usuarioGuardado.getId() como primer argumento
        return new AuthResponse(
                usuarioGuardado.getId(),
                token,
                usuarioGuardado.getEmail(),
                usuarioGuardado.getNombre(),
                usuarioGuardado.getRoles().stream().map(Rol::getNombre).toList()
        );
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        UsuarioDetailsImpl userDetails = new UsuarioDetailsImpl(usuario);
        String token = jwtService.generarToken(userDetails);

        // usuario.getId() como primer argumento
        return new AuthResponse(
                usuario.getId(),
                token,
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getRoles().stream().map(Rol::getNombre).toList()
        );
    }
}