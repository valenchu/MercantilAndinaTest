package com.mercantil.example.mercantiltest.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedidos_detalle")
public class PedidoDetalleEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    private Double cantidad;
    private Double precioUnitario;

    @ManyToOne
    @JoinColumn(name = "pedido_cabecera_id", nullable = false)
    private PedidoCabeceraEntity pedidoCabecera;

    @ManyToOne
    @JoinColumn(name="producto_id", referencedColumnName = "id")
    private ProductoEntity producto;

}
