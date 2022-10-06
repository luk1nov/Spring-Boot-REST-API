package com.epam.esm.mapper;


import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificate;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = TagMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface GiftCertificateMapper extends BaseMapper<GiftCertificateDto, GiftCertificate>{

    @Mapping(target = "tagList", source = "tags")
    @Override
    GiftCertificate dtoToEntity(GiftCertificateDto dtoObject);

    @Mapping(target = "tags", source = "tagList")
    @Override
    GiftCertificateDto entityToDto(GiftCertificate entityObject);
}
