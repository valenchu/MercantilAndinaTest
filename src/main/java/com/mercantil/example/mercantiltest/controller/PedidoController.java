package com.mercantil.example.mercantiltest.controller;

import com.mercantil.example.mercantiltest.service.ManangerPedidoImpl;
import com.mercantil.example.mercantiltest.service.PedidoCabeceraServiceImpl;
import com.mercantil.example.mercantiltest.service.PedidoDetalleServiceImpl;
import com.mercantil.example.mercantiltest.service.dto.CrearPedidoDto;
import com.mercantil.example.mercantiltest.service.dto.PedidoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("pedidos")
public class PedidoController {
    @Autowired
    private ManangerPedidoImpl manangerPedido;
    @Autowired
    private PedidoDetalleServiceImpl pedidoDetalleService;
    @Autowired
    private PedidoCabeceraServiceImpl pedidoCabeceraService;

    @PostMapping("crear_pedido")
    public ResponseEntity<?> crearPedido(@RequestBody @Valid CrearPedidoDto crearPedidoDto) {
        PedidoResponseDto responseDto = manangerPedido.createPedido(crearPedidoDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("traer_todos_pedidos")
    public ResponseEntity<?> getAllPedidos() {
        return new ResponseEntity<>(pedidoDetalleService.getAll(), HttpStatus.OK);
    }

    @GetMapping("traer_pedidos_fecha")
    public ResponseEntity<?> getPedidosFecha(@RequestParam("fecha") String fecha) {
        return new ResponseEntity<>(pedidoCabeceraService.getPedidoFecha(fecha), HttpStatus.OK);
    }
}
