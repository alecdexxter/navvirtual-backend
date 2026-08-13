package com.navvirtual.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CompraRequest {

    @NotEmpty
    private List<ItemCompraRequest> items;

    // Datos que entrega el Payment Brick en su callback onSubmit
    @NotBlank
    private String token;               // token de la tarjeta (o null si es pago con cuenta MP)

    @NotBlank
    private String paymentMethodId;     // "visa", "master", "account_money", etc.

    private String issuerId;
    private Integer installments;       // cuotas

    @NotNull
    private String payerEmail;
}