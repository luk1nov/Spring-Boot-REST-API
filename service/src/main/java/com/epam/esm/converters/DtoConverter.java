package com.epam.esm.converters;

public interface DtoConverter<D, E> {
    D entityToDto(E entityObject);
    E dtoToEntity(D dtoObject);
}
