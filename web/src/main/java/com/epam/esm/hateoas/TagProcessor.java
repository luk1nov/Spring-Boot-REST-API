package com.epam.esm.hateoas;

import com.epam.esm.controllers.GiftCertificatesController;
import com.epam.esm.controllers.TagsController;
import com.epam.esm.dto.TagDto;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class TagProcessor implements RepresentationModelProcessor<TagDto>, HateoasBaseBuilder {

    @Override
    public TagDto process(TagDto tagDto) {
        setCommonLinks(TagsController.class, tagDto, tagDto.getId(), true, SELF, DELETE, UPDATE);
        tagDto.add(linkTo(GiftCertificatesController.class).slash("search_by_tag")
                .slash(tagDto.getId())
                .withRel("certificates"));
        return tagDto;
    }
}
