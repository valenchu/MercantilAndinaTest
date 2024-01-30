package com.mercantil.example.mercantiltest.util.mapper;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercantil.example.mercantiltest.persistence.entity.ProductoEntity;
import com.mercantil.example.mercantiltest.service.dto.ProductoConCantidadDto;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductoMapper {
    private ObjectMapper objectMapper;

    /**
     * @param productoToUpdate
     * @param producto
     * @return metodo que actualiza el producto para actualizar en la base
     * @throws JsonMappingException
     */
    public ProductoEntity updateProducto(ProductoEntity productoToUpdate, ProductoEntity producto) throws JsonMappingException {
        objectMapper = new ObjectMapper();
        @SuppressWarnings("unchecked") Map<String, Object> personaBAsMap = objectMapper.convertValue(producto, Map.class);

        // Actualiza la instancia de ProductoA con los datos de ProductoB
        objectMapper.updateValue(productoToUpdate, personaBAsMap);

        System.out.println(productoToUpdate.toString());
        return productoToUpdate;
    }

    /**
     * @param productoEntity
     * @param cant
     * @return devuelve ProductoConCantidadDto mapeado de productoEntity y cant
     */
    public ProductoConCantidadDto productoConCantidadDto(ProductoEntity productoEntity, Double cant) {
        ProductoConCantidadDto productoConCantidadDto = new ProductoConCantidadDto(productoEntity, cant);
        return productoConCantidadDto;
    }

}

