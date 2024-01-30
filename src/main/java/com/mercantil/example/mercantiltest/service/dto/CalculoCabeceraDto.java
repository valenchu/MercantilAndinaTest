package com.mercantil.example.mercantiltest.service.dto;

import com.mercantil.example.mercantiltest.util.enums.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculoCabeceraDto {

    private Double montoTotal;
    private Boolean aplicoDescuento;
    private EstadoPedido estado;
}
