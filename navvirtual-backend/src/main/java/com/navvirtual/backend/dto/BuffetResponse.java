package com.navvirtual.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
public class BuffetResponse {
    private Long id;
    private Long eventoId;
    private Long propietarioId;
    private Set<Long> empleadosIds;
}