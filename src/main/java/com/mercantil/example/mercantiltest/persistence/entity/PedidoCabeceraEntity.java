package com.mercantil.example.mercantiltest.persistence.entity;

import com.mercantil.example.mercantiltest.util.enums.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedidos_cabecera")
public class PedidoCabeceraEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    private String direccion;
    private String email;
    private String telefono;
    private LocalTime horario;
    private LocalDate fechaAlta;
    private Double montoTotal;
    private Boolean aplicoDescuento;
    private EstadoPedido estado;

}