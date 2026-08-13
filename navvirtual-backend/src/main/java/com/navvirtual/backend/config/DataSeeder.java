package com.navvirtual.backend.config;

import com.navvirtual.backend.entity.Rol;
import com.navvirtual.backend.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RolRepository rolRepository;

    @Override
    public void run(String... args) {
        crearRolSiNoExiste("ROLE_USUARIO");          // Cliente
        crearRolSiNoExiste("ROLE_DUENIO_STAND");
        crearRolSiNoExiste("ROLE_EMPLEADO_STAND");
        crearRolSiNoExiste("ROLE_DUENIO_BUFFET");
        crearRolSiNoExiste("ROLE_EMPLEADO_BUFFET");
        crearRolSiNoExiste("ROLE_SUPERADMIN");
    }

    private void crearRolSiNoExiste(String nombre) {
        if (rolRepository.findByNombre(nombre).isEmpty()) {
            Rol rol = new Rol();
            rol.setNombre(nombre);
            rolRepository.save(rol);
        }
    }
}