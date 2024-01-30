package com.mercantil.example.mercantiltest.service;

import com.mercantil.example.mercantiltest.persistence.entity.PedidoDetalleEntity;
import com.mercantil.example.mercantiltest.service.dto.CrearPedidoDto;
import com.mercantil.example.mercantiltest.service.dto.PedidoResponseDto;

public interface ManangerPedidoImpl {

    PedidoResponseDto createPedido(CrearPedidoDto crearPedidoDto);
}
