package com.navvirtual.backend.dto;

import com.navvirtual.backend.entity.Hotspot;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HotspotRequest {
    @NotNull private Long panoramaOrigenId;
    private Long panoramaDestinoId; // requerido si tipo = NAVEGACION
    private Long standId;           // requerido si tipo = STAND
    @NotNull private Hotspot.Tipo tipo;
    @NotNull private Double yaw;
    @NotNull private Double pitch;
}