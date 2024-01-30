package com.mercantil.example.mercantiltest.service;

import com.mercantil.example.mercantiltest.persistence.entity.PedidoCabeceraEntity;
import com.mercantil.example.mercantiltest.service.dto.CrearPedidoDto;
import com.mercantil.example.mercantiltest.service.dto.PedidoResponseDtoPadre;
import com.mercantil.example.mercantiltest.service.dto.ProductoConCantidadDto;

import java.util.List;
import java.util.UUID;

public interface PedidoCabeceraServiceImpl extends CrudBaseService<PedidoCabeceraEntity, UUID>{

    PedidoCabeceraEntity save(CrearPedidoDto entity, List<ProductoConCantidadDto> entityList);

    List<PedidoResponseDtoPadre> getPedidoFecha(String fecha);
}
