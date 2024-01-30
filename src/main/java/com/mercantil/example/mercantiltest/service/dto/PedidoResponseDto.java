package com.mercantil.example.mercantiltest.service.dto;

import com.mercantil.example.mercantiltest.util.enums.EstadoPedido;
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
public class PedidoResponseDto extends PedidoResponseDtoPadre {
    private EstadoPedido estado;
}
