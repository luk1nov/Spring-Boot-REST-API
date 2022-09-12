package com.epam.esm.hateoas;

import com.epam.esm.controllers.GiftCertificatesController;
import com.epam.esm.controllers.TagsController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class HateoasBuilder {

    private static final String SELF = "self";
    private static final String DELETE = "delete";
    private static final String UPDATE = "update";

    public void setLinks(TagDto tagDto){
        setCommonLinks(TagsController.class, tagDto, tagDto.getId(), SELF, DELETE, UPDATE);
    }

    public void setLinks(GiftCertificateDto giftCertificateDto){
        setCommonLinks(GiftCertificatesController.class, giftCertificateDto, giftCertificateDto.getId(), SELF, DELETE, UPDATE);
        giftCertificateDto.getTags().forEach(this::setLinks);
    }

    private <T extends RepresentationModel<T>> void setCommonLinks(Class<?> controllerClass, T entity, Long id, String...rels){
        Arrays.asList(rels).forEach(rel -> entity.add(linkTo(controllerClass).slash(id).withRel(rel)));
    }
}
