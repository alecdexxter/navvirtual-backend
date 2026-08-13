package com.navvirtual.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OpcionRequest {
    @NotBlank private String texto;
    private boolean esCorrecta;
}