package com.epam.esm.mapper;

import org.springframework.hateoas.RepresentationModel;

public interface BaseMapper<D extends RepresentationModel<D>, E> {
    E dtoToEntity(D dtoObject);
    D entityToDto(E entityObject);
}
