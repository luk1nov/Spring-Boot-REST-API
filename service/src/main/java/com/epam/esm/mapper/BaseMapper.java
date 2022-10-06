package com.epam.esm.mapper;

import org.springframework.hateoas.RepresentationModel;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public interface BaseMapper<D extends RepresentationModel<D>, E> {
    E dtoToEntity(D dtoObject);
    D entityToDto(E entityObject);

    default <T extends RepresentationModel<T>> void setCommonLinks(Class<?> controllerClass, T entity, Long id, String...rels){
        Arrays.asList(rels).forEach(rel -> entity.add(linkTo(controllerClass).slash(id).withRel(rel)));
    }
}
