package com.epam.esm.mapper;

import com.epam.esm.dto.ResponseOrderDto;
import com.epam.esm.model.Order;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {GiftCertificateMapper.class, UserMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrderMapper {

    @Mapping(target = "userDto", source = "user")
    @Mapping(target = "giftCertificateDtoList", source = "giftCertificateList")
    ResponseOrderDto orderToResponseDto(Order order);

    /*@Mapping(target = "user", source = "tags")
    Order requestDtoToOrder(RequestOrderDto requestOrderDto);*/
}
