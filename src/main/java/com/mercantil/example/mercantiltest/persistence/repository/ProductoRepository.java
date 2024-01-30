package com.mercantil.example.mercantiltest.persistence.repository;

import com.mercantil.example.mercantiltest.persistence.entity.ProductoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductoRepository extends CrudRepository<ProductoEntity, UUID> {
}
