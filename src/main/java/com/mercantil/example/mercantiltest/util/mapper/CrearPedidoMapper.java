package com.mercantil.example.mercantiltest.util.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercantil.example.mercantiltest.persistence.entity.PedidoCabeceraEntity;
import com.mercantil.example.mercantiltest.persistence.entity.PedidoDetalleEntity;
import com.mercantil.example.mercantiltest.persistence.entity.ProductoEntity;
import com.mercantil.example.mercantiltest.service.dto.*;
import com.mercantil.example.mercantiltest.util.UtilsFechaHorario;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class CrearPedidoMapper {
    private ObjectMapper objectMapper;

    /**
     * @param crearPedidoDto
     * @param calculoCabeceraDto
     * @return devuelve el pedido cabecera mapeado del json dto y el calculo de los productos
     */
    public PedidoCabeceraEntity mapCrearDtoToPedidoCabeceraEntity(CrearPedidoDto crearPedidoDto, CalculoCabeceraDto calculoCabeceraDto) {
        LocalTime horarioInTime = UtilsFechaHorario.getInstance().convertirStringAHorario(crearPedidoDto.getHorario());
        return new PedidoCabeceraEntity(null, crearPedidoDto.getDireccion(), crearPedidoDto.getEmail(), crearPedidoDto.getTelefono(), horarioInTime, LocalDate.now(), calculoCabeceraDto.getMontoTotal(), calculoCabeceraDto.getAplicoDescuento(), calculoCabeceraDto.getEstado());
    }

    /**
     * @param pedidoCabecera
     * @param productoEntities
     * @return Devuelve el detalle mapeado final para guardar el pedido final en la BD
     */
    public PedidoDetalleEntity mapCabeceraProductoToDetalle(PedidoCabeceraEntity pedidoCabecera, ProductoConCantidadDto productoEntities) {
        double precioUnitarioSinDescuentoSuma = productoEntities.getProductoEntity().getPrecioUnitario();
        return new PedidoDetalleEntity(null, productoEntities.getCant(), precioUnitarioSinDescuentoSuma, pedidoCabecera, productoEntities.getProductoEntity());
    }

    public PedidoResponseDto mapPedidoCabeceraToResponseDto(List<PedidoDetalleEntity> pedidoDetalle) {
        PedidoResponseDto responseDto = new PedidoResponseDto();
        PedidoCabeceraEntity pedidoCabecera = pedidoDetalle.get(0).getPedidoCabecera();
        // Mapeo de atributos simples
        responseDto.setFecha(pedidoCabecera.getFechaAlta());
        responseDto.setDireccion(pedidoCabecera.getDireccion());
        responseDto.setEmail(pedidoCabecera.getEmail());
        responseDto.setTelefono(pedidoCabecera.getTelefono());
        responseDto.setHorario(UtilsFechaHorario.getInstance().convertirHorarioAString(pedidoCabecera.getHorario()));
        responseDto.setTotal(pedidoCabecera.getMontoTotal());
        responseDto.setDescuento(pedidoCabecera.getAplicoDescuento());
        responseDto.setEstado(pedidoCabecera.getEstado());

        // Mapeo de la lista de DetalleProductoPedidoResponseDto
        List<DetalleProductoPedidoResponseDto> detalleDtoList = new ArrayList<>();
        for (PedidoDetalleEntity detalleEntity : pedidoDetalle) {
            DetalleProductoPedidoResponseDto detalleDto = mapDetalleProductoPedidoToResponseDto(detalleEntity.getProducto(), detalleEntity.getCantidad());
            detalleDtoList.add(detalleDto);
        }
        responseDto.setDetalle(detalleDtoList);

        return responseDto;
    }

    private DetalleProductoPedidoResponseDto mapDetalleProductoPedidoToResponseDto(ProductoEntity productoEntity, Double cantidad) {
        return new DetalleProductoPedidoResponseDto(productoEntity.getId().toString(), productoEntity.getNombre(), cantidad, productoEntity.getPrecioUnitario());
    }

    /**
     * @param pedidoDetalleEntities
     * @return mapeo pedido detalle recolectado via cabecera id
     */
    public List<PedidoResponseDtoPadre> mapPedidoDetalleToResponseDtoPadre(List<PedidoDetalleEntity> pedidoDetalleEntities) {
        AtomicReference<UUID> save = new AtomicReference<>();
        return pedidoDetalleEntities.stream().map(detalle -> {
            PedidoResponseDtoPadre dto = null;
            if (Objects.isNull(save.get()) || !save.get().equals(detalle.getPedidoCabecera().getId())) {
                dto = new PedidoResponseDtoPadre();
                dto.setFecha(detalle.getPedidoCabecera().getFechaAlta());
                dto.setDireccion(detalle.getPedidoCabecera().getDireccion());
                dto.setEmail(detalle.getPedidoCabecera().getEmail());
                dto.setTelefono(detalle.getPedidoCabecera().getTelefono());
                dto.setHorario(String.valueOf(detalle.getPedidoCabecera().getHorario()));
                dto.setDetalle(pedidoDetalleEntities.stream().map(detalleInterno -> {
                    DetalleProductoPedidoResponseDto detalleDto = null;
                    if (detalleInterno.getPedidoCabecera().getId().equals(detalle.getPedidoCabecera().getId())) {
                        detalleDto = new DetalleProductoPedidoResponseDto();
                        detalleDto.setProducto(detalleInterno.getProducto().getId().toString());
                        detalleDto.setNombre(detalleInterno.getProducto().getNombre());
                        detalleDto.setCantidad(detalleInterno.getCantidad());
                        detalleDto.setImporte(detalleInterno.getProducto().getPrecioUnitario());

                    }
                    return detalleDto;
                }).filter(producto -> producto != null).collect(Collectors.toList()));
                dto.setTotal(detalle.getPedidoCabecera().getMontoTotal());
                dto.setDescuento(detalle.getPedidoCabecera().getAplicoDescuento());
            }
            save.set(detalle.getPedidoCabecera().getId());
            return dto;
        }).filter(detalle -> detalle != null).collect(Collectors.toList());

    }
}
