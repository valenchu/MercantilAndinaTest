package com.mercantil.example.mercantiltest.service;

import com.mercantil.example.mercantiltest.exceptions.ErrorPersonalizado;
import com.mercantil.example.mercantiltest.persistence.entity.PedidoCabeceraEntity;
import com.mercantil.example.mercantiltest.persistence.entity.PedidoDetalleEntity;
import com.mercantil.example.mercantiltest.persistence.repository.PedidoCabeceraRepository;
import com.mercantil.example.mercantiltest.persistence.repository.PedidoDetalleRepository;
import com.mercantil.example.mercantiltest.service.dto.CalculoCabeceraDto;
import com.mercantil.example.mercantiltest.service.dto.CrearPedidoDto;
import com.mercantil.example.mercantiltest.service.dto.PedidoResponseDtoPadre;
import com.mercantil.example.mercantiltest.service.dto.ProductoConCantidadDto;
import com.mercantil.example.mercantiltest.util.enums.EstadoPedido;
import com.mercantil.example.mercantiltest.util.mapper.CrearPedidoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PedidoCabeceraServiceService implements PedidoCabeceraServiceImpl {
    private final PedidoCabeceraRepository pedidoCabeceraRepository;
    private final CrearPedidoMapper crearPedidoMapper;

    private final PedidoDetalleRepository pedidoDetalleRepository;

    private static final String FORMATO_FECHA = "yyyy-MM-dd";

    @Autowired
    public PedidoCabeceraServiceService(PedidoCabeceraRepository pedidoCabeceraRepository, CrearPedidoMapper crearPedidoMapper, PedidoDetalleRepository pedidoDetalleRepository) {
        this.pedidoCabeceraRepository = pedidoCabeceraRepository;
        this.crearPedidoMapper = crearPedidoMapper;
        this.pedidoDetalleRepository = pedidoDetalleRepository;
    }

    @Override
    public List<PedidoCabeceraEntity> getAll() {
        return null;
    }

    @Override
    public PedidoCabeceraEntity getById(UUID uuid) {
        return null;
    }

    @Override
    public PedidoCabeceraEntity save(PedidoCabeceraEntity entity) {
        return null;
    }

    @Override
    public PedidoCabeceraEntity save(CrearPedidoDto entity, List<ProductoConCantidadDto> entityList) {
        CalculoCabeceraDto calculoCabeceraEstado = calculoCabeceraEstado(entityList);
        if (Objects.isNull(calculoCabeceraEstado)) {
            throw new ErrorPersonalizado("Error: al calcular los productos", HttpStatus.INTERNAL_SERVER_ERROR, "Line: 46 PedidoCabeceraServiceService save()");
        }
        PedidoCabeceraEntity pedidoCabecera = crearPedidoMapper.mapCrearDtoToPedidoCabeceraEntity(entity, calculoCabeceraEstado);
        return pedidoCabeceraRepository.save(pedidoCabecera);
    }

    @Override
    public List<PedidoResponseDtoPadre> getPedidoFecha(String fecha) {
        isValid(fecha);
        List<PedidoCabeceraEntity> listPedidoCabechera = pedidoCabeceraRepository.findByFecha(fecha);
        if (listPedidoCabechera.isEmpty()) {
            throw new ErrorPersonalizado("Error: no se encontraron pedidos con dicha fecha", HttpStatus.NOT_FOUND, fecha);
        }
        List<PedidoDetalleEntity> pedidoDetalleEntities = new ArrayList<>();
        listPedidoCabechera.forEach(cabecera -> {
            List<PedidoDetalleEntity> pedidoDetalleEntitiesAux = new ArrayList<>();
            pedidoDetalleEntitiesAux = pedidoDetalleRepository.findByPedidoCabeceraIdOrPedidoCabecera2(cabecera.getId(), null);
            pedidoDetalleEntities.removeAll(pedidoDetalleEntitiesAux);
            pedidoDetalleEntities.addAll(pedidoDetalleEntitiesAux);
        });
        return crearPedidoMapper.mapPedidoDetalleToResponseDtoPadre(pedidoDetalleEntities);
    }

    @Override
    public List<PedidoCabeceraEntity> saveAll(List<PedidoCabeceraEntity> entity) {
        return null;
    }

    @Override
    public PedidoCabeceraEntity update(UUID uuid, PedidoCabeceraEntity entity) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    /**
     * @param entityList
     * @return devuelve el dto con monto, descuento, estado
     * @implNote Este metodo se encargara de calcular los datos de monto, descuento, estado para rellenar la entity cabecera
     */
    private CalculoCabeceraDto calculoCabeceraEstado(List<ProductoConCantidadDto> entityList) {
        if (Objects.nonNull(entityList) && !entityList.isEmpty()) {
            boolean descuento = entityList.stream().mapToDouble(ProductoConCantidadDto::getCant).sum() > 3;
            double acumularMonto = entityList.stream().mapToDouble(producEntity -> producEntity.getProductoEntity().getPrecioUnitario() * producEntity.getCant()).sum();
            if (descuento) {
                acumularMonto = acumularMonto * (1 - (30D / 100D));
            }
            return new CalculoCabeceraDto(acumularMonto, descuento, EstadoPedido.PENDIENTE);
        } else {
            return null;
        }
    }

    /**
     * @param fecha
     * @return valida la fecha
     */
    public void isValid(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new ErrorPersonalizado("Error: fecha null o vacia", HttpStatus.BAD_REQUEST, "NO_EXIST_DATE");
        }

        try {
            LocalDate.parse(fecha, DateTimeFormatter.ofPattern(FORMATO_FECHA));
        } catch (Exception e) {
            throw new ErrorPersonalizado("Error: el formato de la fecha es erroneo", HttpStatus.BAD_REQUEST, "FORMATO_ERRONEO" + fecha);
        }
    }

}
