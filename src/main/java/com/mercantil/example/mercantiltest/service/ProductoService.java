package com.mercantil.example.mercantiltest.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.mercantil.example.mercantiltest.exceptions.ErrorPersonalizado;
import com.mercantil.example.mercantiltest.exceptions.dto.ErrorDto;
import com.mercantil.example.mercantiltest.persistence.entity.ProductoEntity;
import com.mercantil.example.mercantiltest.persistence.repository.ProductoRepository;
import com.mercantil.example.mercantiltest.util.mapper.ProductoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Valentín Cassino
 * @implNote Clase que se encarga de la logica de producto
 */
@Service
public class ProductoService implements ProductoServiceImpl {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private String mensajeError = "";

    @Autowired
    public ProductoService(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    public List<ProductoEntity> getAll() {
        List<ProductoEntity> entityList = (List<ProductoEntity>) this.productoRepository.findAll();
        if (Objects.isNull(entityList) || entityList.isEmpty()) {
            throw new ErrorPersonalizado("Error: No se encontraron productos a devolver", HttpStatus.NOT_FOUND, "lista vacia o nula");
        }
        return entityList;
    }

    @Override
    public ProductoEntity getById(UUID uuid) {
        String uuiddSinBlancos = getUuiddSinBlancos(uuid);
        Optional<ProductoEntity> productoEntity = Optional.ofNullable(this.productoRepository.findById(UUID.fromString(uuiddSinBlancos))).orElse(null);
        if (Objects.isNull(productoEntity) || !productoEntity.isPresent()) {
            throw new ErrorPersonalizado("Error: ID: "+uuid.toString().trim()+" producto, no encontrado", HttpStatus.NOT_FOUND, uuiddSinBlancos);
        }
        return productoEntity.get();
    }

    @Override
    public ProductoEntity save(ProductoEntity entity) {
        if (validateProducto(entity)) {
            throw new ErrorPersonalizado(mensajeError, HttpStatus.INTERNAL_SERVER_ERROR, entity.getId().toString());
        }
        return this.productoRepository.save(entity);
    }

    @Override
    public List<ProductoEntity> saveAll(List<ProductoEntity> entity) {
        if (validateProductoList(entity)) {
            throw new ErrorPersonalizado(mensajeError, HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
        return (List<ProductoEntity>) this.productoRepository.saveAll(entity);
    }

    @Override
    public ProductoEntity update(UUID uuid, ProductoEntity entity) {
        String uuiddSinBlancos = getUuiddSinBlancos(uuid);
        Optional<ProductoEntity> updatedEntity = this.productoRepository.findById(UUID.fromString(uuiddSinBlancos)).map(userDb -> {
            try {
                return this.productoMapper.updateProducto(userDb, entity);
            } catch (JsonMappingException e) {
                throw new ErrorPersonalizado("Error: al actualizar prodcuto " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        });
        if (!updatedEntity.isPresent()) {
            throw new ErrorPersonalizado("Error: al actualizar prodcuto", HttpStatus.INTERNAL_SERVER_ERROR, uuiddSinBlancos);
        }

        return productoRepository.save(updatedEntity.get());
    }

    @Override
    public void delete(UUID uuid) {
        String uuiddSinBlancos = getUuiddSinBlancos(uuid);
        if (Objects.isNull(uuid) || uuid.toString().isEmpty()) {
            throw new ErrorPersonalizado("Error: es requerido el ID", HttpStatus.INTERNAL_SERVER_ERROR, uuiddSinBlancos);
        }
        this.productoRepository.delete(this.productoRepository.findById(UUID.fromString(uuiddSinBlancos)).get());
    }

    private static String getUuiddSinBlancos(UUID uuid) {
        String uuiddSinBlancos = uuid.toString().replaceAll("\\s", "");
        return uuiddSinBlancos;
    }

    /**
     * @param entity
     * @return devuelve true si es valido
     * @apiNote Valida lista de producto a guardar
     */
    private boolean validateProductoList(List<ProductoEntity> entity) {
        boolean respuesta = false;
        if (entity.stream().anyMatch(prodcut -> Objects.isNull(prodcut) || Objects.isNull(prodcut.getId()) || prodcut.getId().toString().trim().isEmpty())) {
            this.mensajeError = "Error: al crear los productos uno o mas no posee ID";
            respuesta = true;
        }
        return respuesta;
    }

    /**
     * @param entity
     * @return devuelve true si es valido
     * @apiNote Valida el producto unico a guardar
     */
    private boolean validateProducto(ProductoEntity entity) {
        boolean respuesta = false;
        if (Objects.isNull(entity) || Objects.isNull(entity.getId()) || entity.getId().toString().trim().isEmpty()) {
            this.mensajeError = "Error: al crear producto necesita ID";
            respuesta = true;
        }
        return respuesta;
    }
}
