package com.navvirtual.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VotoResponse {
    private Long standId;
    private long totalVotos;
    private boolean yaVoto;
}