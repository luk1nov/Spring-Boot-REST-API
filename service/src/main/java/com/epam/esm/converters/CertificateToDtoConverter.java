package com.epam.esm.converters;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CertificateToDtoConverter implements Converter<GiftCertificate, GiftCertificateDto> {

    private final TagToDtoConverter tagToDtoConverter;

    @Autowired
    public CertificateToDtoConverter(TagToDtoConverter tagToDtoConverter) {
        this.tagToDtoConverter = tagToDtoConverter;
    }

    @Override
    public GiftCertificateDto convert(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .id(giftCertificate.getId())
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .price(giftCertificate.getPrice())
                .duration(giftCertificate.getDuration())
                .createDate(giftCertificate.getCreateDate())
                .lastUpdateDate(giftCertificate.getLastUpdateDate())
                .build();
        if(Objects.nonNull(giftCertificate.getTags())){
            giftCertificateDto.setTags(giftCertificate.getTags().stream().map(tagToDtoConverter::convert).collect(Collectors.toSet()));
        }
        return giftCertificateDto;
    }
}
