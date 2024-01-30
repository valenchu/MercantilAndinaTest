package com.mercantil.example.mercantiltest.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrearPedidoDto {
    @NotNull(message = "Error: la direccion no puede estar nula" )
    @NotEmpty(message = "Error: la direccion no puede estar vacia")
    private String direccion;
    private String email;
    private String telefono;
    private String horario;
    @Valid
    private List<DetalleCrearProductoPedidoDto> detalle;
}
