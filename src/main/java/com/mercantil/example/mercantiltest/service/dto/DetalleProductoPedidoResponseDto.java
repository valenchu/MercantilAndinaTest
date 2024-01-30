package com.mercantil.example.mercantiltest.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleProductoPedidoResponseDto {
    private String producto;
    private String nombre;
    private Double cantidad;
    private Double importe;
}
