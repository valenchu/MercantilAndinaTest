package com.mercantil.example.mercantiltest.persistence.repository;

import com.mercantil.example.mercantiltest.persistence.entity.PedidoCabeceraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoCabeceraRepository extends JpaRepository<PedidoCabeceraEntity, UUID> {
    @Query("SELECT pc FROM PedidoCabeceraEntity pc WHERE CAST(pc.fechaAlta AS date) = CAST(:fecha AS date)")
    List<PedidoCabeceraEntity> findByFecha(@Param("fecha") String fecha);
}
