package com.mercantil.example.mercantiltest.service;

import com.mercantil.example.mercantiltest.exceptions.ErrorListPersonalizadoPedido;
import com.mercantil.example.mercantiltest.exceptions.dto.ErrorDto;
import com.mercantil.example.mercantiltest.persistence.entity.PedidoCabeceraEntity;
import com.mercantil.example.mercantiltest.persistence.entity.PedidoDetalleEntity;
import com.mercantil.example.mercantiltest.persistence.repository.PedidoDetalleRepository;
import com.mercantil.example.mercantiltest.service.dto.PedidoResponseDto;
import com.mercantil.example.mercantiltest.service.dto.ProductoConCantidadDto;
import com.mercantil.example.mercantiltest.util.mapper.CrearPedidoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PedidoDetalleServiceService implements PedidoDetalleServiceImpl {

    private final PedidoDetalleRepository pedidoDetalleRepository;
    private final CrearPedidoMapper crearPedidoMapper;

    @Autowired
    public PedidoDetalleServiceService(PedidoDetalleRepository pedidoDetalleRepository, CrearPedidoMapper crearPedidoMapper) {
        this.pedidoDetalleRepository = pedidoDetalleRepository;
        this.crearPedidoMapper = crearPedidoMapper;
    }

    @Override
    public List<PedidoDetalleEntity> getAll() {
        return pedidoDetalleRepository.findAll();
    }

    @Override
    public PedidoDetalleEntity getById(UUID uuid) {
        return null;
    }

    @Override
    public PedidoDetalleEntity save(PedidoDetalleEntity entity) {
        return null;
    }

    @Override
    public List<PedidoDetalleEntity> saveAll(List<PedidoDetalleEntity> entity) {
        return null;
    }

    @Override
    public PedidoDetalleEntity update(UUID uuid, PedidoDetalleEntity entity) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public PedidoResponseDto save(PedidoCabeceraEntity pedidoCabecera, List<ProductoConCantidadDto> entityList) {
        validateDatosCrearDetalle(pedidoCabecera, entityList);
        List<PedidoDetalleEntity> pedidoDetalleEntity = new ArrayList<>();
        entityList.forEach(product -> {
            pedidoDetalleEntity.add(pedidoDetalleRepository.save(crearPedidoMapper.mapCabeceraProductoToDetalle(pedidoCabecera, product)));
        });

        return crearPedidoMapper.mapPedidoCabeceraToResponseDto(pedidoDetalleEntity);
    }

    private void validateDatosCrearDetalle(PedidoCabeceraEntity pedidoCabecera, List<ProductoConCantidadDto> entityList) {
        List<ErrorDto> errorDtos = new ArrayList<>();
        if (Objects.isNull(pedidoCabecera)) {
            errorDtos.add(new ErrorDto("Error: La cabecera del pedido esta llegando null", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "NULL DATA"));
        } else if (Objects.isNull(entityList)) {
            errorDtos.add(new ErrorDto("Error: No llegaron correctamente los productos", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "NULL DATA"));
        }
        if (!errorDtos.isEmpty()) {
            throw new ErrorListPersonalizadoPedido("GLOBAL_ERROR_RUNTIME", HttpStatus.INTERNAL_SERVER_ERROR, errorDtos);
        }
    }
}
