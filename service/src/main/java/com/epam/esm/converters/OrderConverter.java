package com.epam.esm.converters;

import com.epam.esm.dto.ResponseOrderDto;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.mapper.UserMapperImpl;
import com.epam.esm.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    private final UserMapper userMapper = new UserMapperImpl();
    private final CertificateToDtoConverter certificateToDtoConverter;

    @Autowired
    public OrderConverter(CertificateToDtoConverter certificateToDtoConverter) {
        this.certificateToDtoConverter = certificateToDtoConverter;
    }

    public ResponseOrderDto orderToResponseOderDto(Order order){
       return ResponseOrderDto.builder()
               .id(order.getId())
               .userDto(userMapper.userToDto(order.getUser()))
               .orderDate(order.getOrderDate())
               .cost(order.getCost())
               .giftCertificateDtoList(order.getGiftCertificateList().stream().map(certificateToDtoConverter::convert).toList())
               .build();
    }
}
