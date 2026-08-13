package com.navvirtual.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PreguntaRequest {
    @NotNull private Long standId;
    @NotBlank private String texto;
    @NotEmpty private List<OpcionRequest> opciones;
}