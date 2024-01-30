package com.mercantil.example.mercantiltest.service;

import com.mercantil.example.mercantiltest.exceptions.ErrorListPersonalizadoPedido;
import com.mercantil.example.mercantiltest.exceptions.dto.ErrorDto;
import com.mercantil.example.mercantiltest.persistence.entity.PedidoCabeceraEntity;
import com.mercantil.example.mercantiltest.persistence.entity.ProductoEntity;
import com.mercantil.example.mercantiltest.service.dto.CrearPedidoDto;
import com.mercantil.example.mercantiltest.service.dto.PedidoResponseDto;
import com.mercantil.example.mercantiltest.service.dto.ProductoConCantidadDto;
import com.mercantil.example.mercantiltest.util.mapper.CrearPedidoMapper;
import com.mercantil.example.mercantiltest.util.mapper.ProductoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ManangerPedidoService implements ManangerPedidoImpl {

    private final PedidoCabeceraServiceImpl pedidoCabeceraService;
    private final PedidoDetalleServiceImpl pedidoDetalleService;
    private final ProductoServiceImpl productoService;

    private final CrearPedidoMapper pedidoMapper;

    @Autowired
    public ManangerPedidoService(PedidoCabeceraServiceImpl pedidoCabeceraService, PedidoDetalleServiceImpl pedidoDetalleService, ProductoServiceImpl productoService, CrearPedidoMapper pedidoMapper) {
        this.pedidoCabeceraService = pedidoCabeceraService;
        this.pedidoDetalleService = pedidoDetalleService;
        this.productoService = productoService;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public PedidoResponseDto createPedido(CrearPedidoDto crearPedidoDto) {
        validateCrearPedido(crearPedidoDto);
        List<ProductoConCantidadDto> entityList = crearPedidoDto.getDetalle().stream().map(productList -> {
            ProductoEntity productoEntity = productoService.getById(UUID.fromString(productList.getProducto()));
            ProductoMapper productoMapper = new ProductoMapper();
            validateCrearPedido(productoEntity, productList.getCantidad());
            return productoMapper.productoConCantidadDto(productoEntity, productList.getCantidad());
        }).collect(Collectors.toList());
        PedidoCabeceraEntity pedidoCabecera = pedidoCabeceraService.save(crearPedidoDto, entityList);
        return pedidoDetalleService.save(pedidoCabecera, entityList);
    }

    private void validateCrearPedido(ProductoEntity productList, Double cantidad) {
        List<ErrorDto> errorDtos = new ArrayList<>();
        if (Objects.isNull(productList)) {
            errorDtos.add(new ErrorDto("Error: El ID ingresado es erroneo o contiene errores", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "NULL DATA"));
        } else if (Objects.isNull(cantidad) || cantidad == 0D) {
            errorDtos.add(new ErrorDto("Error: Falta ingresar cantidad o cantidad en cero", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, "NULL DATA"));
        }
        if (!errorDtos.isEmpty()) {
            throw new ErrorListPersonalizadoPedido("GLOBAL_ERROR_RUNTIME", HttpStatus.NOT_FOUND, errorDtos);
        }
    }

    private void validateCrearPedido(CrearPedidoDto productList) {
    }
}
