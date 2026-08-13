package com.navvirtual.backend.dto;

import com.navvirtual.backend.entity.Hotspot;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotspotResponse {
    private Long id;
    private Long panoramaOrigenId;
    private Long panoramaDestinoId;
    private Long standId;
    private Hotspot.Tipo tipo;
    private Double yaw;
    private Double pitch;
}