package com.mercantil.example.mercantiltest.service;

import com.mercantil.example.mercantiltest.persistence.entity.PedidoCabeceraEntity;
import com.mercantil.example.mercantiltest.persistence.entity.PedidoDetalleEntity;
import com.mercantil.example.mercantiltest.service.dto.PedidoResponseDto;
import com.mercantil.example.mercantiltest.service.dto.PedidoResponseDtoPadre;
import com.mercantil.example.mercantiltest.service.dto.ProductoConCantidadDto;

import java.util.List;
import java.util.UUID;

public interface PedidoDetalleServiceImpl extends CrudBaseService<PedidoDetalleEntity, UUID> {
    PedidoResponseDto save(PedidoCabeceraEntity pedidoCabecera, List<ProductoConCantidadDto> entityList);
}
