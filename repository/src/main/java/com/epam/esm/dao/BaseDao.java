package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    T insert(T t);

    List<T> findAll();

    Optional<T> findById(Long id);

    int update(Long id, T t);

    int deleteById(Long id);
}
