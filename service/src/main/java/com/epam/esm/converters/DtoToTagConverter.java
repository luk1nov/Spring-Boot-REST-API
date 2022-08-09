package com.epam.esm.converters;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToTagConverter implements Converter<TagDto, Tag> {

    @Override
    public Tag convert(TagDto tagDto) {
        return new Tag(tagDto.getId(), tagDto.getName());
    }
}
