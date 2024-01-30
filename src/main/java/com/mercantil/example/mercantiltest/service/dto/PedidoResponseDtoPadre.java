package com.mercantil.example.mercantiltest.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDtoPadre {
    private LocalDate fecha;
    private String direccion;
    private String email;
    private String telefono;
    private String horario;
    private List<DetalleProductoPedidoResponseDto> detalle;
    private Double total;
    private Boolean descuento;
}
