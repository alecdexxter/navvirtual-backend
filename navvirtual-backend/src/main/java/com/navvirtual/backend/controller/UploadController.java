package com.navvirtual.backend.controller;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    @PostMapping
    public ResponseEntity<Map<String, String>> subir(@RequestParam("archivo") MultipartFile archivo) throws IOException {
        if (archivo.isEmpty()) {
            throw new IllegalStateException("El archivo está vacío");
        }

        String extension = "";
        String nombreOriginal = archivo.getOriginalFilename();
        if (nombreOriginal != null && nombreOriginal.contains(".")) {
            extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        }

        String nombreArchivo = UUID.randomUUID() + extension;
        Path directorio = Path.of("uploads");
        Files.createDirectories(directorio);
        Path destino = directorio.resolve(nombreArchivo);
        archivo.transferTo(destino);

        String url = "http://localhost:8082/uploads/" + nombreArchivo;
        return ResponseEntity.ok(Map.of("url", url));
    }
}