package com.epam.esm.dao;

import com.epam.esm.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends AbstractEntity> {
    T insert(T t);

    List<T> findAll();

    Optional<T> findById(Long id);

    int update(Long id, T t);

    int deleteById(Long id);
}
