package com.mercantil.example.mercantiltest.persistence.repository;

import com.mercantil.example.mercantiltest.persistence.entity.PedidoCabeceraEntity;
import com.mercantil.example.mercantiltest.persistence.entity.PedidoDetalleEntity;
import com.mercantil.example.mercantiltest.service.dto.PedidoResponseDtoPadre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalleEntity, UUID> {
    @Query("SELECT pd FROM PedidoDetalleEntity pd WHERE pd.pedidoCabecera.id = :pedidoCabeceraId OR pd.pedidoCabecera = :pedidoCabecera")
    List<PedidoDetalleEntity> findByPedidoCabeceraIdOrPedidoCabecera(@Param("pedidoCabeceraId") UUID pedidoCabeceraId, @Param("pedidoCabecera") PedidoCabeceraEntity pedidoCabecera);

    @Query("SELECT pd FROM PedidoDetalleEntity pd " +
            "JOIN FETCH pd.pedidoCabecera pc " +
            "JOIN FETCH pd.producto p " +
            "WHERE pc.id = :pedidoCabeceraId OR pc = :pedidoCabecera")
    List<PedidoDetalleEntity> findByPedidoCabeceraIdOrPedidoCabecera2(@Param("pedidoCabeceraId") UUID pedidoCabeceraId, @Param("pedidoCabecera") PedidoCabeceraEntity pedidoCabecera);

}

