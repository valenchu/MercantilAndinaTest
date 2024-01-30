package com.mercantil.example.mercantiltest.service.dto;

import com.mercantil.example.mercantiltest.persistence.entity.ProductoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoConCantidadDto {
    private ProductoEntity productoEntity;
    private Double cant;
}
