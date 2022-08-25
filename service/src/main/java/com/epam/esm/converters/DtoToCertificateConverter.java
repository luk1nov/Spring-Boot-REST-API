package com.epam.esm.converters;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DtoToCertificateConverter implements Converter<GiftCertificateDto, GiftCertificate> {

    private final DtoToTagConverter dtoToTagConverter;

    @Autowired
    public DtoToCertificateConverter(DtoToTagConverter dtoToTagConverter) {
        this.dtoToTagConverter = dtoToTagConverter;
    }

    @Override
    public GiftCertificate convert(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate =  GiftCertificate.builder()
                .id(giftCertificateDto.getId())
                .name(giftCertificateDto.getName())
                .description(giftCertificateDto.getDescription())
                .price(giftCertificateDto.getPrice())
                .duration(giftCertificateDto.getDuration())
                .createDate(giftCertificateDto.getCreateDate())
                .lastUpdateDate(giftCertificateDto.getLastUpdateDate())
                .build();
        if(Objects.nonNull(giftCertificateDto.getTags())){
            giftCertificate.setTagList(giftCertificateDto.getTags().stream().map(dtoToTagConverter::convert).collect(Collectors.toSet()));
        }
        return giftCertificate;
    }
}
