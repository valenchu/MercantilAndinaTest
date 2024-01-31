package com.mercantil.example.mercantiltest.controller;

import com.mercantil.example.mercantiltest.persistence.entity.ProductoEntity;
import com.mercantil.example.mercantiltest.service.ProductoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("MercantilAndinaTest/productos")
public class ProductoController {
    @Autowired
    private ProductoServiceImpl productoService;

    @GetMapping("traer_todos_productos")
    public ResponseEntity<?> getAllProducto() {
        List<ProductoEntity> productoEntityList = this.productoService.getAll();
        return new ResponseEntity<>(productoEntityList, HttpStatus.OK);
    }
    @GetMapping("traer_producto/{id}")
    public ResponseEntity<?> getProducto(@PathVariable(value = "id")UUID id) {
        ProductoEntity productoEntityList = this.productoService.getById(id);
        return new ResponseEntity<>(productoEntityList, HttpStatus.OK);
    }


    @PostMapping("create_lista_productos")
    public ResponseEntity<?> createAll(@RequestBody List<ProductoEntity> productoEntity) {
        List<ProductoEntity> productEntity = this.productoService.saveAll(productoEntity);
        return new ResponseEntity<>(productEntity, HttpStatus.CREATED);
    }

    @PostMapping("create_producto")
    public ResponseEntity<?> create(@RequestBody ProductoEntity productoEntity) {
        ProductoEntity productEntity = this.productoService.save(productoEntity);
        return new ResponseEntity<>(productEntity, HttpStatus.CREATED);
    }

    @PutMapping("update_producto/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") UUID id, @RequestBody ProductoEntity productoEntity) {
        this.productoService.update(id, productoEntity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("delete_producto/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") UUID id) {
        String uuiddSinBlancos = id.toString().replaceAll("\\s", "");
        this.productoService.delete(id);
        return new ResponseEntity<>("Producto: "+uuiddSinBlancos+" borrado exitosamente",HttpStatus.NO_CONTENT);
    }



}
