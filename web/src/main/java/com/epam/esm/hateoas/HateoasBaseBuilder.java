package com.epam.esm.hateoas;

import org.springframework.hateoas.RepresentationModel;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public interface HateoasBaseBuilder {

    String SELF = "self";
    String DELETE = "delete";
    String UPDATE = "update";

    default <T extends RepresentationModel<T>> void setCommonLinks(Class<?> controllerClass, T entity, Long id, boolean all, String...rels){
        Arrays.asList(rels).forEach(rel -> entity.add(linkTo(controllerClass).slash(id).withRel(rel)));
        if(all){
            entity.add(linkTo(controllerClass).withRel("all"));
        }
    }
}
