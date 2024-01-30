package com.mercantil.example.mercantiltest.service;

import java.util.List;
import java.util.Optional;

public interface CrudBaseService<T, ID> {

    List<T> getAll();

    T getById(ID id);

    T save(T entity);
    List<T> saveAll(List<T> entity);

    T update(ID id, T entity);

    void delete(ID id);
}