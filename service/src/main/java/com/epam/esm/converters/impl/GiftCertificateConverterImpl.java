package com.epam.esm.converters.impl;

import com.epam.esm.converters.DtoConverter;
import com.epam.esm.converters.DtoToTagConverter;
import com.epam.esm.converters.TagToDtoConverter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class GiftCertificateConverterImpl implements DtoConverter<GiftCertificateDto, GiftCertificate> {

    private final TagToDtoConverter tagToDtoConverter;
    private final DtoToTagConverter dtoToTagConverter;

    @Autowired
    public GiftCertificateConverterImpl(TagToDtoConverter tagToDtoConverter, DtoToTagConverter dtoToTagConverter) {
        this.tagToDtoConverter = tagToDtoConverter;
        this.dtoToTagConverter = dtoToTagConverter;
    }

    @Override
    public GiftCertificateDto entityToDto(GiftCertificate entityObject) {
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .id(entityObject.getId())
                .name(entityObject.getName())
                .description(entityObject.getDescription())
                .price(entityObject.getPrice())
                .duration(entityObject.getDuration())
                .createDate(entityObject.getCreateDate())
                .lastUpdateDate(entityObject.getLastUpdateDate())
                .build();
        if(Objects.nonNull(entityObject.getTagList())){
            giftCertificateDto.setTags(entityObject.getTagList().stream().map(tagToDtoConverter::convert).collect(Collectors.toSet()));
        }
        return giftCertificateDto;
    }

    @Override
    public GiftCertificate dtoToEntity(GiftCertificateDto dtoObject) {
        GiftCertificate giftCertificate =  GiftCertificate.builder()
                .id(dtoObject.getId())
                .name(dtoObject.getName())
                .description(dtoObject.getDescription())
                .price(dtoObject.getPrice())
                .duration(dtoObject.getDuration())
                .createDate(dtoObject.getCreateDate())
                .lastUpdateDate(dtoObject.getLastUpdateDate())
                .build();
        if(Objects.nonNull(dtoObject.getTags())){
            giftCertificate.setTagList(dtoObject.getTags().stream().map(dtoToTagConverter::convert).collect(Collectors.toSet()));
        }
        return giftCertificate;
    }
}
