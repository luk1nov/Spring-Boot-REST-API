package com.epam.esm.service;

import java.util.List;

public interface BaseService<T> {
    T findById(Long id);

    List<T> findAll();
}
