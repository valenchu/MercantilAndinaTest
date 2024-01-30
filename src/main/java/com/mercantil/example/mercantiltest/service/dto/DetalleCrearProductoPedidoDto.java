package com.mercantil.example.mercantiltest.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCrearProductoPedidoDto {
    private String producto;
    @NotNull(message = "Error: falta ingresar cantidad")
    @Min(value = 1,message = "Error: la cantidad minima es 1")
    private Double cantidad;
}
